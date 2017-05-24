package com.baojing.credit.model ;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
@SuppressWarnings("serial")
public class DataAccessLog  extends BaseObject {
  /** 访问者userid */
  private  Long  userId;
  /** 访问者用户名 */
  private  String  userName;
  /** 访问者用户类型MAIN企业主账户SUB子账户ADMIN管理员 */
  private  String  userType;
  /** 用户所属公司名称 */
  private  String  userComName;
  /** 被查询公司名称 */
  private  String  searchComName;
  /** 被访问的企业表单/接口名称 */
  private  String  dataFormName;
  /** 本次查看的页码 */
  private  Long  pageIndex;
  /** 访问异常信息 */
  private  String  exceptionMsg;
  /** 访问结果 */
  private  String  dataStatus;
  /** 返回描述 */
  private  String  retMsg;
  /** 额外参数 */
  private  String  seachExtraParam;
  /** 访问者userid */
	public Long getUserId(){
		return this.userId;
	}
  /** 访问者userid */
	public DataAccessLog setUserId(Long userId){
		 this.userId=userId;
		 return this;
	}
  /** 访问者用户名 */
	public String getUserName(){
		return this.userName;
	}
  /** 访问者用户名 */
	public DataAccessLog setUserName(String userName){
		 this.userName=userName;
		 return this;
	}
  /** 访问者用户类型MAIN企业主账户SUB子账户ADMIN管理员 */
	public String getUserType(){
		return this.userType;
	}
  /** 访问者用户类型MAIN企业主账户SUB子账户ADMIN管理员 */
	public DataAccessLog setUserType(String userType){
		 this.userType=userType;
		 return this;
	}
  /** 用户所属公司名称 */
	public String getUserComName(){
		return this.userComName;
	}
  /** 用户所属公司名称 */
	public DataAccessLog setUserComName(String userComName){
		 this.userComName=userComName;
		 return this;
	}
  /** 被查询公司名称 */
	public String getSearchComName(){
		return this.searchComName;
	}
  /** 被查询公司名称 */
	public DataAccessLog setSearchComName(String searchComName){
		 this.searchComName=searchComName;
		 return this;
	}
  /** 被访问的企业表单/接口名称 */
	public String getDataFormName(){
		return this.dataFormName;
	}
  /** 被访问的企业表单/接口名称 */
	public DataAccessLog setDataFormName(String dataFormName){
		 this.dataFormName=dataFormName;
		 return this;
	}
  /** 本次查看的页码 */
	public Long getPageIndex(){
		return this.pageIndex;
	}
  /** 本次查看的页码 */
	public DataAccessLog setPageIndex(Long pageIndex){
		 this.pageIndex=pageIndex;
		 return this;
	}
  /** 访问异常信息 */
	public String getExceptionMsg(){
		return this.exceptionMsg;
	}
  /** 访问异常信息 */
	public DataAccessLog setExceptionMsg(String exceptionMsg){
		 this.exceptionMsg=exceptionMsg;
		 return this;
	}
  /** 访问结果 */
	public String getDataStatus(){
		return this.dataStatus;
	}
  /** 访问结果 */
	public DataAccessLog setDataStatus(String dataStatus){
		 this.dataStatus=dataStatus;
		 return this;
	}
  /** 返回描述 */
	public String getRetMsg(){
		return this.retMsg;
	}
  /** 返回描述 */
	public DataAccessLog setRetMsg(String retMsg){
		 this.retMsg=retMsg;
		 return this;
	}
	public String toString(){
	    return JSON.toJSONString(this,SerializerFeature.WriteDateUseDateFormat,SerializerFeature.WriteMapNullValue);
	}
	public String getSeachExtraParam() {
		return seachExtraParam;
	}
	public void setSeachExtraParam(String seachExtraParam) {
		this.seachExtraParam = seachExtraParam;
	}
}
