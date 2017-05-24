package com.baojing.credit.dao ;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.baojing.credit.model.Users;
@Component
public class UsersDao extends GenericDAOImpl<Users> {
	@Override
	public String getNameSpace() {
		return "users";
	}
	
	public Users findByUserName(String userName) {
		return this.getSqlSession().selectOne(this.getNameSpace()+".findByUserName", userName);
	}

	public int modifyLoginPwd(Map<String, Object> params) {
		return  this.getSqlSession().update(this.getNameSpace()+".modifyLoginPwd", params);
	}
}
