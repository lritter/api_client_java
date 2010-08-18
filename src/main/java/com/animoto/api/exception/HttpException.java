package com.animoto.api.exception;

public class HttpException extends Exception {
  private Exception exception;

  public HttpException(Exception exception) {
    this.exception = exception;
  }

  public String toString() {
    return exception.toString();
  }
}
