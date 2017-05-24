package com.baojing.credit.service;

import com.alibaba.fastjson.JSONObject;

public interface DataFetchService {
	
	public JSONObject commDataFetch(JSONObject params);
	
	public JSONObject dataDownLoad(JSONObject params);

	public JSONObject dataQueryLogQuery(JSONObject params);
	
	public JSONObject downloadLogQuery(JSONObject params);

	public JSONObject dataQueryLogQueryAll(JSONObject params);

	public JSONObject downloadLogQueryAll(JSONObject params);
	
	public JSONObject queryHotComps(String comName , String userName, String userComName);
	
	public JSONObject queryTrend(String userName);
	
	public JSONObject dataQueryLogQueryV1(JSONObject params);
}
