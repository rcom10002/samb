package info.woody.so.controller;

import info.woody.so.bean.BookBean;
import info.woody.so.bean.ResponseDude;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/index")
public class IndexController {

	@Autowired
	private SqlSessionFactory sqlSessionFactory;

	@RequestMapping(value="/login", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseDude addRecord(@RequestBody BookBean book) {
		SqlSession session = sqlSessionFactory.openSession();
		int r = session.insert("authenticateCredential", book);
		session.close();
		return 1 == r ? ResponseDude.OK : ResponseDude.NOT_FOUND;
	}

}
