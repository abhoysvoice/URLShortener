package com.URLShortener.Statistics.Service.Impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.URLShortener.Domain.ShortURLDataMapObject;
import com.URLShortener.Domain.TotalMappedShortURL;
import com.URLShortener.Domain.TotalMappedShortURLPerDay;
import com.URLShortener.Statistics.Service.URLShortenerStatisticsService;
import com.URLShortener.Utils.ShortUrlDataMapService;

@Service
public class URLShortenerStatisticsServiceImpl implements URLShortenerStatisticsService {

	@Autowired
	Environment env;
	
	@Override
	public TotalMappedShortURL getTotalMappedUrls() {
		ShortUrlDataMapService shortUrlDataMapService = new ShortUrlDataMapService();
		
		List<ShortURLDataMapObject> existingShortURLMap = shortUrlDataMapService.getStoredDataMap(env.getProperty("urlStorageFilePath"));
		
		return new TotalMappedShortURL(existingShortURLMap.size());
	}

	@Override
	public TotalMappedShortURLPerDay getTotalMappedUrlsPerDay(String date) {
		LocalDate convertedSelectedDate = LocalDate.parse(date);
		ShortUrlDataMapService shortUrlDataMapService = new ShortUrlDataMapService();
		List<ShortURLDataMapObject> existingShortURLMap = shortUrlDataMapService.getStoredDataMap(env.getProperty("urlStorageFilePath"));
		
		long mappedURLsForGivenDay = existingShortURLMap.stream()
				.filter(mapObj -> mapObj.getCreationDate().equals(convertedSelectedDate))
				.count();
		
		return new TotalMappedShortURLPerDay(mappedURLsForGivenDay, convertedSelectedDate);
	}

}
