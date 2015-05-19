package com.example.desidimeassignment.apihandler;

public interface UpdateViewsListener {
	public void updateViews(int requestType, String responseJsonString,
			boolean isErrorInResponse, String errorString);
}
