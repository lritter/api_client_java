package com.animoto.api.util;

import com.animoto.api.RenderingManifest;
import com.animoto.api.RenderingProfile;
import com.animoto.api.enums.*;

public class RenderingManifestFactory {
  public static RenderingManifest newInstance() {
    RenderingManifest renderingManifest = new RenderingManifest();
    RenderingProfile renderingProfile = new RenderingProfile();

    renderingProfile.setFramerate(new Float(30));
    renderingProfile.setFormat(Format.H264);
    renderingProfile.setVerticalResolution(VerticalResolution.VR_720P);

    renderingManifest.setRenderingProfile(renderingProfile);
    renderingManifest.setStoryboardUrl("http://animoto.com/storyboard/123");
    return renderingManifest;
  }
}
