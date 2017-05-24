package com.baojing.credit.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.alibaba.fastjson.JSONObject;
import com.baojing.credit.common.utils.JsonUtils;
import com.baojing.credit.common.utils.MD5Util;
import com.baojing.credit.dao.UsersDao;
import com.baojing.credit.model.Users;
import com.baojing.credit.service.OldUserService;

@Service
public class OldfUserServiceImpl implements OldUserService {
	
	@Autowired
	private UsersDao usersDao;
	
	@Override
	public JSONObject login(String userName, String loginPwd) {
		Users user = usersDao.findByUserName(userName);
		loginPwd = "credit1212"+loginPwd;
		if (user == null) {
			return JsonUtils.commonJsonReturn("0002", "用户名或密码错误");
		}
		String pwdEncrypt = MD5Util.MD5Encode(loginPwd, "utf-8");
		if(!pwdEncrypt.equals(user.getPassword())){//密码错误
			return JsonUtils.commonJsonReturn("0002", "用户名或密码错误");
		}
		Date now = new Date();
		if(now.before(user.getStartDate()) || now.after(user.getEndDate())){//不可用
			return JsonUtils.commonJsonReturn("0003", "该账户当前无授权");
		}
		JSONObject result = JsonUtils.commonJsonReturn();
		Map<String , Object> loginMap = new HashMap<String, Object>();	
		loginMap.put("userId", user.getId());
		loginMap.put("comName", user.getCompanyName());
		loginMap.put("reserveWord", user.getReserveWord());
		JsonUtils.setBody(result, "loginMap", loginMap);
		return result;
	}

	@Override
	public JSONObject setVerifyMsg(Long userId, String verifyMsg) {
		Users updateUser = new Users();
		updateUser.setId(userId);
		updateUser.setReserveWord(verifyMsg);
		updateUser.setUpdateTime(new Date());
		int effectCount = usersDao.update(updateUser);
		Assert.isTrue(effectCount>0);
		return JsonUtils.commonJsonReturn();
	}
	
}
