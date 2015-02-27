package info.woody.so.bean;

public class FamilyBean extends BaseBean {
	private String name;
	private int    age ;
	public String getName() {
		return name;
	}
	public int getAge() {
		return age;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setAge(int age) {
		this.age = age;
	}


}