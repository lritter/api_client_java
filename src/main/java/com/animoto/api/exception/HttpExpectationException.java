package com.animoto.api.exception;

import com.animoto.api.ApiError;
import com.animoto.api.dto.Status;

public class HttpExpectationException extends ApiException {
  private int receivedCode;
  private int expectedCode;
  private String body;
  private ApiError[] apiErrors; 

  public HttpExpectationException(int receivedCode, int expectedCode, String body, Status status) {
    this.receivedCode = receivedCode;
    this.body = body;
    this.expectedCode = expectedCode;
    this.apiErrors = status.getApiErrors();
  }

  public String toString() {
    return "received [" + receivedCode + "] instead of [" + expectedCode + "] with body [" + body + "]";
  }

  public int getReceivedCode() {
    return receivedCode;
  }

  public int getExpectedCode() {
    return expectedCode;
  } 

  public String getBody() {
    return this.body;
  }

  public ApiError[] getApiErrors() {
    return apiErrors;
  }
}
