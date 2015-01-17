package info.woody.so.bean;

public class RequirementBean extends BaseBean {
	private String corp;
	private String location;
	private String target;
	private String manager;
	private String position;
	private int quantity;
	private String status;
	private String hr;
	private String comment;
	public RequirementBean() { }
	public String getCorp() {
		return corp;
	}
	public String getLocation() {
		return location;
	}
	public String getTarget() {
		return target;
	}
	public String getManager() {
		return manager;
	}
	public String getPosition() {
		return position;
	}
	public int getQuantity() {
		return quantity;
	}
	public String getStatus() {
		return status;
	}
	public String getHr() {
		return hr;
	}
	public String getComment() {
		return comment;
	}
	public void setCorp(String corp) {
		this.corp = corp;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public void setManager(String manager) {
		this.manager = manager;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public void setHr(String hr) {
		this.hr = hr;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	@Override
	public String toString() {
		return "RequirementBean [corp=" + corp + ", location=" + location
				+ ", target=" + target + ", manager=" + manager + ", position="
				+ position + ", quantity=" + quantity + ", status=" + status
				+ ", hr=" + hr + ", comment=" + comment + ", id=" + id
				+ ", createBy=" + createBy + ", createAt=" + createAt
				+ ", updateBy=" + updateBy + ", updateAt=" + updateAt + "]";
	}

}