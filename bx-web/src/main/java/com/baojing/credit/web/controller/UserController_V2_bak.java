package com.baojing.credit.web.controller;
//package com.baojing.credit.web.controller;
//
//import java.io.IOException;
//import java.util.Date;
//import java.util.Map;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
//import org.apache.commons.lang3.StringUtils;
//import org.apache.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.dao.DuplicateKeyException;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import com.alibaba.fastjson.JSONObject;
//import com.baojing.credit.common.utils.JsonUtils;
//import com.baojing.credit.common.utils.VerifyCodeUtils;
//import com.baojing.credit.enums.UserType;
//import com.baojing.credit.model.AdminUser;
//import com.baojing.credit.service.UserService;
//import com.baojing.credit.utils.BxEnvUtil;
//import com.baojing.credit.web.utils.WebUtil;
//
//@Controller
//public class UserController_bak {
//	
//	private static final Logger logger = Logger.getLogger(UserController_bak.class);
//	
//	@Autowired
//	private UserService userService;
//	
//	@RequestMapping(value="/credit/login/img_code")
//	@ResponseBody
//	public void getImgCode(HttpServletRequest request , HttpServletResponse response , HttpSession session) throws IOException{
//		String verifyCode = VerifyCodeUtils.outputVerifyImage(200, 80, response.getOutputStream(), 4);
//		session.setAttribute("imgVerifyCode", verifyCode);
//	}
//	
//	@RequestMapping(value="/credit/login/imgCode/verify")
//	@ResponseBody
//	public JSONObject verifyImgCode(HttpServletRequest request , HttpServletResponse response , HttpSession session , @RequestParam(value="imgverify" , required = true) String custVerifyCode){
//		JSONObject result = JsonUtils.commonJsonReturn();
//		String sessionCode = (String)session.getAttribute("imgVerifyCode");
//		if(!StringUtils.equalsIgnoreCase(custVerifyCode, sessionCode)){
//			return JsonUtils.commonJsonReturn("0001", "验证码错误");
//		}
//		session.removeAttribute("imgVerifyCode");
//		return result;
//	}
//	
//	@RequestMapping(value="/credit/user/login")
//	@ResponseBody
//	public String userLogin(HttpServletRequest request , HttpServletResponse response , HttpSession session ,
//			@RequestParam(value="imgverify" , required = true) String custVerifyCode , 
//			@RequestParam(value="username" , required = true) String userName,
//			@RequestParam(value="password" , required = true) String loginPwd){
//		JSONObject result = JsonUtils.commonJsonReturn();
//		//验证图片验证码
//		result = verifyImgCode(request, response, session, custVerifyCode);
//		if(!JsonUtils.equalDefSuccCode(result) && !BxEnvUtil.isDev()){
//			return result.toJSONString();
//		}
//		//用户账户名和密码验证
//		JSONObject loginResult = userService.login(userName, loginPwd);
//		if(JsonUtils.equalDefSuccCode(loginResult)){
//			@SuppressWarnings("unchecked")
//			Map<String , Object> loginMap = loginResult.getJSONObject("body").getObject("loginMap", Map.class);
//			session.setAttribute("userType", (String)loginMap.get("userType"));
//			session.setAttribute("userId", loginMap.get("userId"));
//			session.setAttribute("userName", userName);
//			session.setAttribute("verifyMsg",  loginMap.get("verifyMsg"));
//			session.setAttribute("comName",  loginMap.get("comName"));
//		}
//		return loginResult.toJSONString();
//	}
//	
//	@RequestMapping(value="/credit/user/logout")
//	@ResponseBody
//	public String userLogout(HttpServletRequest request , HttpServletResponse response){
//		try{
//	    	WebUtil.logOut(request);
//	    	return JsonUtils.commonJsonReturn().toJSONString();
//    	}catch(Exception e){
//			logger.error("logout error " ,e);
//			return JsonUtils.commonJsonReturn("9999", "系统异常").toJSONString();
//		}
//	}
//	
//	@RequestMapping(value="/credit/user/add_sub_user")
//	@ResponseBody
//	public String add_sub_user(HttpServletRequest request , HttpServletResponse response ,
//												@RequestParam(value="subLoginPwd" , required = true) String subLoginPwd,
//												@RequestParam(value="subUserName" , required = true) String subUserName){
//		try{
//	    	 String currUserType = WebUtil.getUserType(request);
//	    	 if(!UserType.EMP_MAIN.getCode().equals(currUserType)){//无操作权限
//	    		 return JsonUtils.commonJsonReturn("0001", "无权创建子账户").toJSONString();
//	    	 }
//	    	 return userService.createSubUser(WebUtil.getUserId(request) , WebUtil.getUserName(request) , subLoginPwd , subUserName).toJSONString();
//    	}catch(DuplicateKeyException e){
//			logger.error("add_sub_user error subUserName = "+subUserName , e);
//			return JsonUtils.commonJsonReturn("0002" , "用户已存在").toJSONString();
//		}catch(Exception e){
//			logger.error("add_sub_user error " ,e);
//			return JsonUtils.commonJsonReturn("9999", "系统异常").toJSONString();
//		}
//	}
//	
//	@RequestMapping(value="/credit/user/modify_pwd")
//	@ResponseBody
//	public String modify_pwd(HttpServletRequest request , HttpServletResponse response ,
//												@RequestParam(value="oldPwd" , required = true) String oldPwd,
//												@RequestParam(value="newPwd" , required = true) String newPwd
//												){
//		try{
//			JSONObject result =  userService.modifyLoginPwd(WebUtil.getUserId(request) , oldPwd , newPwd);
////			if(JsonUtils.equalDefSuccCode(result)){
////				userLogout(request, response);
////			}
//	    	 return result.toJSONString();
//    	}catch(Exception e){
//			logger.error("modify_pwd error " ,e);
//			return JsonUtils.commonJsonReturn("9999", "系统异常").toJSONString();
//		}
//	}
//	
//	@RequestMapping(value="/credit/user/query_sub_user")
//	@ResponseBody
//	public String query_sub_user(HttpServletRequest request , 	HttpServletResponse response){
//			 JSONObject result = userService.querySubUser(WebUtil.getUserId(request));
//			 JsonUtils.setBody(result, "mainUserId", WebUtil.getUserId(request));
//			 JsonUtils.setBody(result, "mainUserName", WebUtil.getUserName(request));
//	    	 return result.toJSONString();
//	}
//
//	@RequestMapping(value="/credit/user/set_verify_msg")
//	@ResponseBody
//	public String set_verify_msg(HttpServletRequest request , HttpServletResponse response  , HttpSession session,  @RequestParam(value="reserveWord" , required = true) String verifyMsg ){
//			JSONObject  result = userService.setVerifyMsg(WebUtil.getUserId(request) , verifyMsg);
//			if(JsonUtils.equalDefSuccCode(result)){
//				session.setAttribute("verifyMsg",  verifyMsg);
//			}
//	    	 return result.toJSONString();
//	}
//	
//	@RequestMapping(value="/credit/user/getUser")
//	@ResponseBody
//	public String getUser(HttpServletRequest request , HttpServletResponse response ){
//	    	 JSONObject head = new JSONObject();
//	    	 head.put("code", "0000");
//	    	 head.put("username", WebUtil.getUserName(request));
//	    	 head.put("type", WebUtil.getUserType(request));
//	    	 head.put("reserveword", WebUtil.getVerifyMsg(request));
//	    	 JSONObject result = new JSONObject();
//	    	 result.put("head", head);
//	    	 return result.toJSONString();
//	}
//	
//	@RequestMapping(value="/credit/user/del_sub_user")
//	@ResponseBody
//	public String del_sub_user(HttpServletRequest request , HttpServletResponse response , @RequestParam(value="id" , required = true) long subUserId){
//		 String currUserType = WebUtil.getUserType(request);
//    	 if(!UserType.EMP_MAIN.getCode().equals(currUserType)){//无操作权限
//    		 return JsonUtils.commonJsonReturn("0001", "无权删除子账户").toJSONString();
//    	 }
//	    return userService.deleteSubUser(WebUtil.getUserId(request) , subUserId).toJSONString();
//	}
//	
//}
