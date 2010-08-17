package com.animoto.api.job;

import com.animoto.api.Jsonable;
import com.animoto.api.manifest.DirectingManifest;

public class DirectingJob extends BaseJob implements Jsonable {
  private DirectingManifest directingManifest;

  public void setDirectingManifest(DirectingManifest directingManifest) {
    this.directingManifest = directingManifest;
  }

  public DirectingManifest getDirectingManifest() {
    return directingManifest;
  }

  public String toJson() {
    return newGson().toJson(this); 
  }
}
