package info.woody.so.bean;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class UserBean extends BaseBean {
	private String username;
	private String pwd;
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
	private Date pwdCreateAt;
	private int pwdExpDays;
	private String status;
	private String role;
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
	private Date birth;
	private String gender;
	private String idCard;
	public String getUsername() {
		return username;
	}
	public String getPwd() {
		return pwd;
	}
	public Date getPwdCreateAt() {
		return pwdCreateAt;
	}
	public int getPwdExpDays() {
		return pwdExpDays;
	}
	public String getStatus() {
		return status;
	}
	public String getRole() {
		return role;
	}
	public Date getBirth() {
		return birth;
	}
	public String getGender() {
		return gender;
	}
	public String getIdCard() {
		return idCard;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public void setPwdCreateAt(Date pwdCreateAt) {
		this.pwdCreateAt = pwdCreateAt;
	}
	public void setPwdExpDays(int pwdExpDays) {
		this.pwdExpDays = pwdExpDays;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public void setBirth(Date birth) {
		this.birth = birth;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	
	
}