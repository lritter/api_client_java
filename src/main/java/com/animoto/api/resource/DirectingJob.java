package com.animoto.api.resource;

import com.animoto.api.Jsonable;
import com.animoto.api.manifest.DirectingManifest;
import com.animoto.api.exception.HttpExpectationException;
import com.animoto.api.util.GsonUtil;

import java.io.IOException;

import org.apache.http.HttpResponse;

public class DirectingJob extends BaseResource implements Jsonable {
  private DirectingManifest directingManifest;
  private Storyboard storyboard;

	public String getContentType() {
		return "application/vnd.animoto.directing_manifest-v1+json";
	}

	public String getAccept() {
		return "application/vnd.animoto.directing_job-v1+json";
	}

  public void setDirectingManifest(DirectingManifest directingManifest) {
    this.directingManifest = directingManifest;
  }

  public DirectingManifest getDirectingManifest() {
    return directingManifest;
  }

  public void setStoryboard(Storyboard storyboard) {
    this.storyboard = storyboard;
  }

  public Storyboard getStoryboard() {
    return storyboard;
  }

  public String toJson() {
    return newGson().toJson(new Container(this));
  }

	public void handleHttpResponse(HttpResponse httpResponse, int expectedStatusCode) throws HttpExpectationException, IOException {
		super.handleHttpResponse(httpResponse, expectedStatusCode);

    // If the directing job is complete, then populate the storyboard.
    if (isComplete()) {
     	storyboard = new Storyboard();
     	storyboard.getLinks().put("self", getLinks().get("storyboard"));
     	setStoryboard(storyboard);
    }
  }

  /**
   * Allows for a Gson to reflect the outer class context into JSON.
   */
  private class Container {
    private DirectingJob directingJob;

    public Container(DirectingJob directingJob) {
      this.directingJob = directingJob;  
    }
  }
}
