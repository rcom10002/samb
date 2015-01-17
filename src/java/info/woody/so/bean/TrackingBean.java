package info.woody.so.bean;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class TrackingBean extends BaseBean {
	private String _toRequirement   ;
	private int    toRequirement    ;
	private String candidate        ;
	private String phone            ;
	private String hr               ;
	private String workLocation     ;
	private String skill            ;
	private int    experience       ;
	private String language         ;
	private String employer         ;
	private String level            ;
	private String remarks          ;
    //@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Shanghai")
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
	private Date   toSubmitResumeAt ;
	private String interviewResult  ;
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
	private Date   interviewTime    ;
	private int    expectedSalary   ;
	//private String _files           ;
	private String files            ;
	
	public TrackingBean() { }
	public String get_toRequirement() {
		return this._toRequirement;
	}
	public int getToRequirement() {
		return toRequirement;
	}
	public String getCandidate() {
		return candidate;
	}
	public String getPhone() {
		return phone;
	}
	public String getHr() {
		return hr;
	}
	public String getWorkLocation() {
		return workLocation;
	}
	public String getSkill() {
		return skill;
	}
	public int getExperience() {
		return experience;
	}
	public String getLanguage() {
		return language;
	}
	public String getEmployer() {
		return employer;
	}
	public String getLevel() {
		return level;
	}
	public String getRemarks() {
		return remarks;
	}
	public Date getToSubmitResumeAt() {
		return toSubmitResumeAt;
	}
	public String getInterviewResult() {
		return interviewResult;
	}
	public Date getInterviewTime() {
		return interviewTime;
	}
	public int getExpectedSalary() {
		return expectedSalary;
	}
	public String[] get_files() {
		if (null == this.files || 0 == this.files.length()) {
			return new String[0];
		}
		return this.files.split("/");
	}
	public String getFiles() {
		return files;
	}
	public void setToRequirement(int toRequirement) {
		this.toRequirement = toRequirement;
	}
	public void setCandidate(String candidate) {
		this.candidate = candidate;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public void setHr(String hr) {
		this.hr = hr;
	}
	public void setWorkLocation(String workLocation) {
		this.workLocation = workLocation;
	}
	public void setSkill(String skill) {
		this.skill = skill;
	}
	public void setExperience(int experience) {
		this.experience = experience;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public void setEmployer(String employer) {
		this.employer = employer;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public void setToSubmitResumeAt(Date toSubmitResumeAt) {
		this.toSubmitResumeAt = toSubmitResumeAt;
	}
	public void setInterviewResult(String interviewResult) {
		this.interviewResult = interviewResult;
	}
	public void setInterviewTime(Date interviewTime) {
		this.interviewTime = interviewTime;
	}
	public void setExpectedSalary(int expectedSalary) {
		this.expectedSalary = expectedSalary;
	}
	public void setFiles(String files) {
		this.files = files;
	}
}