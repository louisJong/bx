package com.baojing.credit.dao ;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.baojing.credit.model.UserRole;
@Component
public class UserRoleDao extends GenericDAOImpl<UserRole> {
	@Override
	public String getNameSpace() {
		return "user_role";
	}

	public List<Map<String, Object>> queryRecord(JSONObject params) {
		return this.getSqlSession().selectList(this.getNameSpace()+".queryRecord" , params);
	}

	public long queryCount(JSONObject params) {
		return this.getSqlSession().selectOne(this.getNameSpace()+".queryCount" , params);
	}
}
