package cn.com.ut.toolkit.builder.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.com.ut.toolkit.builder.pojo.DataBase;
import cn.com.ut.toolkit.builder.pojo.DataBaseHolder;

@RestController
@RequestMapping(value = "/db")
public class DataSourceController {

	@Autowired
	private DataBaseHolder dataBaseHolder;

	@RequestMapping(value = "/query")
	public Object query() {

		return dataBaseHolder.getDbHolder();
	}

	@RequestMapping(value = "/delete/{db}")
	public Object delete(@PathVariable(value = "db") String db) {

		Map<String, DataBase> dbHolder = dataBaseHolder.getDbHolder();
		dbHolder.remove(db);
		return dbHolder;
	}

	@RequestMapping(value = "/add")
	public Object add(@RequestParam(value = "db") String db,
			@RequestParam(value = "username") String username,
			@RequestParam(value = "password") String password,
			@RequestParam(value = "url") String url) {

		DataBase dataBase = new DataBase();
		dataBase.setDb(db);
		dataBase.setUsername(username);
		dataBase.setPassword(password);
		dataBase.setUrl(url);

		Map<String, DataBase> dbHolder = dataBaseHolder.getDbHolder();
		dbHolder.put(db, dataBase);
		return dbHolder;
	}

}
