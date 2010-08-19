package com.animoto.api.resource;

import com.animoto.api.Jsonable;
import com.animoto.api.DirectingManifest;
import com.animoto.api.RenderingManifest;

public class DirectingAndRenderingJob extends BaseResource implements Jsonable {

	private DirectingManifest directingManifest;
	private RenderingManifest renderingManifest;

  public String getContentType() {
    return "application/vnd.animoto.directing_and_rendering_manifest-v1+json";
  }

  public String getAccept() {
    return "application/vnd.animoto.directing_and_rendering_job-v1+json";
  }

	public void setDirectingManifest(DirectingManifest directingManifest) {
		this.directingManifest = directingManifest;
	}

	public DirectingManifest getDirectingManifest() {
		return directingManifest;
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

	private class Container {
		private DirectingAndRenderingJob directingAndRenderingJob;

		public Container(DirectingAndRenderingJob directingAndRenderingJob) {
			this.directingAndRenderingJob = directingAndRenderingJob;
		}
	}
}