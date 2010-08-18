package com.animoto.api.manifest;

public class RenderingManifest {
  private String storyboardUrl;
  private RenderingProfile renderingProfile;

  public void setStoryboardUrl(String storyboardUrl) {
    this.storyboardUrl = storyboardUrl;
  }

  public String getStoryboardUrl() {
    return storyboardUrl;
  }

  public void setRenderingProfile(RenderingProfile renderingProfile) {
    this.renderingProfile = renderingProfile;
  }

  public RenderingProfile getRenderingProfile() {
    return renderingProfile;
  }
}
