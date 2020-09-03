package com.krungsri.jaos.card.model;

public class CardModel {

	String code;
	String desc;
	String data;
	
	
	public CardModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public CardModel(String code, String desc, String data) {
		super();
		this.code = code;
		this.desc = desc;
		this.data = data;
	}


	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
}
