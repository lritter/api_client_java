package com.animoto.api.resource;

import com.animoto.api.exception.HttpExpectationException;
import com.animoto.api.util.StringUtil;

import org.apache.http.HttpResponse;

import java.io.IOException;

public class Storyboard extends BaseGetOnlyResource {
	public String getAccept() {
		return "application/vnd.animoto.storyboard-v1+json";
	}

	public String getUrl() {
		return getLocation();
	}

  public void handleHttpResponse(HttpResponse httpResponse, int expectedStatusCode) throws HttpExpectationException, IOException {

		String body = validateHttpExpectations(httpResponse, expectedStatusCode);	
		System.out.println("HERE IS MY BODY " + body);
	}
}
