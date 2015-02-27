package info.woody.so.controller;

import info.woody.so.bean.Pagination;
import info.woody.so.bean.Pagination.InternalPagination;
import info.woody.so.bean.Pagination.Page;
import info.woody.so.bean.ResponseDude;
import info.woody.so.bean.RoleBean;
import info.woody.so.bean.RoleToPrivilegeBean;

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
@RequestMapping(value="/role")
public class RoleController {

	@Autowired
	private SqlSessionFactory sqlSessionFactory;

	@RequestMapping(value="/add", method=RequestMethod.PUT, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseDude addRecord(@RequestBody RoleBean role) {
		SqlSession session = sqlSessionFactory.openSession();
		int r = session.insert("insertRole", role);
		for (RoleToPrivilegeBean roleToPrivilege : role.getEntryModelList()) {
			roleToPrivilege.setRoleId(role.getId());
			session.insert("insertUnitToMember", roleToPrivilege);
		}
		session.close();
		return 1 == r ? ResponseDude.OK : ResponseDude.INTERNAL_SERVER_ERROR;
	}

	@RequestMapping(value="/load", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public Page<RoleBean> loadRecords() {
		return this.loadRecords(1);
	}

	@RequestMapping(value="/load/{pageNumber}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public Page<RoleBean> loadRecords(@PathVariable int pageNumber) {
		SqlSession session = sqlSessionFactory.openSession();
		int rowCount = session.selectOne("selectRoleRowCount");
		Pagination pagination = Pagination.getEntityPagination();
		InternalPagination internalPagination = pagination.new InternalPagination(pageNumber, rowCount);
		List<RoleBean> roleList = session.selectList("selectRole", null, new RowBounds(internalPagination.getOffset(), internalPagination.getLimit()));
		Page<RoleBean> page = pagination.new Page<RoleBean>(roleList);
		session.close();
		return page;
	}

	@RequestMapping(value="/edit", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseDude updateRecord(@RequestBody RoleBean role) {
		SqlSession session = sqlSessionFactory.openSession();
		session.update("updateRole", role);
		session.close();
		return ResponseDude.OK;
	}

	@RequestMapping(value="/delete/{id}", method=RequestMethod.DELETE, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseDude deleteRecord(@PathVariable int id) {
		SqlSession session = sqlSessionFactory.openSession();
		session.delete("deleteRole", id);
		session.close();
		return ResponseDude.OK;
	}

}
