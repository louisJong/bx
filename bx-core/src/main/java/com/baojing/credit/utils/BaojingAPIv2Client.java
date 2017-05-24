package com.baojing.credit.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baojing.credit.common.utils.JsonUtils;
import com.baojing.credit.common.utils.ResourceUtils;
import com.baojing.credit.model.Credit;
import com.baojing.credit.model.Header;
import com.baojing.credit.model.QueryReq;
import com.baojing.credit.model.QueryRsp;
import com.baojing.credit.model.ReqBodyName;
import com.baojing.credit.model.ReqBodyNamePerson;
import com.baojing.credit.model.ReqBodyNameSkip;
import com.baojing.credit.model.ReqBodySearchkey;
import com.fasterxml.jackson.core.type.TypeReference;

public class BaojingAPIv2Client {
	private static final Logger logger = Logger.getLogger(BaojingAPIv2Client.class);
	private static final String url = ResourceUtils.getValue("bx-core", "credit.interface.access.baseurl");
	public static final String key = "23fsic4EW16NR7pp";
	
	public static JSONObject reqNamePerson(String formName, String name, String person , String userName) {
		String reqBodyPlain;
		ReqBodyNamePerson reqBody = new ReqBodyNamePerson();
		reqBody.setName(name);
		reqBody.setPerson(person);
		reqBodyPlain = JsonUtil.toShortJson(reqBody);
	     return	baojingV2( formName, reqBodyPlain , userName);
	}
	
	public static JSONObject reqSearchKey(String formName, String searchKey, String type , String userName) {
		String reqBodyPlain;
		ReqBodySearchkey reqBody = new ReqBodySearchkey();
		reqBody.setSearchKey(searchKey);
		reqBody.setType(type);
		reqBodyPlain = JsonUtil.toShortJson(reqBody);
		return	baojingV2( formName, reqBodyPlain , userName);
	}
	
	public static JSONObject reqName(String formName, String name , String userName) {
		String reqBodyPlain;
		ReqBodyName reqBody = new ReqBodyName();
		reqBody.setName(name);
		reqBodyPlain = JsonUtil.toShortJson(reqBody);
		return baojingV2( formName, reqBodyPlain , userName);
	}
	
	public static JSONObject reqNameSkip(String formName, String name, Long skip , String userName) {
		String reqBodyPlain;
		ReqBodyNameSkip reqBody = new ReqBodyNameSkip();
		reqBody.setName(name);
		reqBody.setSkip(skip.intValue());
		reqBodyPlain = JsonUtil.toShortJson(reqBody);
		return baojingV2( formName, reqBodyPlain , userName);
	}
	
	public static JSONObject baojingV2(String formName, String reqBodyPlain , String userName){
		String[] urls = url.split(",");
		String url=urls[0]+formName;
		if(StringUtils.isNotEmpty(userName)){
			int hash = userName.hashCode();
			int index= hash%urls.length;
			url = urls[index]+formName;
		}
		QueryReq<String> req = new QueryReq<String>();
		Credit<String> credit = new Credit<String>();
		req.setCredit(credit);
		Header header = new Header();
		header.setStreamingNo(DateTimeUtil.getCurrentTime()+MyStringUtil.getRandomNums(4));
		header.setSrcSystemNo("10000");
		header.setVersion("0002");
		credit.setHeader(header);
		String bodyStr = "";
		logger.info(reqBodyPlain);
		try {
			bodyStr = AesEncrypt.encrypt(reqBodyPlain, key);
		} catch (Exception e) {
			logger.error("baojingV2 error " , e );
		}
		bodyStr = bodyStr.replace("\r\n", "");
		credit.setBody(bodyStr);
		JSONObject signJson = new JSONObject();
		signJson.put("header", header);
		signJson.put("body", bodyStr);
		String sign =  MD5Encrypter.md5(("\"header\":"+JsonUtil.toShortJson(header)+",\"body\":\""+bodyStr+"\"").getBytes());
//		MD5Util.MD5Encode("\"header\":"+JsonUtil.toShortJson(header)+",\"body\":\""+bodyStr+"\"", "utf-8");
		credit.setSign(sign);
		logger.info("Req: "+JsonUtil.toShortJson(req));
		String respStr = HttpUtil.doPost(url, req);
		logger.debug("Resp: "+respStr);
		QueryRsp<String> resp = JsonUtil.fromJson(respStr, new TypeReference<QueryRsp<String>>(){});
		JSONObject result = null;
		if(resp != null) {
			String rspCode = resp.getCredit().getHeader().getRspCode();
			String rspDesc = resp.getCredit().getHeader().getRspDesc();
			if("00000".equals(rspCode)){//成功
				result = JsonUtils.commonDataInterfaceJsonReturn(userName);
				result.put("body", JSON.parse(getBodyPlain(resp)));
			}else{
				result = JsonUtils.commonDataInterfaceJsonReturn(userName, rspCode, rspDesc, true);
			}
		}
		return result;
	}

	private static String getBodyPlain(QueryRsp<String> resp) {
		String bodyCipher = resp.getCredit().getBody();
		String bodyPlain = null;
		try {
			bodyPlain = AesEncrypt.decrypt(bodyCipher, key);
			logger.info("body decrypted: "+bodyPlain);
		} catch (Exception e) {
			logger.error("baojingV2  bodyPlain  decrypt error " , e );
		}
		return bodyPlain;
	}

	public static void main(String[] args){
		System.out.println(reqSearchKey("qygszcqry", "北京小米科技有限责任公司", "1", "jiang"));
//		System.out.println(reqNameSkip( "dcdyxxqry", "江苏中超新材料科技有限公司", 0));
		
	}
	
}
