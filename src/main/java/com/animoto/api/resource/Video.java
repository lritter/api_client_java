package com.animoto.api.resource;

public class Video extends BaseHttpGetOnlyResource {
  public String getAccept() {
    return "application/vnd.animoto.video-v1+json";
  }
}
