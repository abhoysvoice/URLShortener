package com.URLShortener.Domain;

public class TotalMappedShortURL {
	
	private int totalMappedUrls;

	public TotalMappedShortURL() {
	}
	
	public TotalMappedShortURL(int totalMappedUrls) {
		this.totalMappedUrls = totalMappedUrls;
	}
	
	public int getTotalMappedUrls() {
		return totalMappedUrls;
	}

	public void setTotalMappedUrls(int totalMappedUrls) {
		this.totalMappedUrls = totalMappedUrls;
	}
}
