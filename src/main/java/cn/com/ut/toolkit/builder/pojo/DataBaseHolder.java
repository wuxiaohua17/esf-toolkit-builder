package cn.com.ut.toolkit.builder.pojo;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.com.ut.core.common.util.CommonUtil;

@Component
public class DataBaseHolder implements InitializingBean {

	@Autowired
	private Environment env;

	private ConcurrentHashMap<String, DataBase> dbHolder = new ConcurrentHashMap<>();

	public ConcurrentHashMap<String, DataBase> getDbHolder() {

		return dbHolder;
	}

	@Override
	public void afterPropertiesSet() throws Exception {

		String databaseArray = env.getProperty("database");
		if (!CommonUtil.isEmpty(databaseArray)) {
			JSONArray databaseJsonArray = JSON.parseArray(databaseArray);
			for (int index = 0; index < databaseJsonArray.size(); index++) {
				JSONObject databaseJson = databaseJsonArray.getJSONObject(index);
				DataBase dataBase = new DataBase();
				dataBase.setDb(databaseJson.getString("db"));
				dataBase.setUrl(databaseJson.getString("url"));
				dataBase.setUsername(databaseJson.getString("username"));
				dataBase.setPassword(databaseJson.getString("password"));
				dbHolder.put(dataBase.getDb(), dataBase);
			}
		}

	}

}
