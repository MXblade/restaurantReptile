import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Find_ms_Prob {

	public static void find(float score)throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("sourcefile/result_score.txt"), "gbk"));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("sourcefile/Min_Score_Prob.txt"),"utf-8"));
		String temp = null;
		int linenum = 0;
		while((temp = br.readLine())!=null){
			linenum++;
			String[] list = temp.split("\t");
			Float frs = Float.parseFloat(list[1].split(":")[2]);
			if(frs <= score)
			{
				System.out.println(linenum);
				bw.write(linenum + "\t" + temp);
				bw.newLine();
			}
		}
		br.close();
		bw.close();
	}
	public static void main(String[] args) throws IOException{
		// TODO Auto-generated method stub
		find(100);
	}

}
