package com.animoto.api;

import junit.framework.TestCase;

import java.util.Iterator;

import com.animoto.api.util.DirectingManifestFactory;
import com.animoto.api.util.RenderingManifestFactory;

import com.animoto.api.resource.BaseResource;
import com.animoto.api.resource.DirectingJob;
import com.animoto.api.resource.RenderingJob;
import com.animoto.api.resource.DirectingAndRenderingJob;
import com.animoto.api.resource.Storyboard;
import com.animoto.api.resource.Video;

import com.animoto.api.DirectingManifest;
import com.animoto.api.RenderingManifest;
import com.animoto.api.RenderingProfile;

import com.animoto.api.Song;
import com.animoto.api.visual.TitleCard;
import com.animoto.api.visual.Image;

import com.animoto.api.exception.ApiException;
import com.animoto.api.exception.HttpExpectationException;
import com.animoto.api.exception.HttpException;

import com.animoto.api.enums.VerticalResolution;
import com.animoto.api.enums.Format;

public class ApiClientIntegrationTest extends TestCase {
  protected ApiClient apiClient = null;

  public void setUp() {
    apiClient = new ApiClient("bb0d0e005ac4012dc17712313b013462", "c0fe4cfca8bf544b8d0e687247a600ef55ff82e3");
  }

  public void testirecting() {
    createDirectingJob();
  }

  public void testStoryboard() {
    DirectingJob directingJob = createDirectingJob();
    Storyboard storyboard = directingJob.getStoryboard();

    try {
      apiClient.reload(storyboard);
      assertNotNull(storyboard.getLinks());
      assertTrue(storyboard.getLinks().size() > 0);
      assertNotNull(storyboard.getMetadata());
      assertTrue(storyboard.getMetadata().size() > 0);
    }
    catch (Exception e) {
      fail(e.toString());
    }
  }

  public void testRenderingError() throws Exception {
    DirectingJob directingJob = createDirectingJob();
    RenderingJob renderingJob = null;
    RenderingManifest renderingManifest = new RenderingManifest();
    RenderingProfile renderingProfile = new RenderingProfile();
 
    renderingProfile.setFramerate(new Float(1.23));
    renderingManifest.setStoryboard(directingJob.getStoryboard());
    renderingManifest.setRenderingProfile(renderingProfile);
    try {
      renderingJob = apiClient.render(renderingManifest);
      fail("Expected error from API!");
    }
    catch (HttpExpectationException e) {
      assertEquals(201, e.getExpectedCode());
      assertEquals(400, e.getReceivedCode());
      assertNotNull(e.getApiErrors());
      assertNotNull(e.getBody());
      assertEquals(5, e.getApiErrors().length);
    }
    catch (Exception e) {
      throw e;
    }
  }

  public void testRenderingJob() {
    createRenderingJob();
  }

  public void testVideo() {
    RenderingJob renderingJob = createRenderingJob();
    Video video = null;

    try {
      video = renderingJob.getVideo();
      apiClient.reload(video);
      assertNotNull(video.getLinks());
      assertTrue(video.getLinks().size() > 0);
    }
    catch (Exception e) {
      fail(e.toString());
    }
  }

  public void testDirectingAndRendering() {
    DirectingAndRenderingJob directingAndRenderingJob;
    DirectingManifest directingManifest = DirectingManifestFactory.newInstance();
    RenderingManifest renderingManifest = RenderingManifestFactory.newInstance();

    try {
      directingAndRenderingJob = apiClient.directAndRender(directingManifest, renderingManifest);
      assertTrue(directingAndRenderingJob.isPending());
      while(directingAndRenderingJob.isPending()) {
        sleep(3000);
        apiClient.reload(directingAndRenderingJob);
      }
      assertTrue(directingAndRenderingJob.isComplete());
      assertNotNull(directingAndRenderingJob.getStoryboard());
      assertNotNull(directingAndRenderingJob.getVideo());
    }
    catch (Exception e) {
      fail(e.toString());
    }
  }

  protected DirectingJob createDirectingJob() {
    DirectingManifest directingManifest = DirectingManifestFactory.newInstance();
    DirectingJob directingJob = null;

    try {
      // Post a directing job to the API. 
      directingJob = apiClient.direct(directingManifest);
      assertNotNull(directingJob);
      assertNotNull(directingJob.getLocation());
      assertNotNull(directingJob.getRequestId());
      assertEquals("retrieving_assets", directingJob.getState());
      assertTrue(directingJob.isPending());

      // Wait until it is completed.
      while(directingJob.isPending()) {
        sleep(3000);
        print(directingJob);
        apiClient.reload(directingJob);
        assertFalse(directingJob.isFailed());
      }

      // Job is complete!
      assertTrue(directingJob.isComplete());
      assertNotNull(directingJob.getStoryboard());
      assertNotNull(directingJob.getResponse());
      assertNotNull(directingJob.getStoryboard().getLocation());
    }
    catch (Exception e) {
      fail(e.toString());
    }
    return directingJob;
  }

  protected RenderingJob createRenderingJob() {
    DirectingJob directingJob = createDirectingJob(); 
    RenderingJob renderingJob = null;
    RenderingManifest renderingManifest = RenderingManifestFactory.newInstance();
  
    try {
      renderingManifest.setStoryboard(directingJob.getStoryboard());
      renderingJob = apiClient.render(renderingManifest); 
      assertTrue(renderingJob.isPending());
      assertNotNull(renderingJob.getLocation());
      assertNotNull(renderingJob.getRequestId());
      while(renderingJob.isPending()) {
        sleep(3000);
        apiClient.reload(renderingJob);
      }
      assertTrue(renderingJob.isComplete());
      assertNotNull(renderingJob.getVideo());
      assertNotNull(renderingJob.getStoryboard());
    }
    catch (Exception e) {
      fail(e.toString());
    }
    return renderingJob;
  }

  private void sleep(int time) {
    try {
      Thread.sleep(time);
    }
    catch (Exception ignored) {}
  }

  private void print(BaseResource baseResource) {
    StringBuffer buf = new StringBuffer();
    String key;
    Iterator it = null;

    buf.append("@" + new java.util.Date().toString() + "\n");
    buf.append("------------------------------------------------------------\n");
    buf.append("request id: " + baseResource.getRequestId() + "\n");
    buf.append("state: " + baseResource.getState() + "\n");
    buf.append("location: " + baseResource.getLocation() + "\n");
    buf.append("current links\n");
    buf.append("-------------\n");
    it = baseResource.getLinks().keySet().iterator();
    while (it.hasNext()) {
      key = (String) it.next();
      buf.append(key + ": " + baseResource.getLinks().get(key) + "\n"); 
    }
    System.out.println(buf.toString());
  }
}
