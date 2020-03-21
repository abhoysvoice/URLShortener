package com.URLShortener.Controllers.Test;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.URLShortener.Controllers.StatisticsController;
import com.URLShortener.Controllers.URLShortenerController;
import com.URLShortener.Domain.TotalMappedShortURL;
import com.URLShortener.Domain.TotalMappedShortURLPerDay;
import com.URLShortener.Domain.URLRequestObject;
import com.URLShortener.Statistics.Service.URLShortenerStatisticsService;
import com.google.gson.Gson;

@WebMvcTest(URLShortenerController.class)
public class StatisticsControllerTest {
	
	@Autowired
	private Environment env;
	
	@Mock
	URLShortenerStatisticsService urlShortenerStatisticsServiceMock;
	
	@InjectMocks
	StatisticsController statisticsController;
	
	private MockMvc mvc;
	
	private String originalUrl = "davesUrl.ie/thisIsYetAnotherTest";
	
	@AfterEach
	private void deleteMap() {
		String fileLocation = env.getProperty("urlStorageFilePath");
		File file = new File(fileLocation);
		file.delete();
	}

    @Before
    public void setup() {
    	MockitoAnnotations.initMocks(this);
        mvc = MockMvcBuilders.standaloneSetup(statisticsController).build();
    }
    
    @Test
    public void testStatisticsControllerCountShortenedUrls() throws Exception {
    	TotalMappedShortURL response = new TotalMappedShortURL();
		when(urlShortenerStatisticsServiceMock.getTotalMappedUrls()).thenReturn(response);
		 this.mvc.perform(get("/stats/HowManyShortURLs")
	                .content(createRequestObject())
	                .contentType(MediaType.APPLICATION_JSON)
	                .accept(MediaType.APPLICATION_JSON))
	                .andExpect(status().isOk());
    }
    
    @Test
    public void testStatisticsControllerCountShortenedUrlsPerDate() throws Exception {
    	TotalMappedShortURLPerDay response = new TotalMappedShortURLPerDay();
		when(urlShortenerStatisticsServiceMock.getTotalMappedUrlsPerDay("2000-12-25")).thenReturn(response);
		 this.mvc.perform(get("/stats/HowManyShortURLsPerDay/2000-12-25")
	                .content(createRequestObject())
	                .contentType(MediaType.APPLICATION_JSON)
	                .accept(MediaType.APPLICATION_JSON))
	                .andExpect(status().isOk());
    }
    
    private String createRequestObject() {
    	Gson gson = new Gson();
    	return gson.toJson(new URLRequestObject(originalUrl));
    }
}
