package com.animoto.api.resource;

import junit.framework.TestCase;

import com.animoto.api.ApiError;

import com.animoto.api.manifest.RenderingManifest;
import com.animoto.api.manifest.RenderingProfile;

import com.animoto.api.enums.HttpCallbackFormat;
import com.animoto.api.enums.VerticalResolution;
import com.animoto.api.enums.Format;

import com.animoto.api.dto.ApiResponse;

import com.animoto.api.util.GsonUtil;

import org.json.simple.*;
import org.json.simple.parser.*;

public class RenderingJobTest extends TestCase {
  public void testToJson() throws ParseException {
    RenderingJob renderingJob = new RenderingJob();
    RenderingManifest renderingManifest = new RenderingManifest();
    RenderingProfile renderingProfile = new RenderingProfile();
    String json;

    renderingJob.setHttpCallback("http://partner.com/some/callback");
    renderingJob.setHttpCallbackFormat(HttpCallbackFormat.XML);

    renderingProfile.setFramerate(new Float(29.97));
    renderingProfile.setFormat(Format.H264);
    renderingProfile.setVerticalResolution(VerticalResolution.VR_720P);
    renderingManifest.setRenderingProfile(renderingProfile);

    renderingManifest.setStoryboardUrl("http://animoto.com/storyboard/123");
    renderingJob.setRenderingManifest(renderingManifest);

    json = renderingJob.toJson();
    
    /*
      Let's use JSON Simple to parse the JSON and validate it's correctness.
     */   
    JSONObject jsonObject, jsonRenderingJob, jsonRenderingManifest, jsonRenderingProfile;
    jsonObject = (JSONObject) new JSONParser().parse(json);
    jsonRenderingJob = (JSONObject) jsonObject.get("rendering_job");
    assertEquals("http://partner.com/some/callback", jsonRenderingJob.get("http_callback"));
    assertEquals("XML", jsonRenderingJob.get("http_callback_format"));

    jsonRenderingManifest = (JSONObject) jsonRenderingJob.get("rendering_manifest");
    assertEquals("http://animoto.com/storyboard/123", jsonRenderingManifest.get("storyboard_url"));

    jsonRenderingProfile = (JSONObject) jsonRenderingManifest.get("rendering_profile");
    assertEquals(29.97, jsonRenderingProfile.get("framerate")); 
    assertEquals("h264", jsonRenderingProfile.get("format"));
    assertEquals("720p", jsonRenderingProfile.get("vertical_resolution"));
  } 

	public void testErrorParsing() {
		String json = "{\"response\":{\"status\":{\"code\":400,\"errors\":[{\"code\":\"PRESENCE\",\"message\":\"Vertical resolution can't be empty\"},{\"code\":\"ENUMERATION\",\"message\":\"Vertical resolution must be one of 180p, 240p, 360p, 480p, 720p, or 1080p\"},{\"code\":\"ENUMERATION\",\"message\":\"Framerate must be one of 12, 15, 24, or 30\"},{\"code\":\"PRESENCE\",\"message\":\"Format can't be empty\"},{\"code\":\"ENUMERATION\",\"message\":\"Format must be one of h264, h264-iphone, flv, or iso\"}]}}}";
		RenderingJob renderingJob = new RenderingJob();
		ApiResponse apiResponse = GsonUtil.create().fromJson(json, ApiResponse.class);
		ApiError[] apiErrors = null;

		assertNotNull(apiResponse);
		assertNotNull(apiResponse.getResponse());
		assertNull(apiResponse.getResponse().getPayload());
		assertNotNull(apiResponse.getResponse().getStatus());
		apiErrors = apiResponse.getResponse().getStatus().getApiErrors();
		assertNotNull(apiErrors);
		assertEquals(5, apiErrors.length);
	}
}
