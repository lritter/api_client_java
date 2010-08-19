package com.animoto.api.dto;

import java.util.Map;

public class DirectingJob {
  protected String state;
  protected Map<String, String> links;

  public String getState() {
    return state;
  }

  public Map<String, String> getLinks() {
    return links;
  }
}
