package com.URLShortener.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.URLShortener.Domain.TotalMappedShortURL;
import com.URLShortener.Domain.TotalMappedShortURLPerDay;
import com.URLShortener.Exceptions.OutputToStorageFileException;
import com.URLShortener.Exceptions.ReadStorageFileException;
import com.URLShortener.Statistics.Service.URLShortenerStatisticsService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "StatisticsController")
@RestController
@RequestMapping("/stats")
public class StatisticsController {
	
	@Autowired
	private URLShortenerStatisticsService urlShortenerStatisticsService;

	@ApiOperation(value = "Get Total Amount of short URL entries", response = TotalMappedShortURL.class)
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "Success | OK"),
		@ApiResponse(code = 418, message = "Error reading Url data from storage file"),
		@ApiResponse(code = 409, message = "Error writing Url data to storage file")})
	@RequestMapping(value = "/HowManyShortURLs", method = RequestMethod.GET)
	public TotalMappedShortURL getAmountofMappedURLs() throws ReadStorageFileException, OutputToStorageFileException {
		return urlShortenerStatisticsService.getTotalMappedUrls();
	}
	
	@ApiOperation(value = "Get Total Amount of short URL entries, per day. Add date in format YYY-MM-DD", response = TotalMappedShortURLPerDay.class)
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "Success | OK"),
		@ApiResponse(code = 418, message = "Error reading Url data from storage file"),
		@ApiResponse(code = 409, message = "Error writing Url data to storage file")}) 
	@RequestMapping(value = "/HowManyShortURLsPerDay/{date}", method = RequestMethod.GET)
	public TotalMappedShortURLPerDay getAmountofMappedURLsPerDay(@PathVariable String date) throws ReadStorageFileException, OutputToStorageFileException {
		return urlShortenerStatisticsService.getTotalMappedUrlsPerDay(date);
	}
}
