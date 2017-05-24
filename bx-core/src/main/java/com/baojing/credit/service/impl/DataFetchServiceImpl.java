package com.baojing.credit.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baojing.credit.common.utils.CommonUtils;
import com.baojing.credit.common.utils.JsonUtils;
import com.baojing.credit.dao.CompanyListDao;
import com.baojing.credit.dao.DataAccessLogDao;
import com.baojing.credit.dao.DataDownloadLogDao;
import com.baojing.credit.dao.LogsDao;
import com.baojing.credit.dao.TrendDao;
import com.baojing.credit.enums.DataInterface;
import com.baojing.credit.model.CompanyList;
import com.baojing.credit.model.DataAccessLog;
import com.baojing.credit.model.DataDownloadLog;
import com.baojing.credit.model.Logs;
import com.baojing.credit.model.Trend;
import com.baojing.credit.service.DataFetchService;
import com.baojing.credit.utils.BaojingAPIv2Client;

@Component
public class DataFetchServiceImpl implements DataFetchService {

	private static final Logger logger = Logger.getLogger(DataFetchServiceImpl.class);
	
	@Autowired
	private DataAccessLogDao dataAccessLogDao;
	
	@Autowired
	private DataDownloadLogDao dataDownloadLogDao;
	
	@Autowired
	private CompanyListDao companyListDao;
	
	@Autowired
	private TrendDao trendDao;
	
	@Autowired
	private LogsDao logsDao;
	
	@Override
	public JSONObject commDataFetch(JSONObject params) {
		String userName = params.getString("userName");
		String formName = params.getString("formName");
		long page = params.getLongValue("page");
		String searchComName = params.getString("searchComName");
		long userId = params.getLongValue("userId");
		String userType = params.getString("userType");
		String userComName = params.getString("userComName");
		DataInterface inter = DataInterface.valueStr(formName);
		if(inter == null){//不识别的访问接口
			return JsonUtils.commonDataInterfaceJsonReturn(userName, "0001", "不识别的接口", false);
		}
		if(StringUtils.isEmpty(searchComName)){//搜索公司未输入
			return JsonUtils.commonDataInterfaceJsonReturn(userName, "0001", "搜索公司不能为空", false);
		}
		logger.info("commDataFetch param:"+params);
		JSONObject result = null;
		String seachExtraParam = params.getString("seachExtraParam");
		try{
			if(DataInterface.qygszcqry.equals(inter) 
					|| DataInterface.jbdjxxqry.equals(inter) 
					|| DataInterface.gdxxqry.equals(inter)  
					|| DataInterface.zyryqry.equals(inter) 
					|| DataInterface.fzjgqry.equals(inter)
					|| DataInterface.gsbgxxqry.equals(inter) ){//企业工商注册信息
				result = BaojingAPIv2Client.reqSearchKey(DataInterface.qygszcqry.getCode(), searchComName, seachExtraParam , userName);
			}else if(DataInterface.qydjgqry.equals(inter)){//企业董监高信息
				result = BaojingAPIv2Client.reqNamePerson(inter.getCode(), searchComName, seachExtraParam , userName);
			}else{
				if(DataInterface.qygsnbqry.equals(inter) 
						|| DataInterface.qyzpxxqry.equals(inter)
						|| DataInterface.qyjgdmqry.equals(inter)
						|| DataInterface.qyymxxqry.equals(inter)
						|| DataInterface.qyfrtzqry.equals(inter)){
					result = BaojingAPIv2Client.reqName(inter.getCode(), searchComName , userName);
				}else{
					result = BaojingAPIv2Client.reqNameSkip(inter.getCode(), searchComName,  page , userName);
				}
			}
			Assert.isTrue(result!=null);
			if(JsonUtils.equalDefSuccCode(result) || "90002".equals(result.getJSONObject("head").getString("code"))){
				addDataAccessLogV1Table(inter.getDesc(), page, searchComName, userName, userComName , 0);
			}
		}catch(Exception e){
			logger.error("commDataFetch error "  , e);
//			addDataAccessLog(inter.getDesc(), null, page, searchComName, userId, userName, userType, userComName, e, "fail", null);
			result = JsonUtils.commonDataInterfaceJsonReturn(userName, "9999", e.getMessage(), false);
		}
		return result;
	}
	
	private long addDataAccessLog(String formName , String seachExtraParam , Long page, String searchComName , Long userId , String userName , String userType , String userComName , Exception ex , String dataStatus , String retMsg){
		DataAccessLog dataLog = new DataAccessLog();
		dataLog.setDataFormName(formName);
		dataLog.setCreateTime(new Date());
		dataLog.setPageIndex(page);
		dataLog.setSearchComName(searchComName);
		dataLog.setUserId(userId);
		dataLog.setUserName(userName);
		dataLog.setUserType(userType);
		dataLog.setUserComName(userComName);
		dataLog.setDataStatus(dataStatus);
		dataLog.setSeachExtraParam(seachExtraParam);
		dataLog.setRetMsg(retMsg);
		if(ex!=null){
			dataLog.setExceptionMsg("type:"+ex.getClass().getName()+" | msg:"+ex.getMessage());
		}
	    return dataAccessLogDao.insert(dataLog);
	}
	
	private long addDataAccessLogV1Table(String formName , Long page, String searchComName ,String userName ,  String userComName , int isSearch ){
		Logs log = new Logs();
		log.setCompanyName(searchComName);
		log.setVisitTime(new Date());
		log.setFormName(formName);
		log.setIsPdf(0);
		log.setIsSearch(isSearch);
		log.setPage(page);
		log.setUserComName(userComName);
		log.setUsername(userName);
	    return logsDao.insert(log);
	}

	@Override
	public JSONObject dataDownLoad(JSONObject params) {
		String userName = params.getString("userName");
		String downloadFormName = params.getString("downloadFormName");
		String level = params.getString("level");
		String downloadComName = params.getString("downloadComName");
		long userId = params.getLongValue("userId");
		String userType = params.getString("userType");
		String userComName = params.getString("userComName");
		JSONObject result = JsonUtils.commonDataInterfaceJsonReturn(userName);
		try{
			//TODO 调用接口
			addDataDownloadLog(level, downloadFormName, downloadComName, userId, userName, userType, userComName, null, "success", "");
		}catch(Exception e){
			addDataDownloadLog(level, downloadFormName, downloadComName, userId, userName, userType, userComName, e, "fail", null);
			result = JsonUtils.commonDataInterfaceJsonReturn(userName, "9999", e.getMessage(), false);
		}
		return result;
	}
	
	private long addDataDownloadLog(String level , String downloadFormName , String downloadComName , Long userId , String userName , String userType , String userComName , Exception ex , String dataStatus , String retMsg){
		DataDownloadLog dataLog = new DataDownloadLog();
		dataLog.setCreateTime(new Date());
		dataLog.setUserId(userId);
		dataLog.setUserName(userName);
		dataLog.setUserType(userType);
		dataLog.setUserComName(userComName);
		dataLog.setDownloadComName(downloadComName);
		dataLog.setDownloadFormName(downloadFormName);
		dataLog.setReportType(level);
		dataLog.setDataStatus(dataStatus);
		dataLog.setRetMsg(retMsg);
		if(ex!=null){
			dataLog.setExceptionMsg("type:"+ex.getClass().getName()+" | msg:"+ex.getMessage());
		}
		return dataDownloadLogDao.insert(dataLog);
	}

	@Override
	public JSONObject dataQueryLogQuery(JSONObject params) {
		long userId = params.getLongValue("userId");
		String userType = params.getString("userType");
		String userName = params.getString("userName");
		String searchName = params.getString("searchname");
		Date startTime = params.getDate("starttime");
		Date endTime = params.getDate("endtime");
		int page = params.getIntValue("page");
		Integer limit = params.getInteger("limit");
		limit = limit==null? 20 : limit;//默认取20条
		startTime = startTime==null? CommonUtils.getDate00000(0) : startTime ; 
		endTime = endTime==null? new Date() : endTime;
		Map<String , Object> param = new HashMap<String, Object>();
		param.put("userId", userId);
		param.put("userType", userType);
		param.put("startTime", startTime);
		param.put("endTime", endTime);
		param.put("startRow", page*limit);
		param.put("searchName", searchName);
		param.put("limit", limit);
		long count = dataAccessLogDao.queryCount(param);
		List<Map<String , Object>> queryList = dataAccessLogDao.queryRecord(param);
		JSONObject result = JsonUtils.commonDataInterfaceJsonReturn(userName);
		JsonUtils.setBody(result, "total", count);
		JsonUtils.setBody(result, "accessWebLogs", queryList);
		return result;
	}
	
	
	
	@Override
	public JSONObject downloadLogQuery(JSONObject params) {
		long userId = params.getLongValue("userId");
		String userType = params.getString("userType");
		String userName = params.getString("userName");
		String searchName = params.getString("searchname");
		Date startTime = params.getDate("startTime");
		Date endTime = params.getDate("endTime");
		int page = params.getIntValue("page");
		Integer limit = params.getInteger("limit");
		limit = limit==null? 20 : limit;//默认取20条
		startTime = startTime==null? CommonUtils.getDate00000(0) : startTime ; 
		endTime = endTime==null? new Date() : endTime;
		Map<String , Object> param = new HashMap<String, Object>();
		param.put("userId", userId);
		param.put("userType", userType);
		param.put("startTime", startTime);
		param.put("searchName", searchName);
		param.put("endTime", endTime);
		param.put("startRow", page*limit);
		param.put("limit", limit);
		long count = dataDownloadLogDao.queryCount(param);
		List<Map<String , Object>> queryList = dataDownloadLogDao.queryRecord(param);
		JSONObject result = JsonUtils.commonDataInterfaceJsonReturn(userName);
		JsonUtils.setBody(result, "total", count);
		JsonUtils.setBody(result, "downloadLogs", queryList);
		return result;
	}

	@Override
	public JSONObject dataQueryLogQueryAll(JSONObject params) {
		String userName = params.getString("userName");
		String searchName = params.getString("searchname");
		String userCompany = params.getString("usercompany");
		Date startTime = params.getDate("starttime");
		Date endTime = params.getDate("endtime");
		int page = params.getIntValue("page");
		Integer limit = params.getInteger("limit");
		limit = limit==null? 20 : limit;//默认取20条
		startTime = startTime==null? CommonUtils.getDate00000(0) : startTime ; 
		endTime = endTime==null? new Date() : endTime;
		Map<String , Object> param = new HashMap<String, Object>();
		param.put("startTime", startTime);
		param.put("endTime", endTime);
		param.put("startRow", page*limit);
		param.put("userCompany", userCompany);
		param.put("searchName", searchName);
		param.put("limit", limit);
		long count = dataAccessLogDao.queryAllCount(param);
		List<Map<String , Object>> queryList = dataAccessLogDao.queryAllRecord(param);
		JSONObject result = JsonUtils.commonDataInterfaceJsonReturn(userName);
		JsonUtils.setBody(result, "total", count);
		JsonUtils.setBody(result, "accessWebLogs", queryList);
		return result;
	}

	@Override
	public JSONObject downloadLogQueryAll(JSONObject params) {
		String userName = params.getString("userName");
		String searchName = params.getString("searchname");
		String userCompany = params.getString("usercompany");
		Date startTime = params.getDate("startTime");
		Date endTime = params.getDate("endTime");
		int page = params.getIntValue("page");
		Integer limit = params.getInteger("limit");
		limit = limit==null? 20 : limit;//默认取20条
		startTime = startTime==null? CommonUtils.getDate00000(0) : startTime ; 
		endTime = endTime==null? new Date() : endTime;
		Map<String , Object> param = new HashMap<String, Object>();
		param.put("startTime", startTime);
		param.put("searchName", searchName);
		param.put("userCompany", userCompany);
		param.put("endTime", endTime);
		param.put("startRow", page*limit);
		param.put("limit", limit);
		long count = dataDownloadLogDao.queryAllCount(param);
		List<Map<String , Object>> queryList = dataDownloadLogDao.queryAllRecord(param);
		JSONObject result = JsonUtils.commonDataInterfaceJsonReturn(userName);
		JsonUtils.setBody(result, "total", count);
		JsonUtils.setBody(result, "downloadLogs", queryList);
		return result;
	}

	@Override
	public JSONObject queryHotComps(String comName , String userName , String userComName) {
		JSONObject result = JsonUtils.commonDataInterfaceJsonReturn(userName);
		List<CompanyList> comList = companyListDao.findByNameLimit10(comName);
		if(CollectionUtils.isEmpty(comList)){
			result = BaojingAPIv2Client.reqSearchKey(DataInterface.qygszcqry.getCode(), comName, "1" , userName);
		}
		Assert.isTrue(result!=null);
		if(JsonUtils.equalDefSuccCode(result) || "90002".equals(result.getJSONObject("head").getString("code"))){
			addDataAccessLogV1Table(DataInterface.jbdjxxqry.getDesc(), 0l, comName, userName, userComName , 1);
		}
		JSONObject body = result.getJSONObject("body");
		JSONArray enterpriseList = body.getJSONArray("enterpriseList");
		if(enterpriseList != null && !enterpriseList.isEmpty()){
			JSONObject enterprise = enterpriseList.getJSONObject(0);
			comList = new ArrayList<CompanyList>();
			CompanyList comp = new CompanyList();
			comp.setId(1l);
			comp.setName(comName);
			comp.setOperName(enterprise.getString("frName"));
			comp.setAddress(enterprise.getString("dom"));
			comp.setRegistCapi(enterprise.getString("regCap"));
			comp.setStartDate(enterprise.getString("esDate"));
			comList.add(comp);
		}
		JsonUtils.setBody(result,"comps", comList);
		return result;
	}

	@Override
	public JSONObject queryTrend(String userName) {
		JSONObject result = JsonUtils.commonDataInterfaceJsonReturn(userName);
		List<Trend>  trends= trendDao.findLimit10();
		JsonUtils.setBody(result,"trends", trends);
		return result;
	}

	@Override
	public JSONObject dataQueryLogQueryV1(JSONObject params) {
		String userName = params.getString("userName");
		String searchName = params.getString("searchname");
		Date startTime = params.getDate("starttime");
		Date endTime = params.getDate("endtime");
		int page = params.getIntValue("page");
		Integer limit = params.getInteger("limit");
		limit = limit==null? 20 : limit;//默认取20条
		startTime = startTime==null? CommonUtils.getDate00000(0) : startTime ; 
		endTime = endTime==null? new Date() : endTime;
		Map<String , Object> param = new HashMap<String, Object>();
		param.put("startTime", startTime);
		param.put("userName", userName);
		param.put("searchName", searchName);
		param.put("endTime", endTime);
		param.put("startRow", page*limit);
		param.put("limit", limit);
		long count = logsDao.queryCount(param);
		List<Map<String , Object>> queryList = logsDao.queryRecord(param);
		JSONObject result = JsonUtils.commonDataInterfaceJsonReturn(userName);
		JsonUtils.setBody(result, "total", count);
		JsonUtils.setBody(result, "accessWebLogs", queryList);
		return result;
	}

}
