package com.URLShortener.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ReadStorageFileException extends ResponseStatusException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ReadStorageFileException() {
		super(HttpStatus.I_AM_A_TEAPOT,"Possible Corrupted storage File");
	}
}
