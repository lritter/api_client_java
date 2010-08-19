package com.animoto.api.job;

import com.animoto.api.Jsonable;
import com.animoto.api.manifest.RenderingManifest;
import com.animoto.api.dto.ApiResponse;
import com.animoto.api.exception.HttpExpectationException;
import com.animoto.api.error.ContractError;
import com.animoto.api.util.GsonUtil;
import com.animoto.api.util.StringUtil;

import java.io.IOException;

import org.apache.http.HttpResponse;

public class RenderingJob extends BaseJob implements Jsonable {
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
    int statusCode;
    String body;
    ApiResponse apiResponse;
    com.animoto.api.dto.RenderingJob dtoRenderingJob;

    statusCode = httpResponse.getStatusLine().getStatusCode();
    body = StringUtil.convertStreamToString(httpResponse.getEntity().getContent());
    if (statusCode != expectedStatusCode) {
      throw new HttpExpectationException(statusCode, expectedStatusCode, body);
    }

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
