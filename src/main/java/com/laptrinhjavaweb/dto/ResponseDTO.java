package com.laptrinhjavaweb.dto;

import java.util.List;

public class ResponseDTO {
	private List<String> errors;
	private Object data;

	public List<String> getErrors() {
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}
