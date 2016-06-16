
import java.io.Serializable;


public class restaurant implements Serializable{
	private static final long serialVersionUID = -5182532647273106745L;
	 public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getAddress() {
		return Address;
	}
	public void setAddress(String address) {
		Address = address;
	}
	public String getTel() {
		return Tel;
	}
	public void setTel(String tel) {
		Tel = tel;
	}
	public String getAveragePrice() {
		return AveragePrice;
	}
	public void setAveragePrice(String averagePrice) {
		AveragePrice = averagePrice;
	}
	public String getFlavor() {
		return Flavor;
	}
	public void setFlavor(String flavor) {
		Flavor = flavor;
	}
	public String getEnvironment() {
		return Environment;
	}
	public void setEnvironment(String environment) {
		Environment = environment;
	}
	public String getService() {
		return service;
	}
	public void setService(String service) {
		this.service = service;
	}
	public String getReview() {
		return review;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public void setReview(String review2) {
		this.review=review2;		
	}
	public String getReview_quantity() {
		return review_quantity;
	}
	public void setReview_quantity(String review_quantity) {
		this.review_quantity = review_quantity;
		
	}
	public String getScore() {
		return Score;
	}
	public void setScore(String score) {
		Score = score;
	}
	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	
	private String Name,Score,Address,Tel,review_quantity,AveragePrice,Flavor,Environment,service,review,tags,summary;


	

}
