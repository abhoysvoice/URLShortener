package com.URLShortener.Domain;

public class URLRequestObject {

	private String urlToTransform;

	public URLRequestObject() {}
	
	public URLRequestObject(String urlToTransform) {
		this.urlToTransform = urlToTransform;
	}
	
	public String getUrlToTransform() {
		return urlToTransform;
	}

	public void setUrlToTransform(String urlToTransform) {
		this.urlToTransform = urlToTransform;
	}
}
