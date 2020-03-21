package com.URLShortener.Shortener.Service;

import com.URLShortener.Domain.ShortURLDataMap;
import com.URLShortener.Exceptions.OutputToStorageFileException;
import com.URLShortener.Exceptions.ReadStorageFileException;
import com.URLShortener.Exceptions.UrlNotFoundInRepositoryException;

public interface URLShortenerService {

	public ShortURLDataMap getShortenedUrl(String originalUrl) throws ReadStorageFileException, OutputToStorageFileException;

	public ShortURLDataMap getOriginalUrl(String shortenedUrl) throws ReadStorageFileException, UrlNotFoundInRepositoryException;
	
}
