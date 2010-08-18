package com.animoto.api.job;

import com.animoto.api.Jsonable;

import com.animoto.api.manifest.RenderingManifest;

public class RenderingJob extends BaseJob implements Jsonable {
  private RenderingManifest renderingManifest;

  public void setRenderingManifest(RenderingManifest renderingManifest) {
    this.renderingManifest = renderingManifest;
  }

  public RenderingManifest getRenderingManifest() {
    return renderingManifest;
  }
  
  public String toJson() {
    return newGson().toJson(new Container(this));
  }

  /**
   * Allows for a Gson to reflect the outer class context into JSON.
   */
  private class Container {
    private RenderingJob renderingJob;

    public Container(RenderingJob renderingJob) {
      this.renderingJob = renderingJob;
    }
  }
}
