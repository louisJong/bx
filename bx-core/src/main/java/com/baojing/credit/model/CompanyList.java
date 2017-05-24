package com.baojing.credit.model ;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
@SuppressWarnings("serial")
public class CompanyList  extends BaseObject {
  /** 公司名称 */
  private  String  name;
  /** 法定代表人 */
  private  String  operName;
  /** 注册地址 */
  private  String  address;
  /** 注册资本 */
  private  String  registCapi;
  /** 成立日期 */
  private  String  startDate;
  /** 经营状态 */
  private  Integer  status;
  /** 省份 */
  private  String  province;
  /** 公司名称 */
	public String getName(){
		return this.name;
	}
  /** 公司名称 */
	public CompanyList setName(String name){
		 this.name=name;
		 return this;
	}
  /** 法定代表人 */
	public String getOperName(){
		return this.operName;
	}
  /** 法定代表人 */
	public CompanyList setOperName(String operName){
		 this.operName=operName;
		 return this;
	}
  /** 注册地址 */
	public String getAddress(){
		return this.address;
	}
  /** 注册地址 */
	public CompanyList setAddress(String address){
		 this.address=address;
		 return this;
	}
  /** 注册资本 */
	public String getRegistCapi(){
		return this.registCapi;
	}
  /** 注册资本 */
	public CompanyList setRegistCapi(String registCapi){
		 this.registCapi=registCapi;
		 return this;
	}
  /** 成立日期 */
	public String getStartDate(){
		return this.startDate;
	}
  /** 成立日期 */
	public CompanyList setStartDate(String startDate){
		 this.startDate=startDate;
		 return this;
	}
  /** 经营状态 */
	public Integer getStatus(){
		return this.status;
	}
  /** 经营状态 */
	public CompanyList setStatus(Integer status){
		 this.status=status;
		 return this;
	}
  /** 省份 */
	public String getProvince(){
		return this.province;
	}
  /** 省份 */
	public CompanyList setProvince(String province){
		 this.province=province;
		 return this;
	}
	public String toString(){
	    return JSON.toJSONString(this,SerializerFeature.WriteDateUseDateFormat,SerializerFeature.WriteMapNullValue);
	}
}
