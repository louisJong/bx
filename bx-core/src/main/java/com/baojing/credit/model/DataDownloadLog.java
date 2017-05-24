package com.baojing.credit.model ;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
@SuppressWarnings("serial")
public class DataDownloadLog  extends BaseObject {
  /** 访问者userid */
  private  Long  userId;
  /** 访问者用户名 */
  private  String  userName;
  /** 访问者用户类型MAIN企业主账户SUB子账户ADMIN管理员 */
  private  String  userType;
  /** 用户所属公司名称 */
  private  String  userComName;
  /** 被下载的公司名称 */
  private  String  downloadComName;
  /** 被访问的企业表单/接口名称 */
  private  String  downloadFormName;
  /** 报告类型H高M中L底 */
  private  String  reportType;
  /** 访问异常信息 */
  private  String  exceptionMsg;
  /** 访问结果 */
  private  String  dataStatus;
  /** 返回描述 */
  private  String  retMsg;
  /** 访问者userid */
	public Long getUserId(){
		return this.userId;
	}
  /** 访问者userid */
	public DataDownloadLog setUserId(Long userId){
		 this.userId=userId;
		 return this;
	}
  /** 访问者用户名 */
	public String getUserName(){
		return this.userName;
	}
  /** 访问者用户名 */
	public DataDownloadLog setUserName(String userName){
		 this.userName=userName;
		 return this;
	}
  /** 访问者用户类型MAIN企业主账户SUB子账户ADMIN管理员 */
	public String getUserType(){
		return this.userType;
	}
  /** 访问者用户类型MAIN企业主账户SUB子账户ADMIN管理员 */
	public DataDownloadLog setUserType(String userType){
		 this.userType=userType;
		 return this;
	}
  /** 用户所属公司名称 */
	public String getUserComName(){
		return this.userComName;
	}
  /** 用户所属公司名称 */
	public DataDownloadLog setUserComName(String userComName){
		 this.userComName=userComName;
		 return this;
	}
  /** 被下载的公司名称 */
	public String getDownloadComName(){
		return this.downloadComName;
	}
  /** 被下载的公司名称 */
	public DataDownloadLog setDownloadComName(String downloadComName){
		 this.downloadComName=downloadComName;
		 return this;
	}
  /** 被访问的企业表单/接口名称 */
	public String getDownloadFormName(){
		return this.downloadFormName;
	}
  /** 被访问的企业表单/接口名称 */
	public DataDownloadLog setDownloadFormName(String downloadFormName){
		 this.downloadFormName=downloadFormName;
		 return this;
	}
  /** 报告类型H高M中L底 */
	public String getReportType(){
		return this.reportType;
	}
  /** 报告类型H高M中L底 */
	public DataDownloadLog setReportType(String reportType){
		 this.reportType=reportType;
		 return this;
	}
  /** 访问异常信息 */
	public String getExceptionMsg(){
		return this.exceptionMsg;
	}
  /** 访问异常信息 */
	public DataDownloadLog setExceptionMsg(String exceptionMsg){
		 this.exceptionMsg=exceptionMsg;
		 return this;
	}
  /** 访问结果 */
	public String getDataStatus(){
		return this.dataStatus;
	}
  /** 访问结果 */
	public DataDownloadLog setDataStatus(String dataStatus){
		 this.dataStatus=dataStatus;
		 return this;
	}
  /** 返回描述 */
	public String getRetMsg(){
		return this.retMsg;
	}
  /** 返回描述 */
	public DataDownloadLog setRetMsg(String retMsg){
		 this.retMsg=retMsg;
		 return this;
	}
	public String toString(){
	    return JSON.toJSONString(this,SerializerFeature.WriteDateUseDateFormat,SerializerFeature.WriteMapNullValue);
	}
}
