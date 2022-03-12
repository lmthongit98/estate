package com.laptrinhjavaweb.myexception;

public class FieldRequiredException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FieldRequiredException(String errorMegssage) {
		super(errorMegssage);
	}
}
