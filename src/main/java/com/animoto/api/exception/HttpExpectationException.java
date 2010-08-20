package com.animoto.api.exception;

import com.animoto.api.ApiError;
import com.animoto.api.dto.Status;

/**
 * This exception is thrown whenever a RESTFUL contract between the client and the API is broken with respect to HTTP status codes.<p/>
 *
 * For example, if an endpoint is expected to return HTTP 201 and an HTTP 404 is returned, then this exception will be thrown.<p/>
 */
public class HttpExpectationException extends ApiException {
  private int receivedCode;
  private int expectedCode;
  private String body;
  private ApiError[] apiErrors; 

  public HttpExpectationException(int receivedCode, int expectedCode, String body, Status status) {
    this.receivedCode = receivedCode;
    this.body = body;
    this.expectedCode = expectedCode;
    if (status != null) {
      this.apiErrors = status.getApiErrors();
    }
  }

  public String toString() {
    return "received [" + receivedCode + "] instead of [" + expectedCode + "] with body [" + body + "]";
  }

  /**
   * Get the actual HTTP status code from API.
   *
   * @return int
   */
  public int getReceivedCode() {
    return receivedCode;
  }

  /** 
   * Get the expected HTTP status code from API.
   *
   * @return int
   */
  public int getExpectedCode() {
    return expectedCode;
  } 
  
  /**
   * Get the original HTTP response body from API.
   *
   * @return String
   */
  public String getBody() {
    return this.body;
  }

  /**
   * Get an array of API errors that were collected from the response body, if any.
   *
   * @return ApiError[]
   */
  public ApiError[] getApiErrors() {
    return apiErrors;
  }
}
