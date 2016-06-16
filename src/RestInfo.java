import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;

public class RestInfo {

	public static void main(String[] args) throws Exception, IOException{
		// TODO Auto-generated method stub
		String inname = "欧巴欧巴韩国年糕火锅(天津 津南区咸水沽月坛商厦)";
		String restname = inname.replace(":", " ");
		File folder = new File("sourcefile/data/serialization");
		File[] list = folder.listFiles();
		for(File f: list){
			if(f.getName().contains(restname))
			{
				restaurant res = DeserializeRestaurant(f.getAbsolutePath());
				System.out.println(res.getName());
				System.out.println(res.getAveragePrice());
				System.out.println(res.getEnvironment());
				System.out.println(res.getFlavor());
				System.out.println(res.getService());
				System.out.println(res.getReview());
				System.out.println(res.getSummary());
				String ss = res.getReview() + res.getSummary();
				System.out.println(count_StringContain(ss, "电视"));
				System.out.println(count_StringContain(ss, "安静"));
				System.out.println(count_StringContain(ss, "舒适"));

			}
		}
		
	}
	
	 private static int count_StringContain(String sentence,String str){//统计一个字符串中出现字符串str的次数(见一个删一个，看删几次)
		 int count=0;
		 // System.out.println(str);
		 if((str!="")&&(sentence!= null)){
			 count = sentence.split(str).length-1;
		 }
		 return count;
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
