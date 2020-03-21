package com.URLShortener.Utils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import com.URLShortener.Domain.ShortURLDataMapList;
import com.URLShortener.Domain.ShortURLDataMapObject;
import com.URLShortener.Exceptions.ReadStorageFileException;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

public class ShortUrlDataMapService {
	
	public List<ShortURLDataMapObject> getStoredDataMap(String fileStoragePath) throws ReadStorageFileException {
		ShortURLDataMapList storageFileData;
		Gson gson = new Gson();
		
		try {
			storageFileData = gson.fromJson(new FileReader(fileStoragePath), ShortURLDataMapList.class);
		} catch (FileNotFoundException e) {
			return new ArrayList<ShortURLDataMapObject>();
		} catch (JsonSyntaxException | JsonIOException e) {
			throw new ReadStorageFileException();
		}
		
		if(storageFileData!=null) {
			return storageFileData.getShortURLDataMapList();
		}
		return new ArrayList<ShortURLDataMapObject>();
	}
}
