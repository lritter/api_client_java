package com.animoto.api.enums;

public enum VerticalResolution {
  V_360P("360p"), V_480P("480p"), V_720P("720p");

  private String value;

  VerticalResolution(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
