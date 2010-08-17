package com.animoto.api.visual;

import com.animoto.api.enums.VisualType;

public abstract class BaseVisual implements Visual {

  protected VisualType visualType = null;

  public String getType() {
    if (visualType == null)
      return null;
    else
      return visualType.getType();
  }
}
