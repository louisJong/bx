package com.baojing.credit.dao ;
import org.springframework.stereotype.Component;

import com.baojing.credit.model.UserAuthInfo;
@Component
public class UserAuthInfoDao extends GenericDAOImpl<UserAuthInfo> {
	@Override
	public String getNameSpace() {
		return "user_auth_info";
	}
}
