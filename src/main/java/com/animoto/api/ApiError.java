package com.animoto.api;

public class ApiError {
  private String code;
  private String message;

  public String getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }

  public String toString() {
    return code + ": " + message;
  }
}
