package info.woody.so.controller;

import info.woody.so.bean.ResponseDude;
import info.woody.so.bean.WorkbenchBean;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/workbench")
public class WorkbenchController {

	@Autowired
	private SqlSessionFactory sqlSessionFactory;

	@RequestMapping(value = "/executeSQL", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseDude executeSQL(@RequestBody WorkbenchBean workbenchBean) {
		DataSource ds = sqlSessionFactory.getConfiguration().getEnvironment().getDataSource();

		QueryRunner run = new QueryRunner(ds);
		List<Object> feedbacks = new ArrayList<Object>();
		for (String sql : workbenchBean.getSqls()) {
			try {
				if (!workbenchBean.isValid(sql)) {
					continue;
				}
				if (workbenchBean.isRetrieving(sql)) {
					feedbacks.add(run.query(sql, new MapListHandler()));
				} else {
					feedbacks.add("Affected rows: " + run.update(sql));
				}
			} catch (SQLException sqle) {
				feedbacks.add(sqle.getMessage());
			}
		}
		ResponseDude rd = ResponseDude.OK;
		rd.setPayload(feedbacks);
		return rd;
	}

	@RequestMapping(value = "/createSubapp", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseDude createSubapp(@RequestParam(required=false) String module, @RequestParam(required=false) String projectBasePath) {
		String[] templateFiles = new String[] {
			"/src/java/info/woody/so/bean/LookupBean.java",
			"/src/java/info/woody/so/controller/LookupController.java",
			"/src/resources/mybatis/mybatis-lookup-mapper.xml",
			"/WebContent/static/js/so/lookup.js",
			"/WebContent/WEB-INF/pages/inc/lookup.jsp"
		};
		if (!new File(projectBasePath).exists()) {
			ResponseDude rd = ResponseDude.NOT_ACCEPTABLE;
			rd.setPayload("");
			return rd;
		}
		for (String templateFile : templateFiles) {
			if (!new File(projectBasePath.concat(templateFile)).exists()) {
				ResponseDude rd = ResponseDude.NOT_ACCEPTABLE;
				rd.setPayload("");
				return rd;
			}
		}
		for (String templateFile : templateFiles) {
			try {
				File srcFile = new File(templateFile);
				File destFile = new File(templateFile.replaceAll("user", module).replaceAll("User", StringUtils.capitalize(module)));
				FileUtils.copyFile(srcFile, destFile);
				String content = FileUtils.readFileToString(destFile);
				content = content.replaceAll("user", module).replaceAll("User", StringUtils.capitalize(module));
				FileUtils.writeStringToFile(destFile, content);
			} catch (Exception e) {
				
			}
		}
		return ResponseDude.OK;
	}

}
