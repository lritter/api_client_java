package com.animoto.api.cli;

import com.animoto.api.resource.RenderingJob;

public class RawRenderingJob extends RenderingJob {
  private String rawJson;

  public RawRenderingJob(String rawJson) {
    this.rawJson = rawJson;
  }

  public String toJson() {
    return rawJson;
  }
}
