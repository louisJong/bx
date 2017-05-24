package com.baojing.credit.web.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.baojing.credit.common.utils.JsonUtils;
import com.baojing.credit.common.utils.VerifyCodeUtils;
import com.baojing.credit.service.OldUserService;
import com.baojing.credit.utils.BxEnvUtil;
import com.baojing.credit.web.utils.WebUtil;

@Controller
public class UserController {
	
	@Autowired
	private OldUserService userService;
	
	@RequestMapping(value="/credit/login/img_code")
	@ResponseBody
	public void getImgCode(HttpServletRequest request , HttpServletResponse response , HttpSession session) throws IOException{
		String verifyCode = VerifyCodeUtils.outputVerifyImage(200, 80, response.getOutputStream(), 4);
		session.setAttribute("imgVerifyCode", verifyCode);
	}
	
	@RequestMapping(value="/credit/login/imgCode/verify")
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
	
	
	
	@RequestMapping(value="/credit/user/login")
	@ResponseBody
	public String userLogin(HttpServletRequest request , HttpServletResponse response , HttpSession session ,
			@RequestParam(value="imgverify" , required = true) String custVerifyCode , 
			@RequestParam(value="username" , required = true) String userName,
			@RequestParam(value="password" , required = true) String loginPwd){
		JSONObject result = JsonUtils.commonJsonReturn();
		//验证图片验证码
		result = verifyImgCode(request, response, session, custVerifyCode);
		if(!JsonUtils.equalDefSuccCode(result) ){
			return result.toJSONString();
		}
		//用户账户名和密码验证
		JSONObject loginResult = userService.login(userName, loginPwd);
		if(JsonUtils.equalDefSuccCode(loginResult)){
			@SuppressWarnings("unchecked")
			Map<String , Object> loginMap = loginResult.getJSONObject("body").getObject("loginMap", Map.class);
			session.setAttribute("userId", loginMap.get("userId"));
			session.setAttribute("userName", userName);
			session.setAttribute("comName",  loginMap.get("comName"));
			session.setAttribute("verifyMsg",  loginMap.get("reserveWord"));
		}
		return loginResult.toJSONString();
	}
	
	@RequestMapping(value="/credit/user/getUser")
	@ResponseBody
	public String getUser(HttpServletRequest request , HttpServletResponse response ){
	    	 JSONObject head = new JSONObject();
	    	 head.put("code", "0000");
	    	 head.put("username", WebUtil.getUserName(request));
	    	 head.put("reserveword", WebUtil.getVerifyMsg(request));
	    	 JSONObject result = new JSONObject();
	    	 result.put("head", head);
	    	 return result.toJSONString();
	}
	
	@RequestMapping(value="/credit/user/logout")
	@ResponseBody
	public String userLogout(HttpServletRequest request , HttpServletResponse response){
		try{
	    	WebUtil.logOut(request);
	    	return JsonUtils.commonJsonReturn().toJSONString();
    	}catch(Exception e){
			return JsonUtils.commonJsonReturn("9999", "系统异常").toJSONString();
		}
	}
	
	@RequestMapping(value="/credit/user/set_verify_msg")
	@ResponseBody
	public String set_verify_msg(HttpServletRequest request , HttpServletResponse response  , HttpSession session,  @RequestParam(value="reserveWord" , required = true) String verifyMsg ){
			JSONObject  result = userService.setVerifyMsg(WebUtil.getUserId(request) , verifyMsg);
			if(JsonUtils.equalDefSuccCode(result)){
				session.setAttribute("verifyMsg",  verifyMsg);
			}
	    	 return result.toJSONString();
	}
	
}
