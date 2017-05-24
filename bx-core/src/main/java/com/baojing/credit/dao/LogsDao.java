package com.baojing.credit.dao ;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.baojing.credit.model.Logs;
@Component
public class LogsDao extends GenericDAOImpl<Logs> {
	@Override
	public String getNameSpace() {
		return "logs";
	}

	public long queryCount(Map<String, Object> param) {
		return this.getSqlSession().selectOne(this.getNameSpace()+".queryCount" , param);
	}

	public List<Map<String, Object>> queryRecord(Map<String, Object> param) {
		return this.getSqlSession().selectList(this.getNameSpace()+".queryRecord", param);
	}
}
