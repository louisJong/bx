package com.baojing.credit.model;

public class ReqBody {
	private String keyword;
	private String type;
	private long skip;
	
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public long getSkip() {
		return skip;
	}
	public void setSkip(long skip) {
		this.skip = skip;
	}
}
