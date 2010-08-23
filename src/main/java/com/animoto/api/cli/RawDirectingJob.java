package com.animoto.api.cli;

import com.animoto.api.resource.DirectingJob;

public class RawDirectingJob extends DirectingJob {
  private String rawJson;

  public RawDirectingJob(String rawJson) {
    this.rawJson = rawJson;
  }

  public String toJson() {
    return rawJson;
  }
}
