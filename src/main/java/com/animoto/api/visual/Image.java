package com.animoto.api.visual;

import com.animoto.api.enums.ExifOrientation;
import com.animoto.api.enums.VisualType;

public class Image extends BaseVisual {
  private Boolean spotlit;
  private ExifOrientation rotation;
  private String sourceUrl;

  public Image() {
    visualType = VisualType.IMAGE;
  }

  public void setSpotlit(Boolean spotlit) {
    this.spotlit = spotlit;
  }

  public Boolean isSpotlit() {
    return spotlit;
  }

  public void setRotation(ExifOrientation rotation) {
    this.rotation = rotation;
  }

  public ExifOrientation getRotation() {
    return rotation;
  }

  public void setSourceUrl(String sourceUrl) {
    this.sourceUrl = sourceUrl;
  }

  public String getSourceUrl() {
    return sourceUrl;
  }
}
