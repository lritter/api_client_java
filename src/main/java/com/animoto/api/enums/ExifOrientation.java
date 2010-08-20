package com.animoto.api.enums;

/**
 * This enumeration represents the possible orientation values for your Image visual.<p/>
 *
 * For more information, <a href="http://www.impulseadventure.com/photo/exif-orientation.html">http://www.impulseadventure.com/photo/exif-orientation.html</a><p/>
 *
 * @see com.animoto.api.visual.Image
 */
public enum ExifOrientation {
  ONE(1), TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7), EIGHT(8);

  private int value;

  ExifOrientation(int value) {
    this.value = value;
  }

  public int getValue() {
    return value;
  }
}
