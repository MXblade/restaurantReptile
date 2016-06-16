import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;

public class CompareTrain {

	/*
	 * 计算训练语料和结果的准确度
	 */
	public static void compare(String trainpath, String resultpath)throws IOException{
		BufferedReader tbr = new BufferedReader(new InputStreamReader(new FileInputStream(trainpath), "gbk"));
		BufferedReader rbr = new BufferedReader(new InputStreamReader(new FileInputStream(resultpath), "gbk"));
		
		String temp1 = null;
		String temp2 = null;
		int line = 0;
		float sum = 0;
		while(((temp1 = tbr.readLine())!=null)&&((temp2 = rbr.readLine())!=null)){
			line ++;
			HashSet<String> trainrest = new HashSet<String>();
			String[] trainlist = temp1.split("\t");
			for(String s: trainlist){
				if(s.contains(":")&&(s.split("\\):")[1].equals("1"))){
					trainrest.add(s.split("\\):")[0] + ")");
				}
			}
			//String[] resultrest = new String[trainrest.size()];
			String[] resultlist = temp2.split("\t");
			Double count = 0.0;
			for(int i = 1; i < trainrest.size() + 1; i++){
				if(trainrest.contains(resultlist[i]))
					count +=1.0;
//				if(trainrest.contains(resultlist[i].split("\\):")[0] + ")"))
//					count += 1.0;
			}
			float bili = (float) (count/trainrest.size());
			sum = sum + bili;
			System.out.println(line + ":" +count + ":" + bili);
		}
		System.out.println("sum:" + sum);
		
	}
	public static void main(String[] args) throws IOException{
		// TODO Auto-generated method stub
		compare("sourcefile/tvShow.TRAINSET.txt", "sourcefile/TV_movie_train_result_6_13.txt");
	}

}
