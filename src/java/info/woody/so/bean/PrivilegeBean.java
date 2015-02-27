package info.woody.so.bean;

public class PrivilegeBean extends BaseBean {
	private String name       ;
	private String source     ;
	private String description;
	private String control    ;
	private String status     ;
	public String getName() {
		return name;
	}
	public String getSource() {
		return source;
	}
	public String getDescription() {
		return description;
	}
	public String getControl() {
		return control;
	}
	public String getStatus() {
		return status;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setControl(String control) {
		this.control = control;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}