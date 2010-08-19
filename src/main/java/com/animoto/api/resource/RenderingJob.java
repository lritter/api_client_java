package com.animoto.api.resource;

import com.animoto.api.Jsonable;
import com.animoto.api.manifest.RenderingManifest;
import com.animoto.api.dto.ApiResponse;
import com.animoto.api.exception.HttpExpectationException;
import com.animoto.api.error.ContractError;
import com.animoto.api.util.GsonUtil;

import java.io.IOException;

import org.apache.http.HttpResponse;

public class RenderingJob extends BaseResource implements Jsonable {
  private RenderingManifest renderingManifest;

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
		String body = validateHttpExpectations(httpResponse, expectedStatusCode);
    ApiResponse apiResponse;
    com.animoto.api.dto.RenderingJob dtoRenderingJob;

    apiResponse = GsonUtil.create().fromJson(body, ApiResponse.class);
    dtoRenderingJob = apiResponse.getResponse().getPayload().getRenderingJob();
    setState(dtoRenderingJob.getState());
    setLocation(dtoRenderingJob.getLinks().get("self"));
    setRequestId(httpResponse.getFirstHeader("x-animoto-request-id").getValue());
    if (getLocation() == null) {
      throw new ContractError();
    }
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
