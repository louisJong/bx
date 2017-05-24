package com.baojing.credit.model;

public class QueryRsp<E> {
	private Credit<E> credit;

	public void setCredit(Credit<E> credit){
	this.credit = credit;
	}
	public Credit<E> getCredit(){
	return this.credit;
	}
}
