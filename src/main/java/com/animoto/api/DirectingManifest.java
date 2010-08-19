package com.animoto.api;

import java.util.List;
import java.util.ArrayList;

import com.animoto.api.visual.Visual;
import com.animoto.api.enums.Pacing;
import com.animoto.api.enums.Style;

public class DirectingManifest {
  private Visual[] visuals = new Visual[0];
  private String title;
  private String producerName;
  private Pacing pacing = Pacing.DEFAULT;
  private Style style = Style.ORIGINAL;
  private Song song;
 
  public void addVisual(Visual visual) {
    /*
      Unfortunately, JSON serialization isn't happy with Collections so we use a typed array :/
     */
    List list = new ArrayList();
    list.addAll(java.util.Arrays.asList(visuals));
    list.add(visual);
    visuals = (Visual[]) list.toArray(new Visual[list.size()]);
  }

  public Visual[] getVisuals() {
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

  public void setSong(Song song) {
    this.song = song;
  }

  public Song getSong() {
    return song;
  }
}
