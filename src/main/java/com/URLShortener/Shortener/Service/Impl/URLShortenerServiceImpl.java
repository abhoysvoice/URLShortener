package com.URLShortener.Shortener.Service.Impl;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.URLShortener.Domain.ShortURLDataMap;
import com.URLShortener.Domain.ShortURLDataMapList;
import com.URLShortener.Domain.ShortURLDataMapObject;
import com.URLShortener.Exceptions.OutputToStorageFileException;
import com.URLShortener.Exceptions.ReadStorageFileException;
import com.URLShortener.Exceptions.UrlNotFoundInRepositoryException;
import com.URLShortener.Shortener.Service.URLShortenerService;
import com.URLShortener.Utils.ShortUrlDataMapService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;

@Service
public class URLShortenerServiceImpl implements URLShortenerService {
	
	private static final int shortUrlPreferredLength = 7;
	private static final String shortUrlAllowedNumericalValues = "1234567890"; 
	private static final String shortUrlAllowedUpperCaseValues = "ABCDEFGHIJKLMNPQRSTUVXYZ";
	private static final String shortUrlAllowedLowerCaseValues = shortUrlAllowedUpperCaseValues.toLowerCase();
	private static final String shortUrlAllowedValues = shortUrlAllowedNumericalValues + shortUrlAllowedUpperCaseValues + shortUrlAllowedLowerCaseValues;
	
	@Autowired
	private Environment env;
	
	@Override
	public synchronized ShortURLDataMap getShortenedUrl(String originalUrl) throws ReadStorageFileException, OutputToStorageFileException {
		ShortUrlDataMapService shortUrlDataMapService = new ShortUrlDataMapService();
		
		List<ShortURLDataMapObject> existingShortURLMap = shortUrlDataMapService.getStoredDataMap(env.getProperty("urlStorageFilePath")); 
		List<ShortURLDataMapObject> shortenedURLList = existingShortURLMap.stream()
		.filter(urlMapObj -> urlMapObj.getOriginalUrl().equals(originalUrl))
		.collect(Collectors.toList());
		
		if(shortenedURLList.size()>0) {
			return new ShortURLDataMap(originalUrl, shortenedURLList.get(0).getShortenedUrl());
		}
		
		String shortenedURL = createNewShortenedURL(existingShortURLMap);
		addShortenedURLToStorage(originalUrl, shortenedURL);
		return new ShortURLDataMap(originalUrl, shortenedURL);
	}

	@Override
	public ShortURLDataMap getOriginalUrl(String shortenedUrl) throws ReadStorageFileException, UrlNotFoundInRepositoryException {
		ShortUrlDataMapService shortUrlDataMapService = new ShortUrlDataMapService();
		List<ShortURLDataMapObject> storageFileData = shortUrlDataMapService.getStoredDataMap(env.getProperty("urlStorageFilePath"));
		
		Optional<ShortURLDataMapObject> originalUrl = storageFileData.stream()
				.filter(mapObj -> mapObj.getShortenedUrl().equals(shortenedUrl))
				.findFirst();
		
		if(originalUrl.isPresent())
			return new ShortURLDataMap(originalUrl.get().getOriginalUrl(), originalUrl.get().getShortenedUrl());
		
		throw new UrlNotFoundInRepositoryException();
	}
	
	private void addShortenedURLToStorage(String originalUrl, String shortenedUrl) throws ReadStorageFileException, OutputToStorageFileException {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		ShortUrlDataMapService shortUrlDataMapService = new ShortUrlDataMapService();
		List<ShortURLDataMapObject> existingUrlMap = shortUrlDataMapService.getStoredDataMap(env.getProperty("urlStorageFilePath"));
		
		existingUrlMap.add(new ShortURLDataMapObject(originalUrl, shortenedUrl, LocalDate.now()));
		ShortURLDataMapList shortURLDataMapList = new ShortURLDataMapList(existingUrlMap);
		
		String jsonFormattedData = gson.toJson(shortURLDataMapList);
		try {
			FileWriter writer = new FileWriter(env.getProperty("urlStorageFilePath"));
			writer.write(jsonFormattedData);
			writer.flush();
			writer.close();
		} catch (JsonIOException | IOException e) {
			throw new OutputToStorageFileException();
		}
	}
	
	private String createNewShortenedURL(List<ShortURLDataMapObject> existingShortURLMap) {
		StringBuilder shortUrlValue = new StringBuilder();
		String shortenedUrlResponse = "";
		boolean alreadyExists = false;
		int stringCharacterCount = 1;
		while(!alreadyExists) {
			while(stringCharacterCount<=shortUrlPreferredLength) {
				shortUrlValue.append(shortUrlAllowedValues.charAt((int) (Math.random()*shortUrlAllowedValues.length())));
				stringCharacterCount++;
			}
			String newShortenedUrl = env.getProperty("shortUrlPrefix")+shortUrlValue.toString();
		
			long existingShortUrls = existingShortURLMap.stream()
			.filter(mapObj -> mapObj.getShortenedUrl().equals(newShortenedUrl))
			.count();
			
			shortenedUrlResponse = newShortenedUrl;
			alreadyExists = existingShortUrls == 0;
		}
	
		return shortenedUrlResponse;
	}
}
