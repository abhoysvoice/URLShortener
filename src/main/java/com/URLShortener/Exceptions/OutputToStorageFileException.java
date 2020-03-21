package com.URLShortener.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class OutputToStorageFileException extends ResponseStatusException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public OutputToStorageFileException() {
		super(HttpStatus.CONFLICT, "Problem writing out to storage File");
	}
}
