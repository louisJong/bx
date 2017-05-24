package com.baojing.credit.model ;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import java.util.Date;
@SuppressWarnings("serial")
public class Logs  extends BaseObject {
  /** 用户名 */
  private  String  username;
  /** 访问的企业名 */
  private  String  companyName;
  /**  */
  private  Integer  isPdf;
  /**  */
  private  Integer  isSearch;
  /** 访问时间 */
  private  Date  visitTime;
  /**  */
  private  String  logId;
  /** 搜索页码 */
  private  Long  page;
  /** 被访问的企业表单/接口名称 */
  private  String  formName;
  /** 搜索用户所属公司 */
  private  String  userComName;
  /** 用户名 */
	public String getUsername(){
		return this.username;
	}
  /** 用户名 */
	public Logs setUsername(String username){
		 this.username=username;
		 return this;
	}
  /** 访问的企业名 */
	public String getCompanyName(){
		return this.companyName;
	}
  /** 访问的企业名 */
	public Logs setCompanyName(String companyName){
		 this.companyName=companyName;
		 return this;
	}
  /**  */
	public Integer getIsPdf(){
		return this.isPdf;
	}
  /**  */
	public Logs setIsPdf(Integer isPdf){
		 this.isPdf=isPdf;
		 return this;
	}
  /**  */
	public Integer getIsSearch(){
		return this.isSearch;
	}
  /**  */
	public Logs setIsSearch(Integer isSearch){
		 this.isSearch=isSearch;
		 return this;
	}
  /** 访问时间 */
	public Date getVisitTime(){
		return this.visitTime;
	}
  /** 访问时间 */
	public Logs setVisitTime(Date visitTime){
		 this.visitTime=visitTime;
		 return this;
	}
  /**  */
	public String getLogId(){
		return this.logId;
	}
  /**  */
	public Logs setLogId(String logId){
		 this.logId=logId;
		 return this;
	}
  /** 搜索页码 */
	public Long getPage(){
		return this.page;
	}
  /** 搜索页码 */
	public Logs setPage(Long page){
		 this.page=page;
		 return this;
	}
  /** 被访问的企业表单/接口名称 */
	public String getFormName(){
		return this.formName;
	}
  /** 被访问的企业表单/接口名称 */
	public Logs setFormName(String formName){
		 this.formName=formName;
		 return this;
	}
  /** 搜索用户所属公司 */
	public String getUserComName(){
		return this.userComName;
	}
  /** 搜索用户所属公司 */
	public Logs setUserComName(String userComName){
		 this.userComName=userComName;
		 return this;
	}
	public String toString(){
	    return JSON.toJSONString(this,SerializerFeature.WriteDateUseDateFormat,SerializerFeature.WriteMapNullValue);
	}
}
