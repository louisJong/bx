package com.baojing.credit.model;

public class Header {
	private String streamingNo;
	private String srcSystemNo;
	private String version;
	private String rspCode;
	private String rspDesc;

	public String getStreamingNo() {
		return streamingNo;
	}

	public void setStreamingNo(String streamingNo) {
		this.streamingNo = streamingNo;
	}

	public String getSrcSystemNo() {
		return srcSystemNo;
	}

	public void setSrcSystemNo(String srcSystemNo) {
		this.srcSystemNo = srcSystemNo;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getRspCode() {
		return rspCode;
	}

	public void setRspCode(String rspCode) {
		this.rspCode = rspCode;
	}

	public String getRspDesc() {
		return rspDesc;
	}

	public void setRspDesc(String rspDesc) {
		this.rspDesc = rspDesc;
	}

}
