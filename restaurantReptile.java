import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class restaurantReptile {
	
	public static boolean review(String url,restaurant res) throws IOException, InterruptedException{//获取短评		
	    Document doc = null;
	    String review="";
	    ArrayList<String> reviewUrlList = new  ArrayList<String>();
	    String page_url="";
		try {
				doc = Jsoup.connect(url)
						.header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.102 Safari/537.36")
						.header("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
						.header("Accept-Charset","ISO-8859-1,utf-8;q=0.7,*;q=0.3")
						.header("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6")
						.header("Accept-Encoding","gzip, deflate, sdch").header("Host", "www.dianping.com")
						.header("Connection","keep-alive").header("Referer",url)
						.timeout(70000).get();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				 System.out.println("餐馆查找url连接发生错误");
				 return false;
			}				
		  Elements Page = doc.select("a[class=review-num]");
		 String pagenumber="";
	      if(Page!=null){
		    page_url= Page.attr("abs:href");
		    pagenumber=Page.text();
	      }
	      Integer reviewNum = null;
	 if(!pagenumber.equals("")){
	      String temp[]=pagenumber.split(" ");	     
		try {
			reviewNum = Integer.parseInt(temp[0]);
		} catch (NumberFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
      }
	 else{ 
		  reviewNum = 0;
	 }
	      Integer totalPage=0;
	      if(reviewNum%20==0)
	    	  totalPage=reviewNum/20;
	      else totalPage=(int)(reviewNum/20)+1;
	      //每页20条评论，计算共需多少页

	      System.out.println(page_url);
	      System.out.println(totalPage);
	   	  
	    String temp2[]=page_url.split("#");
	    String urlist=temp2[0]+"/review_more?pageno=";
	    
	    for(int i=1;i<totalPage+1;i++){
	    	reviewUrlList.add(urlist+i);
	    }
	  if(reviewUrlList.size()>0){
		  try {
				doc = Jsoup.connect(url)
						.header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.102 Safari/537.36")
						.header("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
						.header("Accept-Charset","ISO-8859-1,utf-8;q=0.7,*;q=0.3")
						.header("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6")
						.header("Accept-Encoding","gzip, deflate, sdch").header("Host", "www.dianping.com")
						.header("Connection","keep-alive").header("Referer",url)
						.timeout(70000).get();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				 System.out.println("餐馆查找url连接发生错误");
				 return false;
			}				
	    Elements rate= doc.select("span[itemprop=rating]");
	    res.setScore(rate.text());
	    
	    
	    for(int i=0;i<reviewUrlList.size();i++){
	      try {
	    	  Random ran =new Random();
	  		  Thread.sleep(ran.nextInt(500));//设置随机爬取间隔防止被封；
			  doc = Jsoup.connect(reviewUrlList.get(i))
					  .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.102 Safari/537.36")
						.header("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
						.header("Accept-Charset","ISO-8859-1,utf-8;q=0.7,*;q=0.3")
						.header("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6")
						.header("Accept-Encoding","gzip, deflate, sdch").header("Host", "www.dianping.com")
						.header("Connection","keep-alive").header("Referer",url)
						.timeout(70000).get();
			  Elements commen= doc.select("div[class=J_brief-cont]");
			  for(Element com:commen){
				  review=review+com.text()+"\n";
				 
			  }
		  } catch (Exception e) {
			 // TODO Auto-generated catch block
			  System.out.println("餐馆进入url连接发生错误");
			  return false;
		  }
	    }
	    }
	    res.setReview_quantity(pagenumber);
	    res.setReview(review);
        return true;
    }
	public static boolean basic_info(String url,String name,restaurant res) throws IOException, InterruptedException{
		    res.setName(name);
		    Document doc = null;		   		    
		    String page_url="";
		    
		    Map<String, String> header = new HashMap<String, String>();
		    header.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.64 Safari/537.11");
		    header.put("Accept","text/html;q=0.9,*/*;q=0.8");
		    header.put("Accept-Charset","ISO-8859-1,utf-8;q=0.7,*;q=0.3");
		    header.put("Accept-Encoding","gzip");
		    header.put("Connection","close");
		    header.put("Referer","None");
		    
			try {
				    System.out.println(url);
					doc = Jsoup.connect(url)
						.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.64 Safari/537.11")
						.header("Accept","text/html;q=0.9,*/*;q=0.8")
						.header("Accept-Charset","ISO-8859-1,utf-8;q=0.7,*;q=0.3")
						.header("Accept-Encoding","gzip")
						.header("Accept-Language", "zh-cn,zh;q=0.5")
						.header("Connection","close").header("Referer","None")
						.timeout(70000).get();					
				} catch (Exception e) {
					// TODO Auto-generated catch block					 
					 System.out.println("餐馆查找url连接发生错误");
					 System.out.println(e);
					 return false;
				}
					
			  Elements Page = doc.select("a[class=review-num]");
		      if(Page!=null){
			    page_url= Page.attr("abs:href");
		      }
		    try {
		    	System.out.println(page_url);
				doc =  Jsoup.connect("http://www.dianping.com/shop/20823481")
						.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.64 Safari/537.11")
						.header("Accept","text/html;q=0.9,*/*;q=0.8")
						.header("Accept-Charset","ISO-8859-1,utf-8;q=0.7,*;q=0.3")
						.header("Accept-Language", "zh-cn,zh;q=0.5")
						.header("Accept-Encoding","gzip")
						.header("Connection","close").header("Referer","None")
						.timeout(70000).get();
				Elements info = doc.select("div[class=brief-info]");			
				ArrayList<String> infos=new ArrayList<String>();				
					String str[]=info.select("span[class=item]").text().split(" ");
					for(int i=0;i<str.length;i++){
				      
					  infos.add(str[i]);
					}

					
				for(int i=0;i<5;i++){
					switch(i){
					case 0:res.setReview_quantity(infos.get(i));break;
					case 1:res.setAveragePrice(infos.get(i));break;
					case 2:res.setFlavor(infos.get(i));break;
					case 3:res.setEnvironment(infos.get(i));break;
					case 4:res.setService(infos.get(i))	;break;
					}				
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block				
				 System.out.println("餐馆进入url连接发生错误");
				 System.out.println(e);
				 return false;
			}
		    
			
		Elements t= doc.select("a[rel=tag]");
		String tags="";	
		if(t!=null){
			String tag[]=t.text().split(" ");
							
			for(int i=0;i<tag.length;i++){					      
				tags=tags+tag[0]+" ";
			}
		}
		
	Elements review=doc.select("ul[class=comment-list J-list]");
	if(review!=null){
			 
		String content="";
		Elements review1= review.select("p[class=desc]");
		if(review1!=null){
		 
		 for(Element r:review1){
		 	content=content+r.text()+"\n";
		 }
		}
		Elements review2= review.select("p[class=desc J-desc]");
		 if(review2!=null){
		 for(Element r:review2){
			content=content+r.text()+"\n";
		 }
		}
		res.setReview(content);
	}
	
		Elements summary= doc.select("span[class=good J-summary]");
		String tagsSummary="";	
		 if(summary!=null){
			String sum[]=summary.text().split(" ") ;
			for(int i=0;i<sum.length;i++){					      
				tagsSummary=tagsSummary+sum[i]+" ";
			}
		 }
		 
		 res.setTags(tags);
		 res.setSummary(tagsSummary);
		    return true;
	}
	
	 public static void saveToLocal(restaurant r,String name,String filepath) throws IOException{
		 File mkdir1 =new File(filepath+"\\basic_info");if(!mkdir1.exists())  mkdir1.mkdir();	  
		 File mkdir2 =new File(filepath+"\\review");if(!mkdir2.exists())  mkdir2.mkdir();
		 
		 FileWriter info = new FileWriter(filepath+"\\basic_info\\"+name+".txt");
		 FileWriter review = new FileWriter(filepath+"\\review\\"+name+".txt");
		 
		 try {
			review.write(r.getReview());
			 review.close();
		 } catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			 try {
				info.write(r.getName()+"\n");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 try {
				info.write(r.getScore()+"\n");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 try {
				info.write(r.getReview_quantity()+"\n");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 try {
				info.write(r.getAveragePrice()+"\n");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 try {
				info.write(r.getEnvironment()+"\n");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 try {
				info.write(r.getFlavor()+"\n");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 try {
				info.write(r.getService()+"\n");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 try {
					info.write("标签为: "+r.getTags()+"\n");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			 try {
					info.write("大家认为: "+r.getSummary()+"\n");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			 try {
					info.write("评论: "+"\n"+r.getReview());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			 info.close();
		
	 }
	 
	 public static void SerializeResraurant(restaurant r,String name,String filepath) throws FileNotFoundException, IOException {
		 File mkdir3 =new File(filepath+"\\serialization");if(!mkdir3.exists())  mkdir3.mkdir();
	     ObjectOutputStream oo = null;
			try {
				oo = new ObjectOutputStream(new FileOutputStream(
				         new File(filepath+"\\serialization\\"+name+".txt")));
				 oo.writeObject(r);
				 System.out.println("restaurant对象序列化成功！");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	   oo.close();
	  }
	 public static String filterword(String str){//过滤看不见的字符，呵呵哒
	 String strs="";
	 char[] chars = str.toCharArray();  
        for(int i = 0; i < chars.length; i ++) {  
            if((chars[i] >= 19968 && chars[i] <= 40869) || (chars[i] >= 48 && chars[i] <= 57) || (chars[i] >= 97 && chars[i] <= 122)|| (chars[i] >= 65 && chars[i] <= 90)) {  //提取 汉字、数字、小写字母、大写字母
            	strs=strs+chars[i];
            	//System.out.print(chars[i]);  
            }  
        }
        return strs;
      }
	 
	 public static void main(String[] args) throws IOException, InterruptedException {
		
		String cityNum="cityid.txt";		
	    String restaurantIDPath="restaurant.ENTITYSET.txt";
	    String loacalPath="C:\\餐馆爬取结果";
	    String url="http://www.dianping.com/search/keyword/";
		String content1="";
		String content2="";
		String content3="";
		HashMap<String,String> cityID  = new  HashMap<String,String>();
		
		BufferedReader  reader1 = new BufferedReader(new FileReader(cityNum));  
		
		while((content1=reader1.readLine())!=null){
			
				  String temp[]=content1.split(" ");				  
				  cityID.put(filterword(temp[0]), temp[1]);
		}
		reader1.close();
		
		ArrayList<String> restaurant_ENTITYSET = new ArrayList<String>();
		BufferedReader  reader2 = new BufferedReader(new FileReader(restaurantIDPath));  
		while((content2=reader2.readLine())!=null){
			restaurant_ENTITYSET.add(content2);
		}
		
		for(int i=15097;i<20000;i++){//控制爬取范围，一共54539个餐馆
			Random ran =new Random();
	  		  Thread.sleep(ran.nextInt(500));//设置随机爬取间隔防止被封；
			  content3=restaurant_ENTITYSET.get(i);
			 String ENTITYSET=content3.replace(":", " ").replace("：", " ").replace("*", " ").replace("?", " ").replace("/", " ").replace("\\", " ").replace("|", " ");
			  System.out.println(content3);
			  String name[]=content3.split("\\(");
				int p1=content3.indexOf("(");
				int p2=content3.indexOf(":",p1);				
				String city=content3.substring(p1+1, p2);	
				System.out.println(city);
				String urlcode= URLEncoder.encode(name[0]);	
				String cityNumber=cityID.get(city);				
				String myurl = url + cityNumber + "/0_" + urlcode;
				restaurant res = new restaurant();
				try {	
					basic_info(myurl,ENTITYSET,res);
					//review(myurl,res);
					saveToLocal(res,ENTITYSET,loacalPath);
					SerializeResraurant(res,ENTITYSET,loacalPath);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		reader2.close();
		
	/*
	    String urlcode= URLEncoder.encode("鸡本部脆皮鸡排(上海:徐汇区天等路)");
		url=url+"1/0_"+urlcode;
       // url="http://www.dianping.com/search/keyword/1/0_%E9%B8%A1%E6%9C%AC%E9%83%A8%E8%84%86%E7%9A%AE%E9%B8%A1%E6%8E%92(%E4%B8%8A%E6%B5%B7%3A%E5%BE%90%E6%B1%87%E5%8C%BA%E5%A4%A9%E7%AD%89%E8%B7%AF)";
		restaurant res = new restaurant(); 
		try {			
			basic_info(url,"鸡本部脆皮鸡排",res);
			review(url,res);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(res.getName());
		System.out.println(res.getReview_quantity());
	  */
	 }
}