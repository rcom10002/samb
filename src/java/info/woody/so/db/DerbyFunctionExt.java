package info.woody.so.db;

import org.apache.commons.lang.StringUtils;

public class DerbyFunctionExt {

	public static String replace(String target, String match, String replacement) {
		if (target == null) {
			return null;
		}
		return StringUtils.isEmpty(target) ? "" : target.replace(match,
				replacement);
	}

	public static String regex_replace(String target, String match,
			String replacement) {
		if (target == null) {
			return null;
		}
		return StringUtils.isEmpty(target) ? "" : target.replaceAll(match,
				replacement);
	}

	public static long getTimeOfLongType(java.sql.Timestamp ts) {
		return null == ts ? null : ts.getTime();
	}
}
