package com.URLShortener.Domain;

import java.time.LocalDate;

public class TotalMappedShortURLPerDay {
	private long totalMappedUrls;
	private LocalDate date;
	
	public TotalMappedShortURLPerDay() {
	}
	
	public TotalMappedShortURLPerDay(long totalMappedUrls, LocalDate date) {
		this.totalMappedUrls = totalMappedUrls;
		this.date = date;
	}
	
	public long getTotalMappedUrls() {
		return totalMappedUrls;
	}

	public void setTotalMappedUrls(int totalMappedUrls) {
		this.totalMappedUrls = totalMappedUrls;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}
}
