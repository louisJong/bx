package com.baojing.credit.service;

import com.alibaba.fastjson.JSONObject;
import com.baojing.credit.model.AdminUser;

public interface UserService {
	
	public JSONObject createUser(AdminUser user);
	
	public JSONObject login(String userName , String loginPwd);

	public JSONObject createSubUser(long mainUserId, String mianUserName,String subLoginPwd, String subUserName);

	public JSONObject querySubUser(long userId);

	public JSONObject modifyLoginPwd(Long userId, String oldPwd, String newPwd);

	public JSONObject setVerifyMsg(Long userId, String verifyMsg);

	public JSONObject deleteSubUser(long mainUserId, long subUserId);

	public JSONObject createUserRole(String createUser, String roleName,	String permission, String description);
	
	public JSONObject getAdminRoles(JSONObject params);

	public JSONObject editRole(JSONObject params);
	
	public JSONObject editUser(AdminUser user);
	
	public JSONObject deleteUser(long id);
	
}
