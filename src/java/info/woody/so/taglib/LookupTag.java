package info.woody.so.taglib;

import info.woody.so.bean.LookupBean;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

public class LookupTag extends SimpleTagSupport {

	private static Map<String, List<LookupBean>> lookupListCache = new HashMap<String, List<LookupBean>>();
	public static void reload(SqlSessionFactory sqlSessionFactory) {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		List<String> categoryList = sqlSession.selectList("selectLookupForCategoryOnly");
		for (String category : categoryList) {
			List<LookupBean> lookupList = sqlSession.selectList("selectLookupByCategory", category);
			lookupListCache.put(category, lookupList);
		}
		sqlSession.clearCache();
		sqlSession.close();
	}

	private String category;

	public void setCategory(String category) {
		this.category = category;
	}

	/*
	 * (non-Javadoc)
	 * @see javax.servlet.jsp.tagext.SimpleTagSupport#doTag()
	 * 
	 * http://www.tutorialspoint.com/jsp/jsp_custom_tags.htm
	 * http://www.codeproject.com/Articles/31614/JSP-JSTL-Custom-Tag-Library
	 */
	public void doTag() throws JspException, IOException {
		if (!StringUtils.isEmpty(category)) {
			String[] categoryArray = category.split("[,;]");
			Map<String, List<LookupBean>> lookupResults = new HashMap<String, List<LookupBean>>();
			for (String category : categoryArray) {
				category = category.trim();
				if (StringUtils.isEmpty(category)) {
					continue;
				}
				lookupResults.put(category, lookupListCache.get(category));
			}
			getJspContext().getOut().print("<script>");
			getJspContext().getOut().print("var solib; solib = solib || {}; solib.lookup = ");
			getJspContext().getOut().print(new ObjectMapper().writeValueAsString(lookupResults));
			getJspContext().getOut().println(";</script>");
		}
	}

}
