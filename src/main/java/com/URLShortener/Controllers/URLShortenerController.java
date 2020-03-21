package com.URLShortener.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.URLShortener.Domain.ShortURLDataMap;
import com.URLShortener.Domain.URLRequestObject;
import com.URLShortener.Exceptions.OutputToStorageFileException;
import com.URLShortener.Exceptions.ReadStorageFileException;
import com.URLShortener.Exceptions.UrlNotFoundInRepositoryException;
import com.URLShortener.Service.URLShortenerService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "URLShortenerController")
@RestController
@RequestMapping("/shortifier")
public class URLShortenerController {
	
	@Autowired
	private URLShortenerService URLShortenerService;
	
	@ApiOperation(value = "Get Shortened URL by sending in a long URL", response = ShortURLDataMap.class)
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "Success | OK"),
		@ApiResponse(code = 418, message = "Error reading Url data from storage file"),
		@ApiResponse(code = 409, message = "Error writing Url data to storage file")})
	@RequestMapping(value = "/shortenURL", method = RequestMethod.POST)
	public ShortURLDataMap getShortenedURL(@RequestBody URLRequestObject originalUrlRequest) throws ReadStorageFileException, OutputToStorageFileException {
		return URLShortenerService.getShortenedUrl(originalUrlRequest.getUrlToTransform());
	}
	
	@ApiOperation(value = "Get Original long URL, by sending in previously shortened URL", response = ShortURLDataMap.class)
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "Success | OK"),
		@ApiResponse(code = 404, message = "No Entry found for your shortened URL"),
		@ApiResponse(code = 409, message = "Error Reading Url data storage file")})
	@RequestMapping(value = "/revertURL",  method = RequestMethod.POST)
	@ExceptionHandler({ ReadStorageFileException.class, UrlNotFoundInRepositoryException.class })
	public ShortURLDataMap getOriginalLongURL(@RequestBody URLRequestObject shortenedUrlRequest) throws ReadStorageFileException, ResponseStatusException {
		return URLShortenerService.getOriginalUrl(shortenedUrlRequest.getUrlToTransform());
	}
}
