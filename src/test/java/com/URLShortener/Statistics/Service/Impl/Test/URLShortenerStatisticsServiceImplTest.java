package com.URLShortener.Statistics.Service.Impl.Test;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.URLShortener.Domain.ShortURLDataMapList;
import com.URLShortener.Domain.ShortURLDataMapObject;
import com.URLShortener.Domain.TotalMappedShortURL;
import com.URLShortener.Domain.TotalMappedShortURLPerDay;
import com.URLShortener.Exceptions.OutputToStorageFileException;
import com.URLShortener.Statistics.Service.URLShortenerStatisticsService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class URLShortenerStatisticsServiceImplTest {
	
	@Autowired
	private URLShortenerStatisticsService urlShortenerStatisticsService;
	
	@Autowired
	private Environment env;
	
	String testOriginalUrl = "www.davidsTestWebsite.com/help/HowDoITestThisBit";
	String testShortenedUrl = "http://dmw.io/MgnMMs7";	
	
	private ShortURLDataMapList createTestObjectForFile() {
		ShortURLDataMapObject shortURLDataMapObject = new ShortURLDataMapObject(testOriginalUrl, testShortenedUrl, LocalDate.parse("2020-02-20"));
		List<ShortURLDataMapObject> existingUrlMap = new ArrayList<ShortURLDataMapObject>();
		existingUrlMap.add(shortURLDataMapObject);
		
		return new ShortURLDataMapList(existingUrlMap);
	}
	
	@Before
	public void createTestFile() {
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
	
	@After
	public void deleteMap() {
		String fileLocation = env.getProperty("urlStorageFilePath");
		File file = new File(fileLocation);
		file.delete();
	}
	
	@Test
	public void getTotalMappedUrlsTest() {
		TotalMappedShortURL totalMappedUrls = urlShortenerStatisticsService.getTotalMappedUrls();
		assertTrue(totalMappedUrls.getTotalMappedUrls()==1);
	
	}
	
	@Test
	public void getTotalMappedUrlsPerDayReturnOneTest() {
		String date = "2020-02-20";
		TotalMappedShortURLPerDay totalMappedUrlPerGivenDate = urlShortenerStatisticsService.getTotalMappedUrlsPerDay(date);
		assertTrue(totalMappedUrlPerGivenDate.getTotalMappedUrls()==1);
		assertTrue(totalMappedUrlPerGivenDate.getDate().equals(LocalDate.parse(date)));
	
	}
	
	@Test
	public void getTotalMappedUrlsPerDayReturnZeroTest() {
		String date = "2000-02-20";
		TotalMappedShortURLPerDay totalMappedUrlPerGivenDate = urlShortenerStatisticsService.getTotalMappedUrlsPerDay(date);
		assertTrue(totalMappedUrlPerGivenDate.getTotalMappedUrls()==0);
		assertTrue(totalMappedUrlPerGivenDate.getDate().equals(LocalDate.parse(date)));
	
	}
}
