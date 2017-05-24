package com.baojing.credit.web.utils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
public class WebUtil {
	private static Logger logger = Logger.getLogger(WebUtil.class);
	
	/**
	 * @param request
	 * @return 不会返回空的对象
	 */
	public static String getReqData(HttpServletRequest request) {
		BufferedReader reader = null;
		StringBuilder sb = new StringBuilder();
		String line = null;
		try {
			reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
		} catch (IOException e) {
			logger.error(e);
		}finally{
			if(reader!=null){
				try {
					reader.close();
				} catch (IOException e) {
					logger.error(e);
				}
			}
		}
		logger.debug(sb.toString());
		return sb.toString();
	}
	
	/**
	 * @param request
	 * @return
	 */
    public static JSONObject getRequestParams(HttpServletRequest request){
    	JSONObject params = new JSONObject();
    	if(null != request){
            Set<String> paramsKey = request.getParameterMap().keySet();
            for(String key : paramsKey){
                params.put(key, request.getParameter(key));
            }
        }
        return params;
    }
    public static Map<String,String> getRequestParamsMap(HttpServletRequest request){
    	Map<String,String> params = new HashMap<String,String>();
        if(null != request){
            Set<String> paramsKey = request.getParameterMap().keySet();
            for(String key : paramsKey){
                params.put(key, request.getParameter(key));
            }
        }
        return params;
    }
    public static NameValuePair [] getNameValuePairs(HttpServletRequest request){
    	Map<String,String> map = getRequestParamsMap(request);
    	NameValuePair[] data = new NameValuePair[map.size()];
        @SuppressWarnings("rawtypes")
		Iterator it = map.entrySet().iterator();
        int i = 0;
        while (it.hasNext()) {
            @SuppressWarnings("rawtypes")
			Map.Entry entry = (Map.Entry) it.next();
            Object key = entry.getKey();
            Object value = entry.getValue();
            if(value!=null){
            	data[i] = new NameValuePair(key.toString().trim(), value.toString());
            }else{
            	data[i] = new NameValuePair(key.toString().trim(), "");
            }
            i++;
        }
    	return data;
    }
    

    public static boolean isLogin(HttpServletRequest request){
		HttpSession session = getSession(request);
		if(session == null || session.getAttribute("userId") == null){
			return false;
		}
		return true;
	}
    
    public static String getVerifyMsg(HttpServletRequest request){
 		HttpSession session = getSession(request);
 		if(session != null && session.getAttribute("verifyMsg") != null){
 			return (String) session.getAttribute("verifyMsg") ;
 		}
 		return null;
 	}

    private static HttpSession getSession(HttpServletRequest request){
		return request.getSession();
	}

    public static void logOut(HttpServletRequest request){
		HttpSession session = getSession(request);
		if(session!=null){
			session.removeAttribute("userId");
			session.removeAttribute("userName");
			session.removeAttribute("userType");
			session.removeAttribute("verifyMsg");
		}
	}
    
    public static Long getUserId(HttpServletRequest request){
		HttpSession session = getSession(request);
		if(session != null && session.getAttribute("userId") != null){
			return Long.valueOf( session.getAttribute("userId")+"");
		}
		return null;
	}
    
    public static String getUserName(HttpServletRequest request){
		HttpSession session = getSession(request);
		if(session != null && session.getAttribute("userName") != null){
			return (String) session.getAttribute("userName");
		}
		return null;
	}
    
    public static String getComName(HttpServletRequest request){
 		HttpSession session = getSession(request);
 		if(session != null && session.getAttribute("comName") != null){
 			return (String) session.getAttribute("comName");
 		}
 		return null;
 	}
    
    public static String getUserType(HttpServletRequest request){
		HttpSession session = getSession(request);
		if(session != null && session.getAttribute("userType") != null){
			return (String) session.getAttribute("userType");
		}
		return null;
	}
    
    public static String getReqIpAddr(HttpServletRequest request) {
	        String ip = request.getHeader("X-Real-IP");
	        if (StringUtils.isNotEmpty(ip) && !"unknown".equalsIgnoreCase(ip)) {
	            return ip;
	        }
	        ip = request.getHeader("X-Forwarded-For");
	        if (StringUtils.isNotEmpty(ip) && !"unknown".equalsIgnoreCase(ip)) {
	            // 多次反向代理后会有多个IP值，第一个为真实IP。
	            int index = ip.indexOf(',');
	            if (index != -1) {
	                return ip.substring(0, index);
	            } else {
	                return ip;
	            }
	        } else {
	            return request.getRemoteAddr();
	        }
	    }
}
