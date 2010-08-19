package com.animoto.api;

import junit.framework.TestCase;

import com.animoto.api.resource.DirectingJob;
import com.animoto.api.resource.RenderingJob;
import com.animoto.api.resource.Storyboard;

import com.animoto.api.manifest.DirectingManifest;
import com.animoto.api.manifest.RenderingManifest;
import com.animoto.api.manifest.RenderingProfile;

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

  protected DirectingJob createDirectingJob() {
    DirectingManifest directingManifest = new DirectingManifest();
    DirectingJob directingJob = null;

    Image image = new Image();
    Song song = new Song();
    TitleCard titleCard = new TitleCard();

		try {

    	titleCard.setH1("Integration Test");
    	titleCard.setH2("From Animoto API Java Client");
    	directingManifest.addVisual(titleCard);

    	image.setSourceUrl("http://api.client.java.animoto.s3.amazonaws.com/banker.jpg");
    	directingManifest.addVisual(image);

    	song.setSourceUrl("http://api.client.java.animoto.s3.amazonaws.com/blur.mp3");
    	directingManifest.setSong(song);
 
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
      	apiClient.reload(directingJob);
    	}
    
    	// Job is complete!
    	assertTrue(directingJob.isComplete());
    	assertNotNull(directingJob.getStoryboard());
			assertNotNull(directingJob.getStoryboard().getLocation());
		}
		catch (Exception e) {
			fail(e.toString());
		}
		return directingJob;
  }

	public void testDirecting() {
		createDirectingJob();
	}

	public void testReloadStoryboard() {
		DirectingJob directingJob = createDirectingJob();
		Storyboard storyboard = directingJob.getStoryboard();

		try {
			apiClient.reload(storyboard);
			assertNotNull(storyboard.getLinks());
			assertTrue(storyboard.getLinks().size() > 0);
		}
		catch (Exception e) {
			fail(e.toString());
		}
	}

	public void testRendering() {
		DirectingJob directingJob = createDirectingJob();	
		RenderingJob renderingJob;
		RenderingManifest renderingManifest = new RenderingManifest();
		RenderingProfile renderingProfile = new RenderingProfile();
	
		try {
			renderingProfile.setFormat(Format.H264);
			renderingProfile.setVerticalResolution(VerticalResolution.VR_480P);
			renderingProfile.setFramerate(new	Float(30));
			renderingManifest.setStoryboardUrl(directingJob.getStoryboard().getUrl());
			renderingManifest.setRenderingProfile(renderingProfile);

			renderingJob = apiClient.render(renderingManifest);	
			assertTrue(renderingJob.isPending());
			assertNotNull(renderingJob.getLocation());
			assertNotNull(renderingJob.getRequestId());
			while(renderingJob.isPending()) {
				sleep(3000);
				apiClient.reload(renderingJob);
			}
			assertTrue(renderingJob.isComplete());
		}
		catch (Exception e) {
			fail(e.toString());
		}
	}


  private void sleep(int time) {
    try {
      Thread.sleep(time);
    }
    catch (Exception ignored) {}
  }
}
