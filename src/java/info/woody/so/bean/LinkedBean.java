package info.woody.so.bean;

public class LinkedBean extends BaseBean {
	private String entity;
	private String name  ;
	private String val   ;
	private String weight;
	public LinkedBean() { }
	public String getEntity() {
		return entity;
	}
	public String getName() {
		return name;
	}
	public String getVal() {
		return val;
	}
	public String getWeight() {
		return weight;
	}
	public void setEntity(String entity) {
		this.entity = entity;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setVal(String val) {
		this.val = val;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}

}