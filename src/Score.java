import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

public class Score {
	
	HashMap<String, restaurant> restuarants = new HashMap<String, restaurant>();
	HashMap<String, String> exreview = new HashMap<String, String>();

	/*
	 * 读取文件，进行评分，排序并写入文件
	 */
	public void process(String data, String problem, String result, boolean tag) throws IOException{
		BufferedReader br_1 = new BufferedReader(new InputStreamReader(new FileInputStream(data), "gbk"));
		BufferedReader br_2 = new BufferedReader(new InputStreamReader(new FileInputStream(problem), "gbk"));
		ClearTxt_Path clear = new ClearTxt_Path();
		clear.cleartxt(result);
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(result), "GB18030"));
		String temp1,temp2;
		int linenum = 0;
		while(((temp1 = br_1.readLine())!=null) && ((temp2=br_2.readLine())!=null)){
			
			//输出百行标记
			linenum++;
//			if((linenum%100)==0)
//				System.out.println(linenum);
			System.out.println(linenum);
			
			
			//list1包含query和餐馆，list2包含query和all tags
			String[] list1 = temp1.split("\t");
			String[] list2 = temp2.split(" ");
			String query = list1[0];
			
			//base用于匹配基础tag，包括环境，味道，服务
			String base = "";
			for(int i = 1; i < list2.length; i++){
				String s = list2[i].split(":")[0];
				 if(s.equals("环境") || s.equals("味道") ||s.equals("服务") || s.equals("价格") || s.equals("高档")){
					 base = base + s + " ";
				 }
			}
			
			//用于存放每个餐厅和对应的总分
			final HashMap<String, Float> QueryScore = new HashMap<String, Float>(); 
			for(int i = 1; i < list1.length; i++){
				//将餐厅名改为对应的本地文件的名字
				String resname = list1[i].replace(":", " ");
				//利用base的tag计算基础评分
				float basescore = basescore(base, resname);
				//将temp2的tag通过标题计算标题评分
				float titlescore = tilscore(temp2, resname);
				//对temp2的tag进行评价匹配，得到评价评分
				float reviewsocre = revscore(temp2, resname);
				//对基础评分，标题评分，评价评分进行汇总计算总分
				float sumscore = basescore*10 + titlescore*10 + reviewsocre;//手动或扩展35
				//将对应的餐馆和总分放入hashmap
				QueryScore.put(list1[i], sumscore);
			}
			
			//对餐馆和总分进行降序排序，并写入
	        ArrayList<String> rest = new ArrayList<String>(QueryScore.keySet());
	        Collections.sort(rest, new Comparator<Object>() {  
	            //降序排序  
	            public int compare(Object o1, Object o2) {  
	                return QueryScore.get(o2).compareTo(QueryScore.get(o1));  
	            }  
	        });  
	        bw.write(query);
	        for(String s : rest){
	        	if(tag)
		        	bw.write("\t" + s + ":" + QueryScore.get(s));
	        	else
	        		bw.write("\t" + s);
	        }
	        bw.write("\n");
		}
		bw.close();
		br_1.close();
		br_2.close();
	}
	
	/*
	 * 计算基础评分
	 */
	public float basescore(String base, String resname){
		float score = 0;
		float environment = 0;
		float flavor = 0;
		float service = 0;
		float price = 0;
		float gaodang = 0;
		if(base.equals("") || (restuarants.get(resname) == null)){
			return score;
		}
		else{
			restaurant res = restuarants.get(resname);
			String[] blist = base.split(" ");
			for(String s:blist){
				if(s.equals("环境"))
				{	
					String envir = res.getEnvironment().split("：")[1];
					if((envir!="")&&(envir!=null))
						environment = Float.parseFloat(envir);
				}
				else if(s.equals("味道"))
				{
					String fla = res.getFlavor().split("：")[1];
					if((fla!="")&&(fla!=null))
						flavor = Float.parseFloat(fla);
				}
				else if(s.equals("服务"))
				{
					if(res.getService()!=null)
						
					{	
						if(res.getService().split("：").length==2)
						{String ser = res.getService().split("：")[1];
						//if((ser!="")&&(ser!=null))
						service = Float.parseFloat(ser);}
					}
				}
				else if(s.equals("价格"))
				{
					String pri = res.getAveragePrice().split("：")[1];
					if(pri.equals("-")||pri.equals("0")){
						price = 0;
					}
					else{
						price = 100/Float.parseFloat(pri.split("元")[0]);
					}
				}
				else if(s.equals("高档"))
				{
					String pri = res.getAveragePrice().split("：")[1];
					if(pri.equals("-")){
						gaodang = 0;
					}
					else{
						gaodang = Float.parseFloat(pri.split("元")[0])/10;
					}
				}
			}
			score = environment + flavor + service + price + gaodang;
			return score;
		}
	}
	
	/*
	 * 计算标题评分
	 */
	public float tilscore(String tags, String resname){
		float score = 0;
		String[] list = tags.split(" ");
		for(String s : list){
			s = s.split(":")[0];
			if(resname.contains(s))
			{
				score += 1.0;
			}
		}
		return score;
	}
	
	/*
	 * 计算评价评分
	 */
	public float revscore(String tags, String resname){
		float score = 0;
		if(restuarants.get(resname) == null)
		{return score;}
		else
		{
			String[] list = tags.split(" ");
			String review = restuarants.get(resname).getReview() + restuarants.get(resname).getSummary();
			if(exreview.get(resname)!=null)
			{
				review = review + exreview.get(resname);
			}
			float[] strs = new float[list.length-1];
			float queryscore = count_StringContain(review, list[0])*5000;
			
			for(int i = 1; i < list.length; i++){
				String word = list[i].split(":")[0];
				float weight = Float.parseFloat(list[i].split(":")[1]);
				strs[i-1] = count_StringContain(review, word)*weight;
			}
			float conum = min(strs);
			if(list[0].equals("能用微信支付")&&resname.contains("东北老赵烧烤"))
			{
				for(float s:strs)
					System.out.println(s);
				System.out.println(conum);
			}
				
			float sum = 0;
			for(float i:strs){
				sum = sum + i;
			}
			//tag全部出现给100权值
			score = conum*1000 + sum*10 + queryscore;
			return score;
			
		}
	}
	
	public static void main(String[] args) throws Exception, IOException {
		// TODO Auto-generated method stub
		
		Score score = new Score();
		score.restuarants = score.getallifo("sourcefile/data/serialization", "sourcefile/lackresname.txt");
		System.out.println("完成序列化文件的读取");
		score.exreview = score.getexreview("sourcefile/data/review");
		System.out.println("完成额外评论的读取");
		
		//score.process("sourcefile/restaurant.TESTSET.txt", "sourcefile/restaurant_all_problem.txt", "sourcefile/result.txt", false);
		score.process("sourcefile/restaurant.TESTSET.txt", "sourcefile/restaurant_all_problem.txt", "sourcefile/result_score.txt", true);

//		score.process("sourcefile/train_rest.txt", "sourcefile/train_problem.txt", "sourcefile/train_result.txt", true);
//		CompareTrain accury = new CompareTrain();
//		accury.compare("sourcefile/restaurant.TRAINSET.txt", "sourcefile/train_result.txt");
		
		Date date=new Date();
		DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println(format.format(date));
	}
	
	
	/*
	 * 将所有餐馆全部读取，存入hashmap中，存入时，把餐馆名中的:换成空格；
	 */
	public HashMap<String, restaurant> getallifo(String respath,String lackfilepath)throws Exception, IOException{
		HashMap<String, restaurant> hm = new HashMap<String, restaurant>();
		File floder = new File(respath);
		File[] flist = floder.listFiles();
		for(File f :flist){
			String name = f.getName().split(".txt")[0];
			String path = f.getAbsolutePath();
			//System.out.println(path);
			restaurant rest = DeserializeRestaurant(path);
			hm.put(name, rest);
		}
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(lackfilepath), "utf-8"));
		String temp = null;
		while((temp = br.readLine())!=null){
			restaurant res = new restaurant();
			res.setName(temp.replace(":", " "));
			hm.put(temp, res);
		}
		br.close();
		return hm;
	}
	
	/*
	 * 反序列化读入给定文件的餐馆内容
	 */
	private static restaurant DeserializeRestaurant(String path) throws Exception, IOException {
        try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(
			        new File(path)));
			   
			//System.out.println(ois);
			restaurant res = (restaurant) ois.readObject();
			ois.close();
			return res;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		return null;
	}

	/*
	 * 将额外的评论存入单独的hashmap
	 */
	 public HashMap<String, String> getexreview(String folderpath)throws IOException{
		 HashMap<String, String> hm = new HashMap<String, String>();
		 File folder = new File(folderpath);
		 File[] list = folder.listFiles();
		 for(File f: list){
			String content = "";
			String name = f.getName().replace(".txt", "");
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f), "utf-8"));
			String temp = null;
			while((temp = br.readLine())!=null){
				content = content + temp;
			}
			br.close();
			hm.put(name, content);
		 }
		 return hm;
	 }

	 private static int count_StringContain(String sentence,String str){//统计一个字符串中出现字符串str的次数(见一个删一个，看删几次)
		 int count=0;
		 if((str!="")&&(sentence!= null)){
			 //System.out.println(str + " " + sentence);
			 count = sentence.split(str).length-1;
		}
		return count;
	   }

	/*
	 * 计算最小值
	 */
	public float min(float[] nums) {
		float min = nums[0];
		for(float i : nums){
			if(i< min)
				min = i;
		}
		return min;
	}



}
