package com.URLShortener.Statistics.Service;

import com.URLShortener.Domain.TotalMappedShortURL;
import com.URLShortener.Domain.TotalMappedShortURLPerDay;

public interface URLShortenerStatisticsService {

	public TotalMappedShortURL getTotalMappedUrls();

	public TotalMappedShortURLPerDay getTotalMappedUrlsPerDay(String date);
}
