package com.baojing.credit.admin.controller;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.baojing.credit.admin.util.WebUtil;
import com.baojing.credit.common.utils.JsonUtils;
import com.baojing.credit.common.utils.VerifyCodeUtils;
import com.baojing.credit.enums.UserType;
import com.baojing.credit.model.AdminUser;
import com.baojing.credit.service.UserService;
import com.baojing.credit.utils.BxEnvUtil;

@Controller
public class UserController {
	
	private static final Logger logger = Logger.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/admin/login/img_code")
	@ResponseBody
	public void getImgCode(HttpServletRequest request , HttpServletResponse response , HttpSession session) throws IOException{
		String verifyCode = VerifyCodeUtils.outputVerifyImage(200, 80, response.getOutputStream(), 4);
		session.setAttribute("imgVerifyCode", verifyCode);
	}
	
	@RequestMapping(value="/admin/login/imgCode/verify")
	@ResponseBody
	public JSONObject verifyImgCode(HttpServletRequest request , HttpServletResponse response , HttpSession session , @RequestParam(value="imgverify" , required = true) String custVerifyCode){
		JSONObject result = JsonUtils.commonJsonReturn();
		String sessionCode = (String)session.getAttribute("imgVerifyCode");
		if(!StringUtils.equalsIgnoreCase(custVerifyCode, sessionCode)){
			return JsonUtils.commonJsonReturn("0001", "验证码错误");
		}
		session.removeAttribute("imgVerifyCode");
		return result;
	}
	
	@RequestMapping(value="/admin/user/create_main_acct")
	@ResponseBody
	public String create_main_acct(HttpServletRequest request , HttpServletResponse response , 
										 @RequestParam(value="username" , required = true) String userName,
										 @RequestParam(value="password" , required = true) String loginPwd,
										 @RequestParam(value="roleId" , required = false) long roleId,
										 @RequestParam(value="district" , required = false) String district,
										 @RequestParam(value="fax" , required = false) String fax,
										 @RequestParam(value="tel" , required = false) String tel,
										 @RequestParam(value="address" , required = false) String address,
										 @RequestParam(value="companyName" , required = true) String comName , 
										 @RequestParam(value="startDate" , required = true) Date authStartTime , 
										 @RequestParam(value="endDate" , required = true) Date authEndTime
			){
		JSONObject result = JsonUtils.commonJsonReturn();
//		UserType userTypeEnum = UserType.valueStr(userType);
//		if(userTypeEnum == null){
//			return JsonUtils.commonJsonReturn("0001" , "错误的用户类型").toJSONString();
//		}
		if(!UserType.ADMIN.getCode().equals(WebUtil.getUserType(request))){//只有管理员有权限创建主账户
			return JsonUtils.commonJsonReturn("0001" , "无权操作").toJSONString();
		}
		try{
			
			AdminUser user = new AdminUser();
			user.setCreateUser(WebUtil.getUserName(request) );
			user.setLoginPwd(loginPwd);
			user.setUserName(userName);
			user.setUserType(UserType.EMP_MAIN.getCode());
			user.setAuthEndTime(authEndTime);
			user.setAuthStartTime(authStartTime);
			user.setComName(comName);
			user.setComAddress(address);
			user.setComRegion(district);
			user.setFax(fax);
			user.setRoleId(roleId);
			user.setTelephone(tel);
			result = userService.createUser(user);
		}catch(DuplicateKeyException e){
			logger.error("userCreate error userName = "+userName , e);
			return JsonUtils.commonJsonReturn("0002" , "用户已存在").toJSONString();
		}
		catch(Exception e){
			logger.error("userCreate error userName = "+userName , e);
			return JsonUtils.commonJsonReturn("9999" , "系统异常").toJSONString();
		}
		return result.toJSONString();
	}
	
	@RequestMapping(value="/admin/user/create_sub_acct")
	@ResponseBody
	public String create_usr_acct(HttpServletRequest request , HttpServletResponse response , 
										 @RequestParam(value="username" , required = true) String userName,
										 @RequestParam(value="password" , required = true) String loginPwd,
										 @RequestParam(value="roleId" , required = false) long roleId,
										 @RequestParam(value="district" , required = false) String district,
										 @RequestParam(value="fax" , required = false) String fax,
										 @RequestParam(value="tel" , required = false) String tel,
										 @RequestParam(value="address" , required = false) String address,
										 @RequestParam(value="companyName" , required = true) String comName , 
										 @RequestParam(value="startDate" , required = true) Date authStartTime , 
										 @RequestParam(value="endDate" , required = true) Date authEndTime
			){
		JSONObject result = JsonUtils.commonJsonReturn();
//		UserType userTypeEnum = UserType.valueStr(userType);
//		if(userTypeEnum == null){
//			return JsonUtils.commonJsonReturn("0001" , "错误的用户类型").toJSONString();
//		}
		if(!UserType.ADMIN.getCode().equals(WebUtil.getUserType(request))){//只有管理员有权限创建主账户
			return JsonUtils.commonJsonReturn("0001" , "无权操作").toJSONString();
		}
		try{
			AdminUser user = new AdminUser();
			user.setCreateUser(WebUtil.getUserName(request) );
			user.setLoginPwd(loginPwd);
			user.setUserName(userName);
			user.setUserType(UserType.EMP_SUB.getCode());
			user.setAuthEndTime(authEndTime);
			user.setAuthStartTime(authStartTime);
			user.setComName(comName);
			user.setComAddress(address);
			user.setComRegion(district);
			user.setFax(fax);
			user.setRoleId(roleId);
			user.setTelephone(tel);
			result = userService.createUser(user);
		}catch(DuplicateKeyException e){
			logger.error("create_usr_acct error userName = "+userName , e);
			return JsonUtils.commonJsonReturn("0002" , "用户已存在").toJSONString();
		}
		catch(Exception e){
			logger.error("create_usr_acct error userName = "+userName , e);
			return JsonUtils.commonJsonReturn("9999" , "系统异常").toJSONString();
		}
		return result.toJSONString();
	}
	
	@RequestMapping(value="/admin/user/login")
	@ResponseBody
	public String userLogin(HttpServletRequest request , HttpServletResponse response , HttpSession session ,
			@RequestParam(value="imgverify" , required = true) String custVerifyCode , 
			@RequestParam(value="username" , required = true) String userName,
			@RequestParam(value="password" , required = true) String loginPwd){
		JSONObject result = JsonUtils.commonJsonReturn();
		//验证图片验证码
		result = verifyImgCode(request, response, session, custVerifyCode);
		if(!JsonUtils.equalDefSuccCode(result) && !BxEnvUtil.isDev()){
			return result.toJSONString();
		}
		//用户账户名和密码验证
		JSONObject loginResult = userService.login(userName, loginPwd);
		if(JsonUtils.equalDefSuccCode(loginResult)){
			@SuppressWarnings("unchecked")
			Map<String , Object> loginMap = loginResult.getJSONObject("body").getObject("loginMap", Map.class);
			session.setAttribute("userType", (String)loginMap.get("userType"));
			session.setAttribute("userId", loginMap.get("userId"));
			session.setAttribute("userName", userName);
		}
		return loginResult.toJSONString();
	}
	
	@RequestMapping(value="/admin/user/logout")
	@ResponseBody
	public String userLogout(HttpServletRequest request , HttpServletResponse response){
		try{
	    	WebUtil.logOut(request);
	    	return JsonUtils.commonJsonReturn().toJSONString();
    	}catch(Exception e){
			logger.error("logout error " ,e);
			return JsonUtils.commonJsonReturn("9999", "系统异常").toJSONString();
		}
	}
	
	
	@RequestMapping(value="/admin/user/modify_pwd")
	@ResponseBody
	public String modify_pwd(HttpServletRequest request , HttpServletResponse response ,
												@RequestParam(value="oldPwd" , required = true) String oldPwd,
												@RequestParam(value="newPwd" , required = true) String newPwd
												){
		try{
			JSONObject result =  userService.modifyLoginPwd(WebUtil.getUserId(request) , oldPwd , newPwd);
//			if(JsonUtils.equalDefSuccCode(result)){
//				userLogout(request, response);
//			}
	    	 return result.toJSONString();
    	}catch(Exception e){
			logger.error("modify_pwd error " ,e);
			return JsonUtils.commonJsonReturn("9999", "系统异常").toJSONString();
		}
	}
	

	@RequestMapping(value="/admin/user/set_verify_msg")
	@ResponseBody
	public String set_verify_msg(HttpServletRequest request , HttpServletResponse response  , HttpSession session,  @RequestParam(value="reserveWord" , required = true) String verifyMsg ){
			JSONObject  result = userService.setVerifyMsg(WebUtil.getUserId(request) , verifyMsg);
			if(JsonUtils.equalDefSuccCode(result)){
				session.setAttribute("verifyMsg",  verifyMsg);
			}
	    	 return result.toJSONString();
	}
	
	@RequestMapping(value="/admin/user/getAdmin")
	@ResponseBody
	public String getUser(HttpServletRequest request , HttpServletResponse response ){
	    	 JSONObject head = new JSONObject();
	    	 head.put("code", "0000");
	    	 head.put("username", WebUtil.getUserName(request));
	    	 head.put("type", WebUtil.getUserType(request));
	    	 JSONObject result = new JSONObject();
	    	 result.put("head", head);
	    	 return result.toJSONString();
	}
	
	@RequestMapping(value="/admin/user/create_Role")
	@ResponseBody
	public String create_Role(HttpServletRequest request , HttpServletResponse response 
			, @RequestParam(value="rolename", required = true) String roleName
			, @RequestParam(value="description", required = false) String description
			, @RequestParam(value="permission", required = true) String permission){
	    	 return userService.createUserRole(WebUtil.getUserName(request) , roleName , permission ,description ).toJSONString();
	}
	
	@RequestMapping(value="/admin/user/get_roles")
	@ResponseBody
	public String create_Role(HttpServletRequest request , HttpServletResponse response ){
			 JSONObject params = WebUtil.getRequestParams(request);
			 params.put("userName", WebUtil.getUserName(request));
	    	 return userService.getAdminRoles(params).toJSONString();
	}
	
	@RequestMapping(value="/admin/user/edit_role")
	@ResponseBody
	public String edit_role(HttpServletRequest request , HttpServletResponse response ){
		 JSONObject params = WebUtil.getRequestParams(request);
	     return userService.editRole(params).toJSONString();
	}
	
	@RequestMapping(value="/admin/user/edit_usr_acct")
	@ResponseBody
	public String edit_usr_acct(HttpServletRequest request , HttpServletResponse response , 
										 @RequestParam(value="id" , required = true) long id,
										 @RequestParam(value="password" , required = true) String loginPwd,
										 @RequestParam(value="roleId" , required = false) long roleId,
										 @RequestParam(value="district" , required = false) String district,
										 @RequestParam(value="fax" , required = false) String fax,
										 @RequestParam(value="tel" , required = false) String tel,
										 @RequestParam(value="address" , required = false) String address,
										 @RequestParam(value="companyName" , required = true) String comName , 
										 @RequestParam(value="startDate" , required = true) Date authStartTime , 
										 @RequestParam(value="endDate" , required = true) Date authEndTime
			){
		JSONObject result = JsonUtils.commonJsonReturn();
//		UserType userTypeEnum = UserType.valueStr(userType);
//		if(userTypeEnum == null){
//			return JsonUtils.commonJsonReturn("0001" , "错误的用户类型").toJSONString();
//		}
		if(!UserType.ADMIN.getCode().equals(WebUtil.getUserType(request))){//只有管理员有权限创建主账户
			return JsonUtils.commonJsonReturn("0001" , "无权操作").toJSONString();
		}
		try{
			AdminUser user = new AdminUser();
			user.setCreateUser(WebUtil.getUserName(request) );
			user.setLoginPwd(loginPwd);
			user.setId(id);
			user.setAuthEndTime(authEndTime);
			user.setAuthStartTime(authStartTime);
			user.setComName(comName);
			user.setComAddress(address);
			user.setComRegion(district);
			user.setFax(fax);
			user.setRoleId(roleId);
			user.setTelephone(tel);
			result = userService.editUser(user);
		}catch(Exception e){
			logger.error("edit_usr_acct error id = "+id , e);
			return JsonUtils.commonJsonReturn("9999" , "系统异常").toJSONString();
		}
		return result.toJSONString();
	}
	
	/**
	 * 不做物理删除，改成无效
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/admin/user/delete_usr_acct")
	@ResponseBody
	public String delete_usr_acct(HttpServletRequest request , HttpServletResponse response , 
										 @RequestParam(value="id" , required = true) long id
			){
		if(!UserType.ADMIN.getCode().equals(WebUtil.getUserType(request))){//只有管理员有权限创建主账户
			return JsonUtils.commonJsonReturn("0001" , "无权操作").toJSONString();
		}
		try{
			return  userService.deleteUser(id).toJSONString();
		}catch(Exception e){
			logger.error("edit_usr_acct error id = "+id , e);
			return JsonUtils.commonJsonReturn("9999" , "系统异常").toJSONString();
		}
	}
}
