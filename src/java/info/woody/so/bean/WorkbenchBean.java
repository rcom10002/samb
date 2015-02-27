package info.woody.so.bean;

import org.apache.commons.lang.StringUtils;


public class WorkbenchBean extends BaseBean {

	private String sqls = null;

	public String[] getSqls() {
		if (StringUtils.isEmpty(sqls) || sqls.trim().length() == 0) {
			return new String[0];
		}
		return sqls.split(";");
	}

	public void setSqls(String sqls) {
		this.sqls = sqls;
	}

	public boolean isValid(String sql) {
		if (StringUtils.isEmpty(sql)) {
			return false;
		}
		return (sql.replaceAll("\\s|\\r|\\n", "").length() == 0) ? false : true;
	}

	public boolean isRetrieving(String sql) {
		return sql.matches("(?is)^(\\s|\\r?\\n)*?(select|values).+$");
	}

	public static class WorkbenchFeedbackBean {
		private Object payload;

		public WorkbenchFeedbackBean(Object payload) {
			super();
			this.payload = payload;
		}

		public Object getPayload() {
			return payload;
		}

		public void setPayload(Object payload) {
			this.payload = payload;
		}
	}
}