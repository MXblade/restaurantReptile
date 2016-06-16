import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class ExtraProblem {

	//提取文件问题
	public static void extra(String inpath, String outpath) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(inpath), "gbk"));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outpath), "utf-8"));
		String temp = null;
		while((temp = br.readLine())!=null){
			bw.write(temp.split("\t")[0]);
			bw.newLine();
		}
		bw.close();
		br.close();
				
	}
	
	/*
	 * 处理训练语料，将答案去除掉
	 */
	public static void dealtrain(String outpath)throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("sourcefile/restaurant.TRAINSET.txt"), "gbk"));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outpath), "GB18030"));
		String temp = null;
		while((temp = br.readLine())!=null){
			String[] list = temp.split("\t");
			for(String s:list){
				if(s.contains(")"))
					s = s.split("\\):")[0];
				bw.write(s + ")" + "\t");
			}
			bw.newLine();
		}
		bw.close();
		br.close();
	}
	public static void main(String[] args)throws IOException{
		extra("sourcefile/restaurant.test_600.txt", "sourcefile/restaurant_600_problem.txt");
		//dealtrain("sourcefile/train_rest.txt");
	}
}
