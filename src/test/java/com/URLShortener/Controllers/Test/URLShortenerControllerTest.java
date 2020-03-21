package com.URLShortener.Controllers.Test;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

import com.URLShortener.Controllers.URLShortenerController;
import com.URLShortener.Domain.ShortURLDataMap;
import com.URLShortener.Domain.URLRequestObject;
import com.URLShortener.Service.URLShortenerService;
import com.google.gson.Gson;

@WebMvcTest(URLShortenerController.class)
public class URLShortenerControllerTest {
	
	@Autowired
	private Environment env;
	
	@Mock
	URLShortenerService urlShortenerServiceMock;
	
	@InjectMocks
	URLShortenerController urlShortenerController;
	
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
        mvc = MockMvcBuilders.standaloneSetup(urlShortenerController).build();
    }
    
    @Test
    public void testControllerMappingShortenUrl() throws Exception {
    	ShortURLDataMap response = new ShortURLDataMap();
		when(urlShortenerServiceMock.getShortenedUrl(originalUrl)).thenReturn(response);
		 this.mvc.perform(post("/shortifier/shortenURL")
	                .content(createRequestObject())
	                .contentType(MediaType.APPLICATION_JSON)
	                .accept(MediaType.APPLICATION_JSON))
	                .andExpect(status().isOk());
    }
    
    @Test
    public void testControllerMappingRevertUrl() throws Exception {
    	ShortURLDataMap response = new ShortURLDataMap();
		when(urlShortenerServiceMock.getShortenedUrl(originalUrl)).thenReturn(response);
		 this.mvc.perform(post("/shortifier/revertURL")
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
