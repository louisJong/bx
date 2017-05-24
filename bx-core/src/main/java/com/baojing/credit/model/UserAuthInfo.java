package com.baojing.credit.model ;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import java.util.Date;
@SuppressWarnings("serial")
public class UserAuthInfo  extends BaseObject {
  /** 用户id */
  private  Long  userId;
  /** 权限code */
  private  String  authCode;
  /** 权限名称 */
  private  String  authName;
  /** 创建人 */
  private  String  createUser;
  /** 更新时间 */
  private  Date  updateTime;
  /** 用户id */
	public Long getUserId(){
		return this.userId;
	}
  /** 用户id */
	public UserAuthInfo setUserId(Long userId){
		 this.userId=userId;
		 return this;
	}
  /** 权限code */
	public String getAuthCode(){
		return this.authCode;
	}
  /** 权限code */
	public UserAuthInfo setAuthCode(String authCode){
		 this.authCode=authCode;
		 return this;
	}
  /** 权限名称 */
	public String getAuthName(){
		return this.authName;
	}
  /** 权限名称 */
	public UserAuthInfo setAuthName(String authName){
		 this.authName=authName;
		 return this;
	}
  /** 创建人 */
	public String getCreateUser(){
		return this.createUser;
	}
  /** 创建人 */
	public UserAuthInfo setCreateUser(String createUser){
		 this.createUser=createUser;
		 return this;
	}
  /** 更新时间 */
	public Date getUpdateTime(){
		return this.updateTime;
	}
  /** 更新时间 */
	public UserAuthInfo setUpdateTime(Date updateTime){
		 this.updateTime=updateTime;
		 return this;
	}
	public String toString(){
	    return JSON.toJSONString(this,SerializerFeature.WriteDateUseDateFormat,SerializerFeature.WriteMapNullValue);
	}
}
