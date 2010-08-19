package com.animoto.api.enums;

public enum VerticalResolution {
  VR_360P("360p"), VR_480P("480p"), VR_720P("720p");

  private String value;

  VerticalResolution(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
