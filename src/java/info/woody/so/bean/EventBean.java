package info.woody.so.bean;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class EventBean extends BaseBean {
	private String subject    ;
	private String category   ;
	private String host       ;
	private String participant;
	private String content    ;
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
	private Date   startAt    ;
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
	private Date   endAt      ;
	
	public String getSubject() {
		return subject;
	}
	public String getCategory() {
		return category;
	}
	public String getHost() {
		return host;
	}
	public String getParticipant() {
		return participant;
	}
	public String getContent() {
		return content;
	}
	public Date getStartAt() {
		return startAt;
	}
	public Date getEndAt() {
		return endAt;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public void setParticipant(String participant) {
		this.participant = participant;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public void setStartAt(Date startAt) {
		this.startAt = startAt;
	}
	public void setEndAt(Date endAt) {
		this.endAt = endAt;
	}

}