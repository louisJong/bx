package com.baojing.credit.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.Assert;

import com.alibaba.fastjson.JSONObject;
import com.baojing.credit.common.utils.JsonUtils;
import com.baojing.credit.common.utils.MD5Util;
import com.baojing.credit.dao.AdminUserDao;
import com.baojing.credit.dao.UserRelInfoDao;
import com.baojing.credit.dao.UserRoleDao;
import com.baojing.credit.enums.UserStatus;
import com.baojing.credit.enums.UserType;
import com.baojing.credit.model.AdminUser;
import com.baojing.credit.model.UserRelInfo;
import com.baojing.credit.model.UserRole;
import com.baojing.credit.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private AdminUserDao adminUserDao;
	
	@Autowired
	private UserRelInfoDao userRelInfoDao;
	
	@Autowired
	private UserRoleDao userRoleDao;
	
	/**
	 * 最大修改密码间隔天数
	 */
	private static final int MAX_MODIFY_PWD_DAYS = 180;
	
	/**
	 * 最大登陆密码错误次数
	 */
	private static final int MAX_WRONG_PWD_TIMES = 5;
	
	/**
	 * 账户锁定时间(分钟)
	 */
	private static final int ACCOUNT_LOCK_MINUTES = 30;
	
	/**
	 * 最大子账户数
	 */
	private static final int MAX_SUB_ACCOUNT_COUNT = 10;
	
	@Autowired
	private TransactionTemplate transactionTemplate;
	
	@Override
	public JSONObject createUser(AdminUser user) {
		String loginPwd = user.getLoginPwd();
		if(!(loginPwd.matches("^.*[a-zA-Z]+.*$") && loginPwd.matches("^.*[0-9]+.*$")
				&& loginPwd.matches("^.*[/^/$/.//,;:'!@#%&/*/|/?/+/(/)/[/]/{/}]+.*$"))){//密码复杂度校验不通过
			return JsonUtils.commonJsonReturn("0005", "密码复杂度校验不通过");
		}
		Date now = new Date();
		user.setLoginPwd(MD5Util.MD5Encode(loginPwd, "utf-8"));
		user.setErrorTimes(0l);
		user.setCreateTime(now);
		user.setLastPwdModifyTime(now);
		user.setStatus(UserStatus.WORK.getCode());
		long id = adminUserDao.insert(user);
		Assert.isTrue(id>0);
		JSONObject result = JsonUtils.commonJsonReturn();
		JsonUtils.setBody(result, "id", id);
		return result;
	}

	@Override
	public JSONObject login(String userName, String loginPwd) {
		Date now = new Date();
		AdminUser user = adminUserDao.findByUserName(userName);
		if (user == null) {
			return JsonUtils.commonJsonReturn("0002", "用户名或密码错误");
		}
		if(UserStatus.INVID.getCode().equals(user.getStatus())){//账户不可用
			return JsonUtils.commonJsonReturn("1001", "账户不可用");
		}
		if(DateUtils.addDays(user.getLastPwdModifyTime() , MAX_MODIFY_PWD_DAYS).before(now)){//需修改密码
			return JsonUtils.commonJsonReturn("0003", "超过180天未修改密码");
		}
		if(UserStatus.LOCK.getCode().equals(user.getStatus()) && user.getErrorTimes()>=MAX_WRONG_PWD_TIMES && DateUtils.addMinutes(user.getLastPwdErrorTime() , ACCOUNT_LOCK_MINUTES).after(now)){
			return JsonUtils.commonJsonReturn("0004", "账户已被锁定");
		}
		String pwdEncrypt = MD5Util.MD5Encode(loginPwd, "utf-8");
		AdminUser updateUser = new AdminUser();
		if(!pwdEncrypt.equals(user.getLoginPwd())){//密码错误
			long errtimes = user.getErrorTimes()+1;
			updateUser.setId(user.getId());
			updateUser.setErrorTimes(errtimes);
			updateUser.setLastPwdErrorTime(now);
			updateUser.setUpdateTime(now);
			if(errtimes>=MAX_WRONG_PWD_TIMES){//锁定
				updateUser.setStatus(UserStatus.LOCK.getCode());
			}
			adminUserDao.update(updateUser);
			JSONObject result = JsonUtils.commonJsonReturn("0002", "用户名或密码错误");
			long leftErrTimes = MAX_WRONG_PWD_TIMES - errtimes;
			JsonUtils.setBody(result, "leftErrTimes", leftErrTimes<0? 0 : leftErrTimes);
			return result;
		}
		if(user.getErrorTimes().longValue()!=0l || !UserStatus.WORK.getCode().equals(user.getStatus())){//清除锁定及错误次数
			updateUser.setId(user.getId());
			updateUser.setStatus(UserStatus.WORK.getCode());
			updateUser.setErrorTimes(0l);
			adminUserDao.update(updateUser);
		}
		JSONObject result = JsonUtils.commonJsonReturn();
		Map<String , Object> loginMap = new HashMap<String, Object>();	
		loginMap.put("userType", user.getUserType());
		loginMap.put("userId", user.getId());
		loginMap.put("verifyMsg", user.getVerifyMsg());
		loginMap.put("comName", user.getComName());
		JsonUtils.setBody(result, "loginMap", loginMap);
		return result;
	}

	@Override
	public JSONObject createSubUser(long mainUserId, String mainUserName,
			String subLoginPwd, String subUserName) {
		long subCount = userRelInfoDao.querySubAcctCount(mainUserId);
		if(subCount>=MAX_SUB_ACCOUNT_COUNT){//超过最大子账户数量限制
			return JsonUtils.commonJsonReturn("2001", "超过最大子账户数量限制");
		}
		AdminUser mainUser = adminUserDao.findById(mainUserId);
		AdminUser subUser = new AdminUser();
		subUser.setAuthEndTime(mainUser.getAuthEndTime());
		subUser.setAuthStartTime(mainUser.getAuthStartTime());
		subUser.setComAddress(mainUser.getComAddress());
		subUser.setComName(mainUser.getComName());
		subUser.setComRegion(mainUser.getComRegion());
		subUser.setCreateUser(mainUserName);
		subUser.setEmail(mainUser.getEmail());
		subUser.setFax(mainUser.getFax());
		subUser.setLoginPwd(subLoginPwd);
		subUser.setUserName(subUserName);
		subUser.setUserType(UserType.EMP_SUB.getCode());
		JSONObject createUserResult = createUser(subUser);
		if(!JsonUtils.equalDefSuccCode(createUserResult)){
			return createUserResult;
		}
		long subUserId = createUserResult.getJSONObject("body").getLongValue("id");
		UserRelInfo realInfo = new UserRelInfo();
		realInfo.setCreateTime(new Date());
		realInfo.setMainUserName(mainUserName);
		realInfo.setMainUserId(mainUserId);
		realInfo.setSubUserId(subUserId);
		realInfo.setCreateUser(mainUserName);
		realInfo.setSubUserName(subUserName);
		long id = userRelInfoDao.insert(realInfo);
		Assert.isTrue(id>0);
		JSONObject result = JsonUtils.commonJsonReturn();
		JsonUtils.setBody(result, "subUserId", subUserId);
		JsonUtils.setBody(result, "relId", id);
		return result;
	}

	@Override
	public JSONObject querySubUser(long userId) {
		List<Map<String , Object>> subUsers = userRelInfoDao.querySubUsers(userId);
		JSONObject result = JsonUtils.commonJsonReturn();
		JsonUtils.setBody(result, "subUsers", subUsers);
		return result;
	}

	@Override
	public JSONObject modifyLoginPwd(Long userId, String oldPwd, String newPwd) {
		if(!(newPwd.matches("^.*[a-zA-Z]+.*$") && newPwd.matches("^.*[0-9]+.*$")
				&& newPwd.matches("^.*[/^/$/.//,;:'!@#%&/*/|/?/+/(/)/[/]/{/}]+.*$"))){//密码复杂度校验不通过
			return JsonUtils.commonJsonReturn("0005", "密码复杂度校验不通过");
		}
		Map<String , Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		params.put("oldPwd", MD5Util.MD5Encode(oldPwd , "utf-8"));
		params.put("newPwd", MD5Util.MD5Encode(newPwd , "utf-8"));
		int effectCount = adminUserDao.modifyLoginPwd(params);
		if(effectCount==0){
			return JsonUtils.commonJsonReturn("0001", "原始密码错误");
		}
		return JsonUtils.commonJsonReturn();
	}

	@Override
	public JSONObject setVerifyMsg(Long userId, String verifyMsg) {
		Map<String , Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		params.put("verifyMsg", verifyMsg);
		int effectCount = adminUserDao.setVerifyMsg(params);
		Assert.isTrue(effectCount>0);
		return JsonUtils.commonJsonReturn();
	}
	@Override
	public JSONObject deleteSubUser(final long mainUserId,final long subUserId) {
		return transactionTemplate.execute(new TransactionCallback<JSONObject>() {
				@Override
				public JSONObject doInTransaction(TransactionStatus status) {
					AdminUser subUserUpdate = new AdminUser();
					subUserUpdate.setId(subUserId);
					subUserUpdate.setStatus(UserStatus.INVID.getCode());
					subUserUpdate.setUpdateTime(new Date());
					int effectCount = adminUserDao.update(subUserUpdate);
					Assert.isTrue(effectCount>0);
					Map<String,Object> params = new HashMap<String, Object>();
					params.put("mainUserId", mainUserId);
					params.put("subUserId", subUserId);
					int deleteCount = userRelInfoDao.deleteRecord(params);
					Assert.isTrue(deleteCount>0);
					return JsonUtils.commonJsonReturn();
				}
			});
		}

	@Override
	public JSONObject createUserRole(String createUser, String roleName,
			String permission, String description) {
		UserRole role = new UserRole();
		role.setCreateTime(new Date());
		role.setCreateUser(createUser);
		role.setPermission(permission);
		role.setRemark(description);
		role.setRoleName(roleName);
		long id = userRoleDao.insert(role);
		Assert.isTrue(id>0);
		return JsonUtils.commonJsonReturn();
	}

	@Override
	public JSONObject getAdminRoles(JSONObject params) {
		int page = params.getIntValue("page");
		int limit = params.getIntValue("limit");
		limit= limit<=0?20:limit;
		page = page<0?0:page;
		int startRow = page*limit;
		params.put("startRow",startRow);
		params.put("limit",limit);
		List<Map<String , Object>> roles = userRoleDao.queryRecord(params);
		long totalCount = userRoleDao.queryCount(params);
		JSONObject result = JsonUtils.commonDataInterfaceJsonReturn(params.getString("userName"));
		JsonUtils.setBody(result, "adminRoles", roles);
		JsonUtils.setBody(result, "total", totalCount);
		return result;
	}

	@Override
	public JSONObject editRole(JSONObject params) {
		UserRole roleUpdate = new UserRole();
		roleUpdate.setId(params.getLong("id"));
		roleUpdate.setPermission(params.getString("permission"));
		roleUpdate.setRemark(params.getString("description"));
		roleUpdate.setUpdateTime(new Date());
		userRoleDao.update(roleUpdate);
		return JsonUtils.commonJsonReturn();
	}

	@Override
	public JSONObject editUser(AdminUser user) {
		String loginPwd = user.getLoginPwd();
		if(StringUtils.isNotEmpty(loginPwd) && !(loginPwd.matches("^.*[a-zA-Z]+.*$") && loginPwd.matches("^.*[0-9]+.*$")
				&& loginPwd.matches("^.*[/^/$/.//,;:'!@#%&/*/|/?/+/(/)/[/]/{/}]+.*$"))){//密码复杂度校验不通过
			return JsonUtils.commonJsonReturn("0005", "密码复杂度校验不通过");
		}
		Date now = new Date();
		if(StringUtils.isNotEmpty(loginPwd)){
			user.setLoginPwd(MD5Util.MD5Encode(loginPwd, "utf-8"));
			user.setLastPwdModifyTime(now);
		}
		user.setUpdateTime(now);
		int effecuCount = adminUserDao.update(user);
		Assert.isTrue(effecuCount>0);
		JSONObject result = JsonUtils.commonJsonReturn();
		return result;
	}

	@Override
	public JSONObject deleteUser(long id) {
		AdminUser subUserUpdate = new AdminUser();
		subUserUpdate.setId(id);
		subUserUpdate.setStatus(UserStatus.INVID.getCode());
		subUserUpdate.setUpdateTime(new Date());
		int effectCount = adminUserDao.update(subUserUpdate);
		Assert.isTrue(effectCount>0);
		return JsonUtils.commonJsonReturn();
	}

}
