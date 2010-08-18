package com.animoto.api;

import junit.framework.TestCase;

import com.animoto.api.job.DirectingJob;

import com.animoto.api.manifest.DirectingManifest;

import com.animoto.api.Song;
import com.animoto.api.visual.TitleCard;
import com.animoto.api.visual.Image;

import com.animoto.api.exception.ApiException;
import com.animoto.api.exception.HttpException;

public class ApiClientIntegrationTest extends TestCase {
  protected ApiClient apiClient = null;

  public void setUp() {
    apiClient = new ApiClient("bb0d0e005ac4012dc17712313b013462", "c0fe4cfca8bf544b8d0e687247a600ef55ff82e3");
  }

  public void testDirecting() throws ApiException, HttpException {
    DirectingManifest directingManifest = new DirectingManifest();
    DirectingJob directingJob = null;

    Image image = new Image();
    Song song = new Song();
    TitleCard titleCard = new TitleCard();

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
  }

  private void sleep(int time) {
    try {
      Thread.sleep(time);
    }
    catch (Exception ignored) {}
  }
}
