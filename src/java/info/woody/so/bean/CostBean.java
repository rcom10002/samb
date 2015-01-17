package info.woody.so.bean;

public class CostBean extends BaseBean {
	private String category;
	private String name;
	private String applicant;
	private String status;
	private Double amount;
	private String detail;
	private String memo;
	private String mailEvidence;
	public String getCategory() {
		return category;
	}
	public String getName() {
		return name;
	}
	public String getApplicant() {
		return applicant;
	}
	public String getStatus() {
		return status;
	}
	public Double getAmount() {
		return amount;
	}
	public String getDetail() {
		return detail;
	}
	public String getMemo() {
		return memo;
	}
	public String[] get_mailEvidence() {
		if (null == this.mailEvidence || 0 == this.mailEvidence.length()) {
			return new String[0];
		}
		return this.mailEvidence.split("/");
	}
	public String getMailEvidence() {
		return mailEvidence;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setApplicant(String applicant) {
		this.applicant = applicant;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public void setMailEvidence(String mailEvidence) {
		this.mailEvidence = mailEvidence;
	}

}