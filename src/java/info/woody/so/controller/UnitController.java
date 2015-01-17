package info.woody.so.controller;

import info.woody.so.bean.Pagination;
import info.woody.so.bean.Pagination.InternalPagination;
import info.woody.so.bean.Pagination.Page;
import info.woody.so.bean.ResponseDude;
import info.woody.so.bean.UnitBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
@RequestMapping(value="/unit")
public class UnitController {

	@Autowired
	private SqlSessionFactory sqlSessionFactory;

	@RequestMapping(value="/add", method=RequestMethod.PUT, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseDude addRecord(@RequestBody UnitBean unit) {
		SqlSession session = sqlSessionFactory.openSession();
		int parentUnitId = Integer.parseInt(unit.getParentUnit());
		if (0 != parentUnitId) {
			UnitBean parentUnit = session.selectOne("selectParentUnit", parentUnitId);
			unit.setParentUnit(parentUnit.getParentUnit() + parentUnit.getId() + "/");
			unit.setParentUnitId(parentUnitId);
		} else {
			unit.setParentUnit("/");
			unit.setParentUnitId(0);
		}
		int r = session.insert("insertUnit", unit);
		session.close();
		return 1 == r ? ResponseDude.OK : ResponseDude.INTERNAL_SERVER_ERROR;
	}

	@RequestMapping(value="/load", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public Page<UnitBean> loadRecords() {
		return this.loadRecords(1);
	}

	@RequestMapping(value="/load/{pageNumber}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public Page<UnitBean> loadRecords(@PathVariable int pageNumber) {
		SqlSession session = sqlSessionFactory.openSession();
		int rowCount = session.selectOne("selectUnitRowCount");
		Pagination pagination = Pagination.getEntityPagination();
		InternalPagination internalPagination = pagination.new InternalPagination(pageNumber, rowCount);
		List<UnitBean> unitList = session.selectList("selectUnit", null, new RowBounds(internalPagination.getOffset(), internalPagination.getLimit()));
		Page<UnitBean> page = pagination.new Page<UnitBean>(unitList);
		session.close();
		return page;
	}

	@RequestMapping(value="/edit", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseDude updateRecord(@RequestBody UnitBean unit) {
		SqlSession session = sqlSessionFactory.openSession();
		int parentUnitId = Integer.parseInt(unit.getParentUnit());
		int unitId = unit.getId();
		UnitBean myUnit = session.selectOne("selectParentUnit", unitId);
		if (0 != parentUnitId) {
			UnitBean parentUnit = session.selectOne("selectParentUnit", parentUnitId);
			unit.setParentUnit(parentUnit.getParentUnit() + parentUnit.getId() + "/");
			unit.setParentUnitId(parentUnitId);
		} else {
			unit.setParentUnit("/");
			unit.setParentUnitId(0);
		}
		session.update("updateUnit", unit);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("newParentUnit", unit.getParentUnit() + unitId + "/");
		params.put("oldParentUnit", myUnit.getParentUnit() + unitId + "/");
		session.update("updateSubUnits", params);
		session.close();
		return ResponseDude.OK;
	}

	@RequestMapping(value="/delete/{id}", method=RequestMethod.DELETE, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseDude deleteRecord(@PathVariable int id) {
		SqlSession session = sqlSessionFactory.openSession();
		UnitBean myUnit = session.selectOne("selectParentUnit", id);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", myUnit.getId());
		params.put("parentUnit", myUnit.getParentUnit() + id + "/");
		session.delete("deleteUnit", params);
		session.close();
		return ResponseDude.OK;
	}

}
