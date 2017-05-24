package com.baojing.credit.model;


public class Credit<E> {
	private Header header;

	private E body;

	private String sign;

	public Header getHeader() {
		return header;
	}

	public void setHeader(Header header) {
		this.header = header;
	}

	public E getBody() {
		return body;
	}

	public void setBody(E body) {
		this.body = body;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}
	
}
