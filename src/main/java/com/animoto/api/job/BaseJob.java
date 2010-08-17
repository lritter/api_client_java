package com.animoto.api.job;

import com.animoto.api.enums.HttpCallbackFormat;
import com.animoto.api.enums.Style;
import com.animoto.api.gson.serializer.StyleSerializer;

import com.google.gson.GsonBuilder;
import com.google.gson.Gson;
import com.google.gson.FieldNamingPolicy;

public abstract class BaseJob implements Job {
  private String httpCallback;
  private HttpCallbackFormat httpCallbackFormat = HttpCallbackFormat.XML;

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

  protected Gson newGson() {
    GsonBuilder gsonBuilder = new GsonBuilder();
    gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
    return gsonBuilder.create();
  }
}
