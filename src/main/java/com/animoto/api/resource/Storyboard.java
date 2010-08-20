package com.animoto.api.resource;

public class Storyboard extends BaseHttpGetOnlyResource {
  public String getAccept() {
    return "application/vnd.animoto.storyboard-v1+json";
  }

  public String getUrl() {
    return getLocation();
  }
}
