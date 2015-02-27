package info.woody.so.controller;

import info.woody.so.bean.RoleToPrivilegeBean;
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
@RequestMapping(value="/roleToPrivilege")
public class RoleToPrivilegeController {

	@Autowired
	private SqlSessionFactory sqlSessionFactory;

	@RequestMapping(value="/add", method=RequestMethod.PUT, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseDude addRecord(@RequestBody RoleToPrivilegeBean roleToPrivilege) {
		SqlSession session = sqlSessionFactory.openSession();
		int r = session.insert("insertRoleToPrivilege", roleToPrivilege);
		session.close();
		return 1 == r ? ResponseDude.OK : ResponseDude.INTERNAL_SERVER_ERROR;
	}

	@RequestMapping(value="/load", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public Page<RoleToPrivilegeBean> loadRecords() {
		return this.loadRecords(1);
	}

	@RequestMapping(value="/load/{pageNumber}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public Page<RoleToPrivilegeBean> loadRecords(@PathVariable int pageNumber) {
		SqlSession session = sqlSessionFactory.openSession();
		int rowCount = session.selectOne("selectRoleToPrivilegeRowCount");
		Pagination pagination = Pagination.getEntityPagination();
		InternalPagination internalPagination = pagination.new InternalPagination(pageNumber, rowCount);
		List<RoleToPrivilegeBean> roleToPrivilegeList = session.selectList("selectRoleToPrivilege", null, new RowBounds(internalPagination.getOffset(), internalPagination.getLimit()));
		Page<RoleToPrivilegeBean> page = pagination.new Page<RoleToPrivilegeBean>(roleToPrivilegeList);
		session.close();
		return page;
	}

	@RequestMapping(value="/edit", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseDude updateRecord(@RequestBody RoleToPrivilegeBean roleToPrivilege) {
		SqlSession session = sqlSessionFactory.openSession();
		session.update("updateRoleToPrivilege", roleToPrivilege);
		session.close();
		return ResponseDude.OK;
	}

	@RequestMapping(value="/delete/{id}", method=RequestMethod.DELETE, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseDude deleteRecord(@PathVariable int id) {
		SqlSession session = sqlSessionFactory.openSession();
		session.delete("deleteRoleToPrivilege", id);
		session.close();
		return ResponseDude.OK;
	}

}
