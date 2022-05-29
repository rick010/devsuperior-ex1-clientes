package com.vipsoftcom.vsclientes.services.exceptions;

public class ServiceEntityNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ServiceEntityNotFoundException(String msg) {
		super(msg);
	}
}
