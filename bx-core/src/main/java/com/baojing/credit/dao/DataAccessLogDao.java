package com.baojing.credit.dao ;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.baojing.credit.model.DataAccessLog;
@Component
public class DataAccessLogDao extends GenericDAOImpl<DataAccessLog> {
	@Override
	public String getNameSpace() {
		return "data_access_log";
	}

	public List<Map<String, Object>> queryRecord(Map<String, Object> param) {
		return this.getSqlSession().selectList(this.getNameSpace()+".queryRecord", param);
	}

	public long queryCount(Map<String, Object> param) {
		return this.getSqlSession().selectOne(this.getNameSpace()+".queryCount", param);
	}

	public long queryAllCount(Map<String, Object> param) {
		return this.getSqlSession().selectOne(this.getNameSpace()+".queryAllCount", param);
	}

	public List<Map<String, Object>> queryAllRecord(Map<String, Object> param) {
		return this.getSqlSession().selectList(this.getNameSpace()+".queryAllRecord", param);
	}
}
