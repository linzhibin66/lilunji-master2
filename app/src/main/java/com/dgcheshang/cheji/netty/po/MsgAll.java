package com.dgcheshang.cheji.netty.po;


public class MsgAll implements java.io.Serializable{

	/**
	 * 整体消息解析
	 */
	private static final long serialVersionUID = 1L;
	private String hexString;//待解析的数据
	private Header header;//头部信息
	private Object Object;//消息体
	private String code;//码  0成功 1效验码错误2消息头解析失败3消息体长度跟头部指定长度不符4分包信息
	private String errormsg;//附加信息
	private String uuid;//通道标志



	public String getHexString() {
		return hexString;
	}
	public void setHexString(String hexString) {
		this.hexString = hexString;
	}
	public Header getHeader() {
		return header;
	}
	public void setHeader(Header header) {
		this.header = header;
	}
	public Object getObject() {
		return Object;
	}
	public void setObject(Object object) {
		Object = object;
	}

	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getErrormsg() {
		return errormsg;
	}
	public void setErrormsg(String errormsg) {
		this.errormsg = errormsg;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

}
