package com.animoto.api.dto;

import com.animoto.api.resource.*;

import java.lang.reflect.*;

public class Payload {
  private DirectingJob directingJob;
	private RenderingJob renderingJob;
	private Storyboard storyboard;
	private Video video;

  public DirectingJob getDirectingJob() {
    return directingJob;
  }

	public RenderingJob getRenderingJob() {
		return renderingJob;
	}

	public Storyboard getStoryboard() {
		return storyboard;
	}

	public Video getVideo() {
		return video;
	}

	public BaseResource getBaseResource(Class clazz) {
    try {
			String name = "get" + clazz.getName().substring(clazz.getName().lastIndexOf ('.') + 1);
      Method method = this.getClass().getMethod(name);
      Object value = method.invoke(this);
			return (BaseResource) value;
    }
    catch (Exception e) {
      throw new Error(e);
    }
	}
}
