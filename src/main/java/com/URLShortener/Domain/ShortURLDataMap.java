package com.URLShortener.Domain;

public class ShortURLDataMap {
	private String originalUrl;
	private String shortenedUrl;
	
	public ShortURLDataMap() {
	}
	
	public ShortURLDataMap(String originalUrl, String shortenedUrl) {
		this.originalUrl = originalUrl;
		this.shortenedUrl = shortenedUrl;
	}
	public String getOriginalUrl() {
		return originalUrl;
	}

	public String getShortenedUrl() {
		return shortenedUrl;
	}
}
