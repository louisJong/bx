package com.baojing.credit.admin.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.baojing.credit.admin.util.WebUtil;
import com.baojing.credit.service.DataFetchService;

@Controller
public class DataAccessController {
	
	@Autowired
	private DataFetchService dataFetchService;
	
	@RequestMapping(value="/admin/com/data/query/log")
	@ResponseBody
	public String dataFetchLogQuery(HttpServletRequest request , HttpServletResponse response  ) {
		JSONObject params = WebUtil.getRequestParams(request);
		params.put("userName", WebUtil.getUserName(request));
		return dataFetchService.dataQueryLogQueryAll(params).toJSONString();
	}
	
	
	@RequestMapping(value="/admin/com/data/download/log")
	@ResponseBody
	public String dataDownloadLogQuery(HttpServletRequest request , HttpServletResponse response  ) {
		JSONObject params = WebUtil.getRequestParams(request);
		params.put("userName", WebUtil.getUserName(request));
		return dataFetchService.downloadLogQueryAll(params).toJSONString();
	}
}
