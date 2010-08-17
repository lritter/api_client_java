package com.animoto.api.visual;

import com.animoto.api.enums.ExifOrientation;
import com.animoto.api.enums.VisualType;

public class Image extends BaseVisual {
  private Boolean spotlit;
  private ExifOrientation exifOrientation;
  private String sourceUrl;
  private VisualType visualType = VisualType.IMAGE;

  public void setSpotlit(Boolean spotlit) {
    this.spotlit = spotlit;
  }

  public Boolean isSpotlit() {
    return spotlit;
  }

  public void setExifOrientation(ExifOrientation exifOrientation) {
    this.exifOrientation = exifOrientation;
  }

  public ExifOrientation getExifOrientation() {
    return exifOrientation;
  }

  public void setSourceUrl(String sourceUrl) {
    this.sourceUrl = sourceUrl;
  }

  public String getSourceUrl() {
    return sourceUrl;
  }
}
