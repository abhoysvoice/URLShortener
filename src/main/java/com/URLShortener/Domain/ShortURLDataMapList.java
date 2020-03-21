package com.URLShortener.Domain;

import java.util.List;

public class ShortURLDataMapList {
	private List<ShortURLDataMap> shortURLDataMapList;
	
	public ShortURLDataMapList() {
	}
	
	public ShortURLDataMapList(List<ShortURLDataMap> shortURLDataMapList) {
		this.shortURLDataMapList = shortURLDataMapList;
	}
	
	public List<ShortURLDataMap> getShortURLDataMapList() {
		return shortURLDataMapList;
	}
	
}
