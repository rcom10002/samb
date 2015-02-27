package info.woody.so.taglib;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.context.WebApplicationContext;

public class LoadModulePhraseTag extends TagSupport {

	private String module;

	public void setModule(String module) {
		this.module = module;
	}

	/*
	 * (non-Javadoc)
	 * @see javax.servlet.jsp.tagext.SimpleTagSupport#doTag()
	 * 
	 * http://www.tutorialspoint.com/jsp/jsp_custom_tags.htm
	 * http://www.codeproject.com/Articles/31614/JSP-JSTL-Custom-Tag-Library
	 */
	public int doStartTag() throws JspException {
		if (!StringUtils.isEmpty(module)) {
			List<String> phraseKeys = phraseKeysCache.get(module);
			if (phraseKeys == null) {
				throw new RuntimeException("The specified module name cannot be identified. Please check the phrase.properties if there is any phrase definitions starting with: " + module);
			}
			WebApplicationContext wac = (WebApplicationContext)this.pageContext.getServletContext().getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
			ReloadableResourceBundleMessageSource rrbms = (ReloadableResourceBundleMessageSource)wac.getBean("messageSource");
			Locale locale = LocaleContextHolder.getLocale();
			try {
				// use script tag as a template container to present all related phrases
				this.pageContext.getOut().println("<script type=\"text/so-phrase\">");
				for (String phraseKey : phraseKeys) {
					this.pageContext.getOut().println(String.format("%s=%s", phraseKey, rrbms.getMessage(phraseKey, null, locale)));
				}
				this.pageContext.getOut().println("</script>");
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			
			
			
		    // LocaleContextHolder.getLocale();
		    //     <property name="fallbackToSystemLocale" value="false" />
//			this.getJspContext().getELContext().get
//			String[] categoryArray = category.split("[,;]");
//			Map<String, List<LookupBean>> lookupResults = new HashMap<String, List<LookupBean>>();
//			for (String category : categoryArray) {
//				category = category.trim();
//				if (StringUtils.isEmpty(category)) {
//					continue;
//				}
//				lookupResults.put(category, lookupListCache.get(category));
//			}
//			getJspContext().getOut().print("<script>");
//			getJspContext().getOut().print("var solib; solib = solib || {}; solib.lookup = ");
//			getJspContext().getOut().print(new ObjectMapper().writeValueAsString(lookupResults));
//			getJspContext().getOut().println(";</script>");
		} else {
			throw new RuntimeException("Dude, don't you know module name cannot be empty?!");
		}
		return super.doStartTag();
	}


	private static Map<String, List<String>> phraseKeysCache = null;
}
