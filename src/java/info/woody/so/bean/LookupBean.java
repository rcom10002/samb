package info.woody.so.bean;

public class LookupBean extends BaseBean {
	private String category;
	private String name    ;
	private String val     ;
	private String weight  ;
	public LookupBean() { }
	public String getCategory() {
		return category;
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
	public void setCategory(String category) {
		this.category = category;
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
	@Override
	public String toString() {
		return "LookupBean [category=" + category + ", name=" + name + ", val="
				+ val + ", weight=" + weight + ", id=" + id + ", createBy="
				+ createBy + ", createAt=" + createAt + ", updateBy="
				+ updateBy + ", updateAt=" + updateAt + "]";
	}

}