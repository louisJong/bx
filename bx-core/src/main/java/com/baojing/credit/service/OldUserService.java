package com.baojing.credit.service;

import com.alibaba.fastjson.JSONObject;

public interface OldUserService {
	
	public JSONObject login(String userName , String loginPwd);

	public JSONObject setVerifyMsg(Long userId, String verifyMsg);
	
}
