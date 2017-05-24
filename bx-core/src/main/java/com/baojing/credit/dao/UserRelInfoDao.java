package com.baojing.credit.dao ;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.baojing.credit.model.UserRelInfo;
@Component
public class UserRelInfoDao extends GenericDAOImpl<UserRelInfo> {
	@Override
	public String getNameSpace() {
		return "user_rel_info";
	}

	public List<Map<String, Object>> querySubUsers(long userId) {
		return this.getSqlSession().selectList(this.getNameSpace()+".querySubUsers", userId);
	}

	public long querySubAcctCount(long mainUserId) {
		return this.getSqlSession().selectOne(this.getNameSpace()+".querySubAcctCount" , mainUserId);
	}

	public int deleteRecord(Map<String, Object> params) {
		return this.getSqlSession().delete(this.getNameSpace()+".deleteRecord" , params);
	}
}
