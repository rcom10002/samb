package info.woody.so.bean;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class BookBean extends BaseBean {
	private String name          ;
	private String category      ;
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
	private Date   dateOfIssue   ;
	private String summary       ;
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
	private Date   dateOfPurchase;
	
	public String getName() {
		return name;
	}
	public String getCategory() {
		return category;
	}
	public Date getDateOfIssue() {
		return dateOfIssue;
	}
	public String getSummary() {
		return summary;
	}
	public Date getDateOfPurchase() {
		return dateOfPurchase;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public void setDateOfIssue(Date dateOfIssue) {
		this.dateOfIssue = dateOfIssue;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public void setDateOfPurchase(Date dateOfPurchase) {
		this.dateOfPurchase = dateOfPurchase;
	}

}