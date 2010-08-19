package com.animoto.api.job;

import com.animoto.api.enums.HttpCallbackFormat;
import com.animoto.api.util.GsonUtil;
import com.animoto.api.exception.ApiException;

import org.apache.http.HttpResponse;

import com.google.gson.Gson;

import java.io.IOException;

public abstract class BaseJob implements Job {
  protected String httpCallback;
  protected HttpCallbackFormat httpCallbackFormat = HttpCallbackFormat.XML;
  protected String location;
  protected String state;
	protected String requestId;

	public abstract void handleHttpResponse(HttpResponse httpResponse, int expectedStatusCode) throws ApiException, IOException;

  public void setHttpCallback(String httpCallback) {
    this.httpCallback = httpCallback;
  }

  public String getHttpCallback() {
    return httpCallback;
  }

  public void setHttpCallbackFormat(HttpCallbackFormat httpCallbackFormat) {
    this.httpCallbackFormat = httpCallbackFormat;
  }

  public HttpCallbackFormat getHttpCallbackFormat() {
    return httpCallbackFormat;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public String getLocation() {
    return location;
  }

  public void setState(String state) {
    this.state = state;
  }

  public String getState() {
    return state;
  }

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getRequestId() {
		return requestId;
	}

  public boolean isPending() {
    return !("completed".equals(state));
  }

  public boolean isComplete() {
    return !isPending();
  }

  protected Gson newGson() {
    return GsonUtil.create();
  }
}
