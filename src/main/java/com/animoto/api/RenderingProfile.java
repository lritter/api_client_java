package com.animoto.api;

import com.animoto.api.enums.VerticalResolution;
import com.animoto.api.enums.Format;

/**
 * A RenderingProfile contains video metadata of how you want API to render a video.<p/>
 *
 * It is part of the RenderingManifest.<p/>
 *
 * @see RenderingManifest
 */
public class RenderingProfile {
  private VerticalResolution verticalResolution;
  private Float framerate;
  private Format format;

  public void setVerticalResolution(VerticalResolution verticalResolution) {
    this.verticalResolution = verticalResolution;
  }

  public VerticalResolution getVerticalResolution() {
    return verticalResolution;
  }

  public void setFramerate(Float framerate) {
    this.framerate = framerate;
  }

  public Float getFramerate() {
    return this.framerate;
  }

  public void setFormat(Format format) {
    this.format = format;
  }

  public Format getFormat() {
    return format;
  }
}
