package info.woody.so.controller;

import info.woody.so.bean.Pagination;
import info.woody.so.bean.Pagination.InternalPagination;
import info.woody.so.bean.Pagination.Page;
import info.woody.so.bean.ReportBean;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/report")
public class ReportController {

	@Autowired
	private SqlSessionFactory sqlSessionFactory;

	@RequestMapping(value="/load", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public Page<ReportBean> loadRecords() {
		return this.loadRecords(1);
	}

	@RequestMapping(value="/load/{pageNumber}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public Page<ReportBean> loadRecords(@PathVariable int pageNumber) {
		SqlSession session = sqlSessionFactory.openSession();
		int rowCount = session.selectOne("selectReportRowCount");
		Pagination pagination = Pagination.getEntityPagination();
		InternalPagination internalPagination = pagination.new InternalPagination(pageNumber, rowCount);
		List<ReportBean> reportList = session.selectList("selectReport", null, new RowBounds(internalPagination.getOffset(), internalPagination.getLimit()));
		Page<ReportBean> page = pagination.new Page<ReportBean>(reportList);
		session.close();
		return page;
	}

}
