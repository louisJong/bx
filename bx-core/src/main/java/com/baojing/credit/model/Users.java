package com.baojing.credit.model ;
import java.util.Date;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
@SuppressWarnings("serial")
public class Users  extends BaseObject {
  /** 用户名 */
  private  String  username;
  /** 密码MD5 */
  private  String  password;
  /** 角色 */
  private  String  role;
  /** 公司名称 */
  private  String  companyName;
  /** 国家地区 */
  private  String  district;
  /** 电话 */
  private  String  tel;
  /** 传真 */
  private  String  fax;
  /** 通信地址 */
  private  String  address;
  /** 授权开始日期 */
  private  Date  startDate;
  /** 授权结束日期 */
  private  Date  endDate;
  /** 更新时间 */
  private  Date  updateTime;
  /** 防钓鱼信息 */
  private  String  reserveWord;
  /** 用户名 */
	public String getUsername(){
		return this.username;
	}
  /** 用户名 */
	public Users setUsername(String username){
		 this.username=username;
		 return this;
	}
  /** 密码MD5 */
	public String getPassword(){
		return this.password;
	}
  /** 密码MD5 */
	public Users setPassword(String password){
		 this.password=password;
		 return this;
	}
  /** 角色 */
	public String getRole(){
		return this.role;
	}
  /** 角色 */
	public Users setRole(String role){
		 this.role=role;
		 return this;
	}
  /** 公司名称 */
	public String getCompanyName(){
		return this.companyName;
	}
  /** 公司名称 */
	public Users setCompanyName(String companyName){
		 this.companyName=companyName;
		 return this;
	}
  /** 国家地区 */
	public String getDistrict(){
		return this.district;
	}
  /** 国家地区 */
	public Users setDistrict(String district){
		 this.district=district;
		 return this;
	}
  /** 电话 */
	public String getTel(){
		return this.tel;
	}
  /** 电话 */
	public Users setTel(String tel){
		 this.tel=tel;
		 return this;
	}
  /** 传真 */
	public String getFax(){
		return this.fax;
	}
  /** 传真 */
	public Users setFax(String fax){
		 this.fax=fax;
		 return this;
	}
  /** 通信地址 */
	public String getAddress(){
		return this.address;
	}
  /** 通信地址 */
	public Users setAddress(String address){
		 this.address=address;
		 return this;
	}
  /** 授权开始日期 */
	public Date getStartDate(){
		return this.startDate;
	}
  /** 授权开始日期 */
	public Users setStartDate(Date startDate){
		 this.startDate=startDate;
		 return this;
	}
  /** 授权结束日期 */
	public Date getEndDate(){
		return this.endDate;
	}
  /** 授权结束日期 */
	public Users setEndDate(Date endDate){
		 this.endDate=endDate;
		 return this;
	}
  /** 更新时间 */
	public Date getUpdateTime(){
		return this.updateTime;
	}
  /** 更新时间 */
	public Users setUpdateTime(Date updateTime){
		 this.updateTime=updateTime;
		 return this;
	}
  /** 防钓鱼信息 */
	public String getReserveWord(){
		return this.reserveWord;
	}
  /** 防钓鱼信息 */
	public Users setReserveWord(String reserveWord){
		 this.reserveWord=reserveWord;
		 return this;
	}
	public String toString(){
	    return JSON.toJSONString(this,SerializerFeature.WriteDateUseDateFormat,SerializerFeature.WriteMapNullValue);
	}
}
