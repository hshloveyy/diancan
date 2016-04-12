package com.zdp.family.cookbook.entity;

import java.util.List;

public class FoodVo {
	private String code;

	private List<Food> data;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public List<Food> getData() {
		return data;
	}

	public void setData(List<Food> data) {
		this.data = data;
	}

}
