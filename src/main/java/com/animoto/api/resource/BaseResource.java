package com.animoto.api.resource;

import com.animoto.api.enums.HttpCallbackFormat;
import com.animoto.api.util.GsonUtil;
import com.animoto.api.util.StringUtil;
import com.animoto.api.dto.ApiResponse;
import com.animoto.api.exception.ApiException;
import com.animoto.api.exception.HttpExpectationException;
import com.animoto.api.error.ContractError;

import org.apache.http.HttpResponse;

import com.google.gson.Gson;

import java.util.Map;
import java.util.HashMap;

import java.io.IOException;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;

import com.google.gson.annotations.SerializedName;

public abstract class BaseResource implements Resource {
  protected String httpCallback;
  protected HttpCallbackFormat httpCallbackFormat = HttpCallbackFormat.XML;
  protected String state;
	protected String requestId;
	protected Map<String, String> links = new HashMap<String, String>();
	protected Map<String, String> metadata = new HashMap<String, String>();

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

  public String getLocation() {
   	return links.get("self");
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

	public void setLinks(Map<String, String> links) {
		this.links = links;
	}
	
	public Map<String, String> getLinks() {
		return links;
	}

	public void setMetadata(Map<String, String> metadata) {
		this.metadata = metadata;
	}

	public Map<String, String> getMetadata() {
		return metadata;
	}

  protected Gson newGson() {
    return GsonUtil.create();
  }

	public void handleHttpResponse(HttpResponse httpResponse, int expectedStatusCode) throws HttpExpectationException, IOException {
    int statusCode;
    String body;
		ApiResponse apiResponse;
		BaseResource dtoBaseResource;

    statusCode = httpResponse.getStatusLine().getStatusCode();
    body = StringUtil.convertStreamToString(httpResponse.getEntity().getContent());
		apiResponse = newGson().fromJson(body, ApiResponse.class);
    if (statusCode != expectedStatusCode) {
      throw new HttpExpectationException(statusCode, expectedStatusCode, body, apiResponse.getResponse().getStatus());
    }

		apiResponse.getResponse();
		apiResponse.getResponse().getPayload();
		apiResponse.getResponse().getPayload().getBaseResource(this.getClass());
    dtoBaseResource = apiResponse.getResponse().getPayload().getBaseResource(this.getClass());
    doErrorableBeanCopy(dtoBaseResource);
    setRequestId(httpResponse.getFirstHeader("x-animoto-request-id").getValue());

    if (getLocation() == null) {
      throw new ContractError();
    }
	}

	protected void doErrorableBeanCopy(Object bean) {
    try {
      BeanUtils.copyProperties(this, bean);
    }
    catch (IllegalAccessException e) {
      throw new Error();
    }
    catch (IllegalArgumentException e) {
      throw new Error();
    }
    catch (InvocationTargetException e) {
      throw new Error();
    }
	}
}
