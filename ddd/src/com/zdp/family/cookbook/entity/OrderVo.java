package com.zdp.family.cookbook.entity;

import java.util.List;

public class OrderVo {
	
	private String code;
	private List<Order> data;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public List<Order> getData() {
		return data;
	}
	public void setData(List<Order> data) {
		this.data = data;
	}
	
	

}
