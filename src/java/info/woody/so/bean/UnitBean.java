package info.woody.so.bean;

public class UnitBean extends BaseBean {
	
	private String name    ;
	private String status  ;
	private String category;
	private String remark  ;
	private String parentUnit  ;
	private String _parentUnit;
	private int parentUnitId;
	public String getName() {
		return name;
	}
	public String getStatus() {
		return status;
	}
	public String getCategory() {
		return category;
	}
	public String getRemark() {
		return remark;
	}
	public int getParentUnitId() {
		return parentUnitId;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getParentUnit() {
		return parentUnit;
	}
	public void setParentUnit(String parentUnit) {
		this.parentUnit = parentUnit;
	}
	public String get_parentUnit() {
		return _parentUnit;
	}
	public void setParentUnitId(int parentUnitId) {
		this.parentUnitId = parentUnitId;
	}
}