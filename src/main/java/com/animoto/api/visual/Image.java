package com.animoto.api.visual;

import com.animoto.api.enums.ExifOrientation;
import com.animoto.api.enums.VisualType;

/**
 * An Image contains all information needed to place an image in an Animoto video.<p/>
 *
 * It is added to a DirectingManifest when directing.<p/>
 *
 * @see com.animoto.api.DirectingManifest
 */
public class Image extends BaseVisual {
  private Boolean spotlit;
  private ExifOrientation rotation;
  private String sourceUrl;

  public Image() {
    visualType = VisualType.IMAGE;
  }

  /**
   * Set whether the image should be spotlit in the video by the API.
   */
  public void setSpotlit(Boolean spotlit) {
    this.spotlit = spotlit;
  }

  public Boolean isSpotlit() {
    return spotlit;
  }

  /**
   * Set the EXIF Orientation for the image.
   */
  public void setRotation(ExifOrientation rotation) {
    this.rotation = rotation;
  }

  public ExifOrientation getRotation() {
    return rotation;
  }

  /**
   * Set a valid HTTP URL for where the image can be located by API.
   */
  public void setSourceUrl(String sourceUrl) {
    this.sourceUrl = sourceUrl;
  }

  public String getSourceUrl() {
    return sourceUrl;
  }
}
