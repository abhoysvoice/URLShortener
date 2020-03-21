package com.URLShortener.Service.Impl;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.URLShortener.Domain.ShortURLDataMap;
import com.URLShortener.Domain.ShortURLDataMapList;
import com.URLShortener.Exceptions.OutputToStorageFileException;
import com.URLShortener.Exceptions.ReadStorageFileException;
import com.URLShortener.Exceptions.UrlNotFoundInRepositoryException;
import com.URLShortener.Service.URLShortenerService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

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
		List<ShortURLDataMap> existingShortURLMap = getStoredDataMap(); 
		
		List<ShortURLDataMap> shortenedURLList = existingShortURLMap.stream()
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
		List<ShortURLDataMap> storageFileData = getStoredDataMap();
		Optional<ShortURLDataMap> originalUrl = storageFileData.stream()
				.filter(mapObj -> mapObj.getShortenedUrl().equals(shortenedUrl))
				.findFirst();
		
		if(originalUrl.isPresent())
			return originalUrl.get();
		
		throw new UrlNotFoundInRepositoryException();
	}
	
	private List<ShortURLDataMap> getStoredDataMap() throws ReadStorageFileException {
		ShortURLDataMapList storageFileData;
		Gson gson = new Gson();
		
		try {
			storageFileData = gson.fromJson(new FileReader(env.getProperty("urlStorageFilePath")), ShortURLDataMapList.class);
		} catch (FileNotFoundException e) {
			return new ArrayList<ShortURLDataMap>();
		} catch (JsonSyntaxException | JsonIOException e) {
			throw new ReadStorageFileException();
		}
		
		if(storageFileData!=null) {
			return storageFileData.getShortURLDataMapList();
		}
		return new ArrayList<ShortURLDataMap>();
	}
	
	private void addShortenedURLToStorage(String originalUrl, String shortenedUrl) throws ReadStorageFileException, OutputToStorageFileException {
		List<ShortURLDataMap> existingUrlMap = getStoredDataMap();
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		existingUrlMap.add(new ShortURLDataMap(originalUrl, shortenedUrl));
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
	
	private String createNewShortenedURL(List<ShortURLDataMap> existingShortURLMap) {
		StringBuilder shortUrlValue = new StringBuilder();
		int stringCharacterCount = 1;
		
		while(stringCharacterCount<=shortUrlPreferredLength) {
			shortUrlValue.append(shortUrlAllowedValues.charAt((int) (Math.random()*shortUrlAllowedValues.length())));
			stringCharacterCount++;
		}
	
		return env.getProperty("shortUrlPrefix")+shortUrlValue.toString();
	}
}
