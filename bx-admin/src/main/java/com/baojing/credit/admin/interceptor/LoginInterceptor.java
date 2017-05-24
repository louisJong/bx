package com.baojing.credit.admin.interceptor;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSONObject;
import com.baojing.credit.admin.util.WebUtil;
import com.baojing.credit.utils.BxEnvUtil;

public class LoginInterceptor extends HandlerInterceptorAdapter {
//	private static Logger logger = Logger.getLogger(LoginInterceptor.class);
	
	@Override
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
		response.addHeader("Access-Control-Allow-Origin"	, "*");
		response.addHeader("Access-Control-Allow-Headers"	, "X-Requested-With");
		if(BxEnvUtil.isDev() ){
			response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
	        response.setHeader("Access-Control-Allow-Credentials", "true");
		}
		if(request.getServletPath().contains("/login") || request.getServletPath().contains("/logout")){
			return true;
		}
		if(!WebUtil.isLogin(request)){
			JSONObject isLoginJson = new JSONObject();
			JSONObject head = new JSONObject();
			head.put("code", "9900");
			head.put("msg", "未登录，请先登录");
			isLoginJson.put("head", head);
			response.setContentType("text/html; charset=utf-8");
			PrintWriter writer = response.getWriter();
			writer.write(isLoginJson.toJSONString());
			writer.flush();
			return false;
		}
//		String preUrl = request.getHeader("REFERER");//父url,为空则为手动输入的url，跳至首页
//		
//		String servletPath = request.getServletPath();//当前访问url,有权限则访问
		return true;
	}
	
}

