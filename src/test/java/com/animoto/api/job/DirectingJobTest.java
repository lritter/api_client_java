package com.animoto.api.job;

import junit.framework.TestCase;

import com.animoto.api.manifest.DirectingManifest;

import com.animoto.api.enums.HttpCallbackFormat;

public class DirectingJobTest extends TestCase {
  public void testToJson() {
    DirectingJob directingJob = new DirectingJob();
    DirectingManifest directingManifest = new DirectingManifest();

    directingManifest.setTitle("My Animoto Video");
    directingManifest.setProducerName("Animoto");

    directingJob.setHttpCallback("http://partner.com/callback");
    directingJob.setHttpCallbackFormat(HttpCallbackFormat.JSON);
    directingJob.setDirectingManifest(directingManifest);

    System.out.println("" + directingJob.toJson());
  }
}
