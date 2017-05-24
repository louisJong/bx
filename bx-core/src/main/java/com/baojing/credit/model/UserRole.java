package com.baojing.credit.model ;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import java.util.Date;
@SuppressWarnings("serial")
public class UserRole  extends BaseObject {
  /** 角色名称 */
  private  String  roleName;
  /** 描述 */
  private  String  remark;
  /** 权限 */
  private  String  permission;
  /** 创建时间 */
  private  Date  updateTime;
  /** 创建人 */
  private  String  createUser;
  /** 角色名称 */
	public String getRoleName(){
		return this.roleName;
	}
  /** 角色名称 */
	public UserRole setRoleName(String roleName){
		 this.roleName=roleName;
		 return this;
	}
  /** 描述 */
	public String getRemark(){
		return this.remark;
	}
  /** 描述 */
	public UserRole setRemark(String remark){
		 this.remark=remark;
		 return this;
	}
  /** 权限 */
	public String getPermission(){
		return this.permission;
	}
  /** 权限 */
	public UserRole setPermission(String permission){
		 this.permission=permission;
		 return this;
	}
  /** 创建时间 */
	public Date getUpdateTime(){
		return this.updateTime;
	}
  /** 创建时间 */
	public UserRole setUpdateTime(Date updateTime){
		 this.updateTime=updateTime;
		 return this;
	}
  /** 创建人 */
	public String getCreateUser(){
		return this.createUser;
	}
  /** 创建人 */
	public UserRole setCreateUser(String createUser){
		 this.createUser=createUser;
		 return this;
	}
	public String toString(){
	    return JSON.toJSONString(this,SerializerFeature.WriteDateUseDateFormat,SerializerFeature.WriteMapNullValue);
	}
}
