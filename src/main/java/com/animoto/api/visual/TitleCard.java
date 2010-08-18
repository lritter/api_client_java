package com.animoto.api.visual;

import com.animoto.api.enums.VisualType;

public class TitleCard extends BaseVisual {
  private Boolean spotlit;
  private String h1;
  private String h2;

  public TitleCard() {
    visualType = VisualType.TITLE_CARD;
  }

  public void setSpotlit(Boolean spotlit) {
    this.spotlit = spotlit;
  }

  public Boolean getSpotlit() {
    return spotlit;
  }

  public void setH1(String h1) {
    this.h1 = h1;
  }

  public String getH1() {
    return h1;
  }

  public void setH2(String h2) {
    this.h2 = h2;
  }

  public String getH2() {
    return h2;
  }
}
