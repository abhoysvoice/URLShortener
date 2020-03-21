package com.URLShortener.Service.Impl.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;

import com.URLShortener.Domain.ShortURLDataMap;
import com.URLShortener.Domain.ShortURLDataMapList;
import com.URLShortener.Exceptions.OutputToStorageFileException;
import com.URLShortener.Exceptions.ReadStorageFileException;
import com.URLShortener.Exceptions.UrlNotFoundInRepositoryException;
import com.URLShortener.Service.URLShortenerService;
import com.URLShortener.Service.Impl.URLShortenerServiceImpl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;

@ActiveProfiles("test")
@SpringBootTest(classes=URLShortenerServiceImpl.class)
public class URLShortenerServiceImplTest {
	
	@Autowired
	private URLShortenerService urlShortenerService;
	
	@Autowired
	private Environment env;
	
	String testOriginalUrl = "www.davidsTestWebsite.com/help/HowDoITestThisBit";
	String testShortenedUrl = "http://dmw.io/MgnMMs7";
	
	@Test
	public void getNewShortenedUrlInNewFileSuccessTest() throws ReadStorageFileException, OutputToStorageFileException {
		ShortURLDataMap response = urlShortenerService.getShortenedUrl(testOriginalUrl);
		String shortenedUrl = response.getShortenedUrl();
		
		String[] responsesplit = shortenedUrl.split("io/");
		
		assertTrue(shortenedUrl.startsWith("http://dmw.io/"));
		assertTrue(responsesplit[1].length()==7);
		assertTrue(shortenedUrl.length()==21);
	}
	
	@Test
	public void getShortenedUrlReturnsAlreadyExistingShortUrlTest() throws ReadStorageFileException, OutputToStorageFileException {
		createTestFile();
		ShortURLDataMap response = urlShortenerService.getShortenedUrl(testOriginalUrl);
		String shortenedUrl = response.getShortenedUrl();
		String[] responsesplit = shortenedUrl.split("io/");
		
		assertTrue(shortenedUrl.startsWith("http://dmw.io/"));
		assertTrue(testShortenedUrl.equals(shortenedUrl));
		assertTrue(responsesplit[1].length()==7);
		assertTrue(shortenedUrl.length()==21);
	}
	
	@Test
	public void getExistingOriginalUrlTest() throws ReadStorageFileException, OutputToStorageFileException, UrlNotFoundInRepositoryException {
		createTestFile();
		ShortURLDataMap response = urlShortenerService.getOriginalUrl(testShortenedUrl); 
		assertTrue(response.getOriginalUrl().equals(testOriginalUrl));
	}
	
	@Test
	public void getNotExistingOriginalUrlTest() throws ReadStorageFileException, OutputToStorageFileException, UrlNotFoundInRepositoryException {
		String testShortenedUrl = "fakeUrl";
		createTestFile();
		assertThrows(UrlNotFoundInRepositoryException.class, () -> urlShortenerService.getOriginalUrl(testShortenedUrl));
	}
	
	@AfterEach
	private void deleteMap() {
		String fileLocation = env.getProperty("urlStorageFilePath");
		File file = new File(fileLocation);
		file.delete();
	}
	
	private void createTestFile() {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String fileLocation = env.getProperty("urlStorageFilePath");
		String jsonFormattedData = gson.toJson(createTestObjectForFile());
		try {
			FileWriter writer = new FileWriter(fileLocation);
			writer.write(jsonFormattedData);
			writer.flush();
			writer.close();
		} catch (JsonIOException | IOException e) {
			throw new OutputToStorageFileException();
		}
	}
	
	
	private ShortURLDataMapList createTestObjectForFile() {
		ShortURLDataMap shortURLDataMap = new ShortURLDataMap(testOriginalUrl, testShortenedUrl);
		List<ShortURLDataMap> existingUrlMap = new ArrayList<ShortURLDataMap>();
		existingUrlMap.add(shortURLDataMap);
		
		return new ShortURLDataMapList(existingUrlMap);
	}
}
