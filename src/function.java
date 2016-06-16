import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.util.HashSet;

public class function {

	//匹配结果中哪一行的首餐厅得分为0
	public static void euqal0(String path) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path), "gbk"));
		String temp = null;
		int line = 0;
		while((temp=br.readLine())!=null){
			line++;
			String s = temp.split("\t")[1].split(":")[2];
			if(s.equals("0.0"))
				System.out.println(line);
		}
		br.close();
	}
	
	//匹配标记的问题中的格式是否错误
	public static void euqalguize(String path)throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path), "utf-8"));
		String temp = null;
		int linenum = 0;
		while((temp = br.readLine())!=null){
			linenum++;
			String[] list = temp.split(" ");
			for(int i = 1;i < list.length;i++){
				if(list[i].split(":").length!=2)
				{	System.out.println(list[i]);
					System.out.println(linenum + ":" + false);
				}
			}
		}
		br.close();
	}
	
	/*
	 * 获取给定的餐馆中，序列化文件缺少的餐馆名字
	 */
	public static void lackfile()throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("sourcefile/restaurant.ENTITYSET.txt"), "gbk"));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("sourcefile/lackresname.txt"), "utf-8"));
		File folder = new File("sourcefile/data/serialization");
		File[] list = folder.listFiles();
		HashSet<String> listset = new HashSet<String>();
		for(File f:list){
			String name = f.getName().split(".txt")[0];
			listset.add(name);
		}
		String temp = null;
		while((temp = br.readLine())!=null){
			String name = temp.replace(":", " ");
			if(!listset.contains(name))
			{
				bw.write(temp);
				bw.write("\n");
			}
		}
		bw.close();

	}
	
	public static void main(String[] args) throws Exception, IOException{
		// TODO Auto-generated method stub
		
		//split("sourcefile/restaurant.TESTSET.txt", "sourcefile/restaurant.test_600.txt", "sourcefile/restaurant.test_400.txt");
		
		//euqal0("sourcefile/result_score.txt");
		
		euqalguize("sourcefile/restaurant_all_problem.txt");
		
		
/*		String line = null;
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("sourcefile/result_score.txt"), "gbk"));
		String temp = null;
		int count = 0;
		while((temp = br.readLine())!=null){
			count++;
			if(count == 644)
				line = temp;
		}
		br.close();
		mergeline(line, "sourcefile/mergeline.txt");
*/
		
		//merge("sourcefile/data/basic_info","sourcefile/merge.txt");

	}

	/*
	 * 拆分文件为600：400
	 */
	public static void split(String path,String outpath1,String outpath2) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path), "gbk"));
		BufferedWriter bw_1 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outpath1), "GB18030"));
		BufferedWriter bw_2 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outpath2), "GB18030"));
		String temp = null;
		int count = 0;
		while((temp = br.readLine())!=null){
			count++;
			if(count<601)
			{
				bw_1.write(temp);
				bw_1.newLine();
			}
			else{
				bw_2.write(temp);
				bw_2.newLine();
			}
		}
		br.close();
		bw_1.close();
		bw_2.close();
		
	}
	
	/*
	 * 合并给定文件夹下的所有文件内容
	 */
	public static void merge(String folderpath, String outpath)throws IOException{
		File folder = new File(folderpath);
		File[] list = folder.listFiles();
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outpath),"utf-8"));
		for(File f:list){
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f), "utf-8"));
			
			String temp = null;
			int count = 0;
			while((temp = br.readLine())!=null)
			{
				count ++;
				temp = temp.replaceAll(" ", "");
				if((count > 7)&&(!temp.contains("null")))
				{
					temp = temp.replaceAll("标签为：", "");
					bw.write(temp);
					bw.newLine();
				}
			}
			br.close();
		}
		bw.close();
	}

	
	/*
	 * 合并给定字串中的餐馆对应的内容
	 */
	public static void mergeline(String line,String outpath)throws Exception, IOException{
		File folder = new File("sourcefile/data/serialization");
		File[] list = folder.listFiles();
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outpath),"utf-8"));
		String[] linelist = line.split("\t");
		for(File f:list){
			for(int i =1;i<linelist.length;i++){
				if(f.getName().contains(linelist[i].split("\\)")[0].replace(":", " ")))
				{
					restaurant res = DeserializeRestaurant(f.getAbsolutePath());
					bw.write(res.getName());
					//bw.write(res.getAveragePrice());
					bw.newLine();
					if(res.getReview()!=null)
						bw.write(res.getReview());
						bw.newLine();
				}
			}
		}
		bw.close();

	}

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


}
