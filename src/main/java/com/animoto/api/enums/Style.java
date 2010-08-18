package com.animoto.api.enums;

public enum Style {
  ORIGINAL("original");

  private String value;

  Style(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
