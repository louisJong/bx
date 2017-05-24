package com.baojing.credit.web.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.baojing.credit.service.DataFetchService;
import com.baojing.credit.web.utils.WebUtil;

@Controller
public class DataAccessController {
	
	@Autowired
	private DataFetchService dataFetchService;
	
	@RequestMapping(value="/credit/data/comm/access/enter")
	@ResponseBody
	public String access(HttpServletRequest request , HttpServletResponse response ) {
		JSONObject params = WebUtil.getRequestParams(request);
		params.put("userName", WebUtil.getUserName(request));
		params.put("userId", WebUtil.getUserId(request));
		params.put("userType", WebUtil.getUserType(request));
		params.put("userComName", WebUtil.getComName(request));
		return dataFetchService.commDataFetch(params).toJSONString();
	}
	
//	@RequestMapping(value="/credit/data/download/enter")
//	@ResponseBody
//	public String download(HttpServletRequest request , HttpServletResponse response ) {
//		JSONObject params = WebUtil.getRequestParams(request);
//		params.put("userName", WebUtil.getUserName(request));
//		params.put("userId", WebUtil.getUserId(request));
//		params.put("userType", WebUtil.getUserType(request));
//		params.put("userComName", WebUtil.getComName(request));
//		return dataFetchService.dataDownLoad(params).toJSONString();
//	}
	
//	@RequestMapping(value="/credit/com/data/query/log")
//	@ResponseBody
//	public String dataFetchLogQuery(HttpServletRequest request , HttpServletResponse response , @RequestParam(value="starttime" , required = false) @DateTimeFormat(pattern="yyyy-MM-dd HH:mm") Date starttime , @RequestParam(value="endtime" , required = false) @DateTimeFormat(pattern="yyyy-MM-dd HH:mm")  Date endtime ) {
//		JSONObject params = WebUtil.getRequestParams(request);
//		params.put("userId", WebUtil.getUserId(request));
//		params.put("userType", WebUtil.getUserType(request));
//		params.put("userName", WebUtil.getUserName(request));
//		params.put("starttime", starttime);
//		params.put("endtime", endtime);
//		return dataFetchService.dataQueryLogQuery(params).toJSONString();
//	}
	
	@RequestMapping(value="/credit/com/data/query/log")
	@ResponseBody
	public String dataFetchLogQuery(HttpServletRequest request , HttpServletResponse response , @RequestParam(value="starttime" , required = false) @DateTimeFormat(pattern="yyyy-MM-dd HH:mm") Date starttime , @RequestParam(value="endtime" , required = false) @DateTimeFormat(pattern="yyyy-MM-dd HH:mm")  Date endtime ) {
		JSONObject params = WebUtil.getRequestParams(request);
		params.put("userName", WebUtil.getUserName(request));
		params.put("starttime", starttime);
		params.put("endtime", endtime);
		return dataFetchService.dataQueryLogQueryV1(params).toJSONString();
	}
	
	
//	@RequestMapping(value="/credit/com/data/download/log")
//	@ResponseBody
//	public String dataDownloadLogQuery(HttpServletRequest request , HttpServletResponse response  ) {
//		JSONObject params = WebUtil.getRequestParams(request);
//		params.put("userId", WebUtil.getUserId(request));
//		params.put("userType", WebUtil.getUserType(request));
//		params.put("userName", WebUtil.getUserName(request));
//		return dataFetchService.downloadLogQuery(params).toJSONString();
//	}
	
	@RequestMapping(value="/credit/hot/coms")
	@ResponseBody
	public String hot_coms(HttpServletRequest request , HttpServletResponse response  , @RequestParam(value="comName" , required = false) String comName) {
		return dataFetchService.queryHotComps(comName, WebUtil.getUserName(request) ,  WebUtil.getComName(request)).toJSONString();
	}
	
	@RequestMapping(value="/credit/com/trends")
	@ResponseBody
	public String trends(HttpServletRequest request , HttpServletResponse response ) {
		return dataFetchService.queryTrend(WebUtil.getUserName(request)).toJSONString();
	}
}
