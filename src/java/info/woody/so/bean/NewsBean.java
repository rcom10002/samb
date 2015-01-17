package info.woody.so.bean;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class NewsBean extends BaseBean {
	private String title      ;
	private String category   ;
	private String author     ;
	private String mainBody   ;
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
	private Date   dateOfIssue;
	
	public String getTitle() {
		return title;
	}
	public String getCategory() {
		return category;
	}
	public String getAuthor() {
		return author;
	}
	public String getMainBody() {
		return mainBody;
	}
	public Date getDateOfIssue() {
		return dateOfIssue;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public void setMainBody(String mainBody) {
		this.mainBody = mainBody;
	}
	public void setDateOfIssue(Date dateOfIssue) {
		this.dateOfIssue = dateOfIssue;
	}

}