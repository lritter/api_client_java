package com.animoto.api.resource;

import com.animoto.api.Jsonable;
import com.animoto.api.RenderingManifest;
import com.animoto.api.util.GsonUtil;
import com.animoto.api.exception.HttpExpectationException;

import java.io.IOException;

import org.apache.http.HttpResponse;


/**
 * A RenderingJob represents the status of your rendering job on the API.<p/>
 *
 * You will need to call ApiClient.reload() in order to obtain the latest information from API.<p/>
 *
 * When the directing job is complete, a Storyboard and Video should be available.<p/>
 *
 * @see com.animoto.api.ApiClient
 * @see Video
 * @see Storyboard
 */
public class RenderingJob extends BaseResource implements Jsonable {
  private RenderingManifest renderingManifest;
  private Video video;

  public String getContentType() {
    return "application/vnd.animoto.rendering_manifest-v1+json";
  }

  public String getAccept() {
    return "application/vnd.animoto.rendering_job-v1+json";
  }

  public void setRenderingManifest(RenderingManifest renderingManifest) {
    this.renderingManifest = renderingManifest;
  }

  public RenderingManifest getRenderingManifest() {
    return renderingManifest;
  }

  public String toJson() {
    return newGson().toJson(new Container(this));
  }

  public void handleHttpResponse(HttpResponse httpResponse, int expectedStatusCode) throws HttpExpectationException, IOException {
    super.handleHttpResponse(httpResponse, expectedStatusCode);
    populateStoryboard();
    populateVideo();
  }

  /**
   * Allows for a Gson to reflect the outer class context into JSON.
   */
  private class Container {
    private RenderingJob renderingJob;

    public Container(RenderingJob renderingJob) {
      this.renderingJob = renderingJob;
    }
  }
}
