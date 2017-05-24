package com.baojing.credit.model ;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import java.util.Date;
@SuppressWarnings("serial")
public class AdminUser  extends BaseObject {
  /** 账户名 */
  private  String  userName;
  /** 账户类型MAIN企业主账户SUB子账户ADMIN管理员 */
  private  String  userType;
  /** 电话 */
  private  String  telephone;
  /** 公司名称 */
  private  String  comName;
  /** 传真 */
  private  String  fax;
  /** 公司地区 */
  private  String  comRegion;
  /** 公司地址 */
  private  String  comAddress;
  /** 邮箱地址 */
  private  String  email;
  /** 登陆密码 */
  private  String  loginPwd;
  /** 授权开始日期 */
  private  Date  authStartTime;
  /** 授权结束日期 */
  private  Date  authEndTime;
  /** 创建人 */
  private  String  createUser;
  /** 密码错误次数 */
  private  Long  errorTimes;
  /** 上次密码错误时间 */
  private  Date  lastPwdErrorTime;
  /** 上次密码修改时间 */
  private  Date  lastPwdModifyTime;
  /** 更新时间 */
  private  Date  updateTime;
  /** 状态WORK正常LOCK锁定INVID不可用 */
  private  String  status;
  /** 预留信息 **/
  private String verifyMsg;
  /** 所属角色 */
  private  Long  roleId;
  /** 账户名 */
	public String getUserName(){
		return this.userName;
	}
  /** 账户名 */
	public AdminUser setUserName(String userName){
		 this.userName=userName;
		 return this;
	}
  /** 账户类型MAIN企业主账户SUB子账户ADMIN管理员 */
	public String getUserType(){
		return this.userType;
	}
  /** 账户类型MAIN企业主账户SUB子账户ADMIN管理员 */
	public AdminUser setUserType(String userType){
		 this.userType=userType;
		 return this;
	}
  /** 电话 */
	public String getTelephone(){
		return this.telephone;
	}
  /** 电话 */
	public AdminUser setTelephone(String telephone){
		 this.telephone=telephone;
		 return this;
	}
  /** 公司名称 */
	public String getComName(){
		return this.comName;
	}
  /** 公司名称 */
	public AdminUser setComName(String comName){
		 this.comName=comName;
		 return this;
	}
  /** 传真 */
	public String getFax(){
		return this.fax;
	}
  /** 传真 */
	public AdminUser setFax(String fax){
		 this.fax=fax;
		 return this;
	}
  /** 公司地区 */
	public String getComRegion(){
		return this.comRegion;
	}
  /** 公司地区 */
	public AdminUser setComRegion(String comRegion){
		 this.comRegion=comRegion;
		 return this;
	}
  /** 公司地址 */
	public String getComAddress(){
		return this.comAddress;
	}
  /** 公司地址 */
	public AdminUser setComAddress(String comAddress){
		 this.comAddress=comAddress;
		 return this;
	}
  /** 邮箱地址 */
	public String getEmail(){
		return this.email;
	}
  /** 邮箱地址 */
	public AdminUser setEmail(String email){
		 this.email=email;
		 return this;
	}
  /** 登陆密码 */
	public String getLoginPwd(){
		return this.loginPwd;
	}
  /** 登陆密码 */
	public AdminUser setLoginPwd(String loginPwd){
		 this.loginPwd=loginPwd;
		 return this;
	}
  /** 授权开始日期 */
	public Date getAuthStartTime(){
		return this.authStartTime;
	}
  /** 授权开始日期 */
	public AdminUser setAuthStartTime(Date authStartTime){
		 this.authStartTime=authStartTime;
		 return this;
	}
  /** 授权结束日期 */
	public Date getAuthEndTime(){
		return this.authEndTime;
	}
  /** 授权结束日期 */
	public AdminUser setAuthEndTime(Date authEndTime){
		 this.authEndTime=authEndTime;
		 return this;
	}
  /** 创建人 */
	public String getCreateUser(){
		return this.createUser;
	}
  /** 创建人 */
	public AdminUser setCreateUser(String createUser){
		 this.createUser=createUser;
		 return this;
	}
  /** 密码错误次数 */
	public Long getErrorTimes(){
		return this.errorTimes;
	}
  /** 密码错误次数 */
	public AdminUser setErrorTimes(Long errorTimes){
		 this.errorTimes=errorTimes;
		 return this;
	}
  /** 上次密码错误时间 */
	public Date getLastPwdErrorTime(){
		return this.lastPwdErrorTime;
	}
  /** 上次密码错误时间 */
	public AdminUser setLastPwdErrorTime(Date lastPwdErrorTime){
		 this.lastPwdErrorTime=lastPwdErrorTime;
		 return this;
	}
  /** 上次密码修改时间 */
	public Date getLastPwdModifyTime(){
		return this.lastPwdModifyTime;
	}
  /** 上次密码修改时间 */
	public AdminUser setLastPwdModifyTime(Date lastPwdModifyTime){
		 this.lastPwdModifyTime=lastPwdModifyTime;
		 return this;
	}
  /** 更新时间 */
	public Date getUpdateTime(){
		return this.updateTime;
	}
  /** 更新时间 */
	public AdminUser setUpdateTime(Date updateTime){
		 this.updateTime=updateTime;
		 return this;
	}
  /** 状态WORK正常LOCK锁定INVID不可用 */
	public String getStatus(){
		return this.status;
	}
  /** 状态WORK正常LOCK锁定INVID不可用 */
	public AdminUser setStatus(String status){
		 this.status=status;
		 return this;
	}
	public String toString(){
	    return JSON.toJSONString(this,SerializerFeature.WriteDateUseDateFormat,SerializerFeature.WriteMapNullValue);
	}
	public String getVerifyMsg() {
		return verifyMsg;
	}
	public void setVerifyMsg(String verifyMsg) {
		this.verifyMsg = verifyMsg;
	}
	public Long getRoleId() {
		return roleId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
}
