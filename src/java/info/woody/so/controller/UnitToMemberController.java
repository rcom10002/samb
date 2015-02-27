package info.woody.so.controller;

import info.woody.so.bean.UnitToMemberBean;
import info.woody.so.bean.Pagination;
import info.woody.so.bean.Pagination.InternalPagination;
import info.woody.so.bean.Pagination.Page;
import info.woody.so.bean.ResponseDude;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/unitToMember")
public class UnitToMemberController {

	@Autowired
	private SqlSessionFactory sqlSessionFactory;

	@RequestMapping(value="/add", method=RequestMethod.PUT, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseDude addRecord(@RequestBody UnitToMemberBean unitToMember) {
		SqlSession session = sqlSessionFactory.openSession();
		int r = session.insert("insertUnitToMember", unitToMember);
		session.close();
		return 1 == r ? ResponseDude.OK : ResponseDude.INTERNAL_SERVER_ERROR;
	}

	@RequestMapping(value="/load", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public Page<UnitToMemberBean> loadRecords() {
		return this.loadRecords(1);
	}

	@RequestMapping(value="/load/{pageNumber}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public Page<UnitToMemberBean> loadRecords(@PathVariable int pageNumber) {
		SqlSession session = sqlSessionFactory.openSession();
		int rowCount = session.selectOne("selectUnitToMemberRowCount");
		Pagination pagination = Pagination.getEntityPagination();
		InternalPagination internalPagination = pagination.new InternalPagination(pageNumber, rowCount);
		List<UnitToMemberBean> unitToMemberList = session.selectList("selectUnitToMember", null, new RowBounds(internalPagination.getOffset(), internalPagination.getLimit()));
		Page<UnitToMemberBean> page = pagination.new Page<UnitToMemberBean>(unitToMemberList);
		session.close();
		return page;
	}

	@RequestMapping(value="/edit", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseDude updateRecord(@RequestBody UnitToMemberBean unitToMember) {
		SqlSession session = sqlSessionFactory.openSession();
		session.update("updateUnitToMember", unitToMember);
		session.close();
		return ResponseDude.OK;
	}

	@RequestMapping(value="/delete/{id}", method=RequestMethod.DELETE, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseDude deleteRecord(@PathVariable int id) {
		SqlSession session = sqlSessionFactory.openSession();
		session.delete("deleteUnitToMember", id);
		session.close();
		return ResponseDude.OK;
	}

}
