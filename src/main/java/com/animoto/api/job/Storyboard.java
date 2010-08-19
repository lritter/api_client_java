package com.animoto.api.gettable;

import com.animoto.api.exception.HttpExpectationException;

import org.apache.http.HttpResponse;

import java.io.IOException;

public class Storyboard extends BaseGettable {
	public String getAccept() {
		return "application/vnd.animoto.storyboard-v1+json";
	}

	public String getUrl() {
		return getLocation();
	}

  public void handleHttpResponse(HttpResponse httpResponse, int expectedStatusCode) throws HttpExpectationException, IOException {
    int statusCode;
    String body;
	}
}
