package com.animoto.api.manifest;

import java.util.List;
import java.util.ArrayList;

import com.animoto.api.visual.Visual;
import com.animoto.api.enums.Pacing;
import com.animoto.api.enums.Style;

public class DirectingManifest {
  List<Visual> visuals = new ArrayList<Visual>();
  String title;
  String producerName;
  Pacing pacing = Pacing.DEFAULT;
  Style style = Style.ORIGINAL;
 
  public void addVisual(Visual visual) {
    visuals.add(visual);
  }

  public List<Visual> getVisuals() {
    return visuals;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getTitle() {
    return title;
  }

  public void setProducerName(String producerName) {
    this.producerName = producerName;
  }

  public String getProducerName() {
    return producerName;
  }

  public void setPacing(Pacing pacing) {
    this.pacing = pacing;
  }

  public Pacing getPacing() {
    return pacing;
  }

  public void setStyle(Style style) {
    this.style = style;
  }

  public Style getStyle() {
    return style;
  }
}
