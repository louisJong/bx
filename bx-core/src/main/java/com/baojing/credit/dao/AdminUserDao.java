package com.baojing.credit.dao ;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.baojing.credit.model.AdminUser;
@Component
public class AdminUserDao extends GenericDAOImpl<AdminUser> {
	@Override
	public String getNameSpace() {
		return "admin_user";
	}

	public Map<String, Object> findByUserNameAndPwd(Map<String, String> params) {
		return this.getSqlSession().selectOne(this.getNameSpace()+".findByUserNameAndPwd", params);
	}

	public AdminUser findByUserName(String userName) {
		return this.getSqlSession().selectOne(this.getNameSpace()+".findByUserName", userName);
	}

	public int modifyLoginPwd(Map<String, Object> params) {
		return  this.getSqlSession().update(this.getNameSpace()+".modifyLoginPwd", params);
	}

	public int setVerifyMsg(Map<String, Object> params) {
		return this.getSqlSession().update(this.getNameSpace()+".setVerifyMsg", params);
	}
}
