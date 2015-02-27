package info.woody.so.bean;

public class ReportBean extends BaseBean {
	private String category;
	private int qty;
	private double avgWeight;
	public String getCategory() {
		return category;
	}
	public int getQty() {
		return qty;
	}
	public double getAvgWeight() {
		return avgWeight;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public void setQty(int qty) {
		this.qty = qty;
	}
	public void setAvgWeight(double avgWeight) {
		this.avgWeight = avgWeight;
	}
	
}
