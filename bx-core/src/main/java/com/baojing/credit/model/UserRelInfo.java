package com.baojing.credit.model ;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import java.util.Date;
@SuppressWarnings("serial")
public class UserRelInfo  extends BaseObject {
  /** 主账户id */
  private  Long  mainUserId;
  /** 主账户名 */
  private  String  mainUserName;
  /** 子账户id */
  private  Long  subUserId;
  /** 子账户名 */
  private  String  subUserName;
  /** 创建人 */
  private  String  createUser;
  /** 更新时间 */
  private  Date  updateTime;
  /** 主账户名 */
	public String getMainUserName(){
		return this.mainUserName;
	}
  /** 主账户名 */
	public UserRelInfo setMainUserName(String mainUserName){
		 this.mainUserName=mainUserName;
		 return this;
	}
  /** 子账户id */
	public Long getSubUserId(){
		return this.subUserId;
	}
  /** 子账户id */
	public UserRelInfo setSubUserId(Long subUserId){
		 this.subUserId=subUserId;
		 return this;
	}
  /** 子账户名 */
	public String getSubUserName(){
		return this.subUserName;
	}
  /** 子账户名 */
	public UserRelInfo setSubUserName(String subUserName){
		 this.subUserName=subUserName;
		 return this;
	}
  /** 创建人 */
	public String getCreateUser(){
		return this.createUser;
	}
  /** 创建人 */
	public UserRelInfo setCreateUser(String createUser){
		 this.createUser=createUser;
		 return this;
	}
  /** 更新时间 */
	public Date getUpdateTime(){
		return this.updateTime;
	}
  /** 更新时间 */
	public UserRelInfo setUpdateTime(Date updateTime){
		 this.updateTime=updateTime;
		 return this;
	}
	public String toString(){
	    return JSON.toJSONString(this,SerializerFeature.WriteDateUseDateFormat,SerializerFeature.WriteMapNullValue);
	}
	public Long getMainUserId() {
		return mainUserId;
	}
	public void setMainUserId(Long mainUserId) {
		this.mainUserId = mainUserId;
	}
}
