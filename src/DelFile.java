import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;

import javax.swing.InputMap;
/*
 * 删除序列化文件和本地中的无效文件
 */
public class DelFile {

	public static void delbase(String basepath) throws IOException{
		File folder = new File(basepath);
		File[] list = folder.listFiles();
		int count = 0;
		for(File f: list){
			
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f), "utf-8"));
			br.readLine();
			br.readLine();
			br.readLine();
			br.readLine();
			String temp = br.readLine();
			br.close();
			if(temp.equals("null")){
				f.delete();
				count++;
			}
			
		}
		System.out.println(count);

	}

	
	public static void delserl(String serlpath) throws Exception, IOException{
		File folder = new File(serlpath);
		File[] list = folder.listFiles();
		int count = 0;
		for(File f : list){
			
			String fpath = f.getAbsolutePath();
			//System.out.println(fpath);
			
			restaurant resf = DeserializeRestaurant(fpath);
			if(resf.getEnvironment()==null){
				f.delete();
				count++;
			}
		}
		System.out.println(count);
	}

	private static restaurant DeserializeRestaurant(String path) throws Exception, IOException {
        try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(
			        new File(path)));
			   
//			System.out.println(ois);
			restaurant res = (restaurant) ois.readObject();
			ois.close();
			return res;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
        
		return null;
	}

	public static void equalf() throws IOException{
		String serpath = "C:/JavaWorkspace/restaurantReptile/sourcefile/餐馆数据/餐馆爬取结果/basic_info";
		String basepath = "C:/JavaWorkspace/restaurantReptile/sourcefile/餐馆数据/餐馆爬取结果/serialization";
		File base = new File(basepath);
		File ser = new File(serpath);
		File[] baselist = base.listFiles();
		File[] serlist = ser.listFiles();
		for(File bf:baselist){
			boolean flag = false;
			for(File sf:serlist){
				if(bf.getName().equals(sf.getName()))
					flag = true;
			}
			if(!flag)
				bf.delete();
				System.out.println(bf.getName());
		}
	}
	
	public static void delexreview(String path) throws IOException{
		File folder = new File(path);
		File[] list = folder.listFiles();
		int num = 0;
		for(File f:list){
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f), "utf-8"));
			String temp = null;
			int line = 0;
			while((temp = br.readLine())!=null){
				line++;
			}
			br.close();
			if(line==0)
			{
				f.delete();
				num++;
			}
		}
		System.out.println(num);
	}
	public static void main(String[] args) throws Exception, IOException {
		// TODO Auto-generated method stub
//		String begin = "C:/JavaWorkspace/restaurantReptile/sourcefile/origin/444/餐馆爬取104/餐馆爬取/";
//		String base = begin + "basic_info";
//		String serl = begin + "serialization";
//		delbase(base);
//		delserl(serl);
//		equalf();
		delexreview("sourcefile/origin/666/review_104/review");
	}

}
