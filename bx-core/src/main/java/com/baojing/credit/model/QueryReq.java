package com.baojing.credit.model;

public class QueryReq<E> {
	private Credit<E> credit;

	public void setCredit(Credit<E> credit){
	this.credit = credit;
	}
	public Credit<E> getCredit(){
	return this.credit;
	}
}
