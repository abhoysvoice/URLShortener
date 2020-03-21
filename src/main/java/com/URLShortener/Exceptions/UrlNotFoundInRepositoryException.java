package com.URLShortener.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UrlNotFoundInRepositoryException extends ResponseStatusException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public UrlNotFoundInRepositoryException() {
		super(HttpStatus.NOT_FOUND, "No Entry found in repository for given shortened URL");
	}
}
