package com.animoto.api.job;

import com.animoto.api.Jsonable;
import com.animoto.api.manifest.DirectingManifest;

public class DirectingJob extends BaseJob implements Jsonable {
  private DirectingManifest directingManifest;
  private String storyboard;

  public void setDirectingManifest(DirectingManifest directingManifest) {
    this.directingManifest = directingManifest;
  }

  public DirectingManifest getDirectingManifest() {
    return directingManifest;
  }

  public void setStoryboard(String storyboard) {
    this.storyboard = storyboard;
  }

  public String getStoryboard() {
    return storyboard;
  }

  public String toJson() {
    return newGson().toJson(new Container(this));
  }

  /**
   * Allows for a Gson to reflect the outer class context into JSON.
   */
  private class Container {
    private DirectingJob directingJob;

    public Container(DirectingJob directingJob) {
      this.directingJob = directingJob;  
    }
  }
}
