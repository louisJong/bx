package com.baojing.credit.test;

import java.io.Serializable;

public class Entity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String sex;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	
	public String toString(){
		return "[name="+name+",sex="+sex+"]";
	}
}
