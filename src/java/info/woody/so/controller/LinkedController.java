package info.woody.so.controller;

import info.woody.so.bean.LinkedBean;
import info.woody.so.bean.Pagination;
import info.woody.so.bean.Pagination.InternalPagination;
import info.woody.so.bean.Pagination.Page;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
public class LinkedController {

	@Autowired
	private SqlSessionFactory sqlSessionFactory;

	@RequestMapping(value="/linked/{entity}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public Page<LinkedBean> queryEntityRecordsByKeyword(@PathVariable String entity) {
		return this.queryEntityRecordsByKeyword(entity, 1);
	}

	@RequestMapping(value="/linked/{entity}/{pageNumber}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public Page<LinkedBean> queryEntityRecordsByKeyword(@PathVariable String entity, @PathVariable int pageNumber) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("entity", entity);
		SqlSession session = sqlSessionFactory.openSession();
		int rowCount = session.selectOne("selectLinkedRowCount", params);
		Pagination pagination = Pagination.getLinkedPagination();
		InternalPagination internalPagination = pagination.new InternalPagination(pageNumber, rowCount);
		List<LinkedBean> linkedList = session.selectList("selectLinked", params, new RowBounds(internalPagination.getOffset(), internalPagination.getLimit()));
		Page<LinkedBean> page = pagination.new Page<LinkedBean>(linkedList);
		session.close();
		return page;
	}
}
