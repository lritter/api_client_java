package com.animoto.api.exception;

public class HttpExpectationException extends ApiException {
  private int receivedCode;
  private int expectedCode;
  private String body;

  public HttpExpectationException(int receivedCode, int expectedCode, String body) {
    this.receivedCode = receivedCode;
    this.body = body;
    this.expectedCode = expectedCode;
  }

  public String toString() {
    return "received [" + receivedCode + "] instead of [" + expectedCode + "] with body [" + body + "]";
  }
}
