package com.animoto.api.dto;

import java.util.Map;

public class DirectingJob {
  private String state;
  private Map<String, String> links;

  public String getState() {
    return state;
  }

  public Map<String, String> getLinks() {
    return links;
  }
}
