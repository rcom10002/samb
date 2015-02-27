package info.woody.so;

import info.woody.so.taglib.LoadModulePhraseTag;
import info.woody.so.taglib.LookupTag;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.context.WebApplicationContext;

/**
 * Application Lifecycle Listener implementation class DispatcherListener
 *
 */
public class DispatcherListener implements ServletContextListener {

	private static Set<String> COMMON_PHRASE_PREFIX = new HashSet<String>();

	/**
	 * Default constructor.
	 */
	public DispatcherListener() {
		COMMON_PHRASE_PREFIX.add("control");
		COMMON_PHRASE_PREFIX.add("rest");
		COMMON_PHRASE_PREFIX.add("button");
		COMMON_PHRASE_PREFIX.add("common");
		COMMON_PHRASE_PREFIX.add("solib");
		COMMON_PHRASE_PREFIX.add("global");
	}

	/**
	 * @see ServletContextListener#contextDestroyed(ServletContextEvent)
	 */
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
	}

	/**
	 * @see ServletContextListener#contextInitialized(ServletContextEvent)
	 */
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		try {
			WebApplicationContext wac = (WebApplicationContext)servletContextEvent.getServletContext().getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
			ReloadableResourceBundleMessageSource rrbms = (ReloadableResourceBundleMessageSource)wac.getBean("messageSource");
			Field basenamesField = rrbms.getClass().getDeclaredField("basenames");
			basenamesField.setAccessible(true);
			String[] files = (String[])basenamesField.get(rrbms);
			Map<String, List<String>> phraseKeysCache = new HashMap<String, List<String>>();
			for (String file : files) {
				Set<String> keySet = ResourceBundle.getBundle(file.replaceFirst(".+?:", "")).keySet();
				for (String key : keySet) {
					String module = key.replaceAll("[.].+$", "");
					if (COMMON_PHRASE_PREFIX.contains(module)) {
						module = "common";
					}
					List<String> keys = (null == phraseKeysCache.get(module)) ? new ArrayList<String>() : phraseKeysCache.get(module);
					keys.add(key);
					phraseKeysCache.put(module, keys);
				}
			}
			
			Field phraseKeysCacheField = LoadModulePhraseTag.class.getDeclaredField("phraseKeysCache");
			phraseKeysCacheField.setAccessible(true);
			phraseKeysCacheField.set(null, phraseKeysCache);
//			ResourceBundle phrases = ResourceBundle.getBundle("spring-regex/regex-resources");
//		    Enumeration<String> phraseKeys = phrases.getKeys();
//		    while (phraseKeys.hasMoreElements()) {
//		    	String phraseKey = phraseKeys.nextElement();
////		    	try {
////					this.pageContext.getOut().print(String.format("", phraseKey, phrases.getString(phraseKey)));
////				} catch (IOException e) {
////					// TODO Auto-generated catch block
////					e.printStackTrace();
////				}
//		    }
//			
			
			
			
			
			
			
			
			SqlSessionFactory sqlSessionFactory = wac.getBean(SqlSessionFactory.class);
			LookupTag.reload(sqlSessionFactory);
			Object entityPageSize = servletContextEvent.getServletContext().getAttribute("PAGE_SIZE_FOR_ENTITY");
			if (entityPageSize == null) {
				entityPageSize = 10;
			} else {
				entityPageSize = entityPageSize.toString();
			}

			
//			Object linkedPageSize = servletContextEvent.getServletContext().getAttribute("PAGE_SIZE_FOR_LINKED");
//			
//			Integer.valueOf();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {
		Parent a1 = new Parent();
		Parent a2 = new Child();
		System.out.println(a1.aaa);
		System.out.println(a2.aaa);
//        String driver = "org.apache.derby.jdbc.EmbeddedDriver";  
//        Class.forName(driver).newInstance();  
//        Connection conn = DriverManager.getConnection("jdbc:derby:/home/woody/git/so/sodb;create=true");
//        Statement stat = conn.createStatement();
//        //stat.executeUpdate("DROP VIEW soLinked");
//        System.out.println(stat.executeUpdate(
//        		"CREATE VIEW soLinked(entity, name, val, weight) AS                                                             " +
//        		"  SELECT 'requirement' AS entity,                                                                              " +
//        		"         id,                                                                                                   " +
//        		"         (corp || ', ' || cast(createAt as date) || ', ' || target || ', ' || position) AS val, corp AS weight " +
//        		"    FROM soRequirement                                                                                         " +
//        		"   WHERE status = 'OPEN'                                                                                       " +
//        		"   UNION                                                                                                       " +
//        		"  SELECT 'unit' AS entity, id, val, weight FROM                                                                " +
//        		"  (                                                                                                            " +
//        		"  SELECT id, name AS val, parentUnit || '/' AS weight                                                          " +
//        		"    FROM soUnit                                                                                                " +
//        		"   WHERE status = 'ACTIVE'                                                                                     " +
//        		"   UNION VALUES (0, '/', '')                                                                                   " +
//        		"   ORDER BY 1, 3                                                                                               " +
//        		"   ) T (id, val, weight)                                                                                       " +
//        		"   ORDER BY 1, 4                                                                                               "
//        		));
//        stat.close();
//        conn.close();
	}

	public static class Parent {
		public int aaa = 10;
		public Parent() {
			aaa = 20;
		}
		public int getAaa() {
			return aaa;
		}
	}

	public static class Child extends Parent {
		public int aaa = 100;
		public Child() {
			aaa = 200;
		}
		public int getAaa() {
			return aaa;
		}
	}

}
