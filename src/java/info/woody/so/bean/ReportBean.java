package info.woody.so.bean;

public class ReportBean extends BaseBean {
	private String name;
	private String sql;
	private String labels;
	private String filter;
	public String getName() {
		return name;
	}
	public String getSql() {
		return sql;
	}
	public String getLabels() {
		return labels;
	}
	public String getFilter() {
		return filter;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setSql(String sql) {
		this.sql = sql;
	}
	public void setLabels(String labels) {
		this.labels = labels;
	}
	public void setFilter(String filter) {
		this.filter = filter;
	}
}
