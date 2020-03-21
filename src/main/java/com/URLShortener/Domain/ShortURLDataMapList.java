package com.URLShortener.Domain;

import java.util.List;

public class ShortURLDataMapList {
	private List<ShortURLDataMapObject> shortURLDataMapList;
	
	public ShortURLDataMapList() {
	}
	
	public ShortURLDataMapList(List<ShortURLDataMapObject> shortURLDataMapList) {
		this.shortURLDataMapList = shortURLDataMapList;
	}
	
	public List<ShortURLDataMapObject> getShortURLDataMapList() {
		return shortURLDataMapList;
	}
	
}
