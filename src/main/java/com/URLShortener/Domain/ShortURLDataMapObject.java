package com.URLShortener.Domain;

import java.time.LocalDate;

public class ShortURLDataMapObject {
	private String shortenedUrl;
	private String originalUrl;
	private LocalDate creationDate;
	
	public ShortURLDataMapObject() {
	}
	
	public ShortURLDataMapObject(String originalUrl, String shortenedUrl, LocalDate creationDate) {
		this.originalUrl = originalUrl;
		this.shortenedUrl = shortenedUrl;
		this.creationDate = creationDate;
	}
	public String getOriginalUrl() {
		return originalUrl;
	}

	public String getShortenedUrl() {
		return shortenedUrl;
	}

	public LocalDate getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDate creationDate) {
		this.creationDate = creationDate;
	}
}
