package com.animoto.api;

import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;

import com.animoto.api.job.BaseJob;
import com.animoto.api.job.DirectingJob;
import com.animoto.api.manifest.DirectingManifest;

import com.animoto.api.dto.ApiResponse;

import com.animoto.api.exception.ApiException;
import com.animoto.api.exception.DirectingException;
import com.animoto.api.exception.HttpException;

import com.animoto.api.error.ContractError;

import com.animoto.api.enums.HttpCallbackFormat;

import com.animoto.api.util.StringUtil;
import com.animoto.api.util.GsonUtil;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.auth.AuthScope;
import org.apache.http.entity.StringEntity;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class ApiClient {
  private String key;
  private String secret;
  private String apiHost = "https://api2-staging.animoto.com";

  /**
   * Constructor
   *
   * @param key     Your Animoto API key
   * @param secret  Your Animoto API secret
   */
  public ApiClient(String key, String secret) {
    this.key = key;
    this.secret = secret;
  }

  public String getKey() {
    return this.key;
  }

  public String getSecret() {
    return this.secret;
  }

  /**
   *
   */
  public DirectingJob direct(DirectingManifest directingManifest) throws DirectingException, HttpException {
    return direct(directingManifest, null, null);
  }

  /**
   *
   */
  public DirectingJob direct(DirectingManifest directingManifest, String httpCallback, HttpCallbackFormat httpCallbackFormat) throws DirectingException, HttpException {
    DirectingJob directingJob = new DirectingJob();
    HttpResponse httpResponse = null;
    int statusCode;
    String body;
    Map<String, String> headers = new HashMap<String, String>();

    if (httpCallback != null) {
      directingJob.setHttpCallback(httpCallback);
    }

    if (httpCallbackFormat != null) {
      directingJob.setHttpCallbackFormat(httpCallbackFormat);
    }

    try {
      directingJob.setDirectingManifest(directingManifest);
      headers.put("Content-Type", "application/vnd.animoto.directing_manifest-v1+json"); 
      headers.put("Accept", "application/vnd.animoto.directing_job-v1+json");
      httpResponse = doHttpPost(apiHost + "/jobs/directing", directingJob.toJson(), headers);
      handleDirectingJobResponse(directingJob, httpResponse, 201);
    }
    catch (IOException e) {
      throw new HttpException(e);
    }
    return directingJob; 
  }

  /**
   *
   */
  public void reload(BaseJob job) throws ApiException, HttpException {
    String location = job.getLocation();
    Map<String, String> headers = new HashMap<String, String>();
    HttpResponse httpResponse;

    try {
      if (job instanceof DirectingJob) {
        headers.put("Accept", "application/vnd.animoto.directing_job-v1+json");
        httpResponse = doHttpGet(location, headers);
        handleDirectingJobResponse((DirectingJob) job, httpResponse, 200); 
      }
      else {
        throw new Error("NOT SUPPORTED");
      }
    }
    catch (IOException e) {
      throw new HttpException(e);
    } 
  }

  protected HttpResponse doHttpGet(String url, Map<String, String> headers) throws IOException, UnsupportedEncodingException {
    HttpGet httpGet = new HttpGet(url);
    return doHttpRequest(httpGet, headers);
  }

  protected HttpResponse doHttpPost(String url, String postBody, Map<String, String> headers) throws IOException, UnsupportedEncodingException {
    HttpPost httpPost = new HttpPost(url);
    httpPost.setEntity(new StringEntity(postBody));
    return doHttpRequest(httpPost, headers);
  }

  private HttpResponse doHttpRequest(HttpRequestBase httpRequestBase, Map<String, String> headers) throws IOException, UnsupportedEncodingException {
    UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(key, secret);
    DefaultHttpClient httpClient = new DefaultHttpClient();
    Iterator it = headers.keySet().iterator();
    String key = null;
    while (it.hasNext()) {
      key = (String) it.next();
      httpRequestBase.addHeader(key, headers.get(key));
    }
    httpClient.getCredentialsProvider().setCredentials(AuthScope.ANY, credentials);
    return httpClient.execute(httpRequestBase);
  } 
  
  private void handleDirectingJobResponse(DirectingJob directingJob, HttpResponse httpResponse, int expectedStatusCode) throws DirectingException, IOException {
    int statusCode;
    String body;
    ApiResponse apiResponse;
    com.animoto.api.dto.DirectingJob dtoDirectingJob;
    statusCode = httpResponse.getStatusLine().getStatusCode();
    body = StringUtil.convertStreamToString(httpResponse.getEntity().getContent());

    if (statusCode != expectedStatusCode) {
      throw new DirectingException(statusCode, expectedStatusCode, body);
    }

    // Parse the state and links of the directing job JSON
    apiResponse = GsonUtil.create().fromJson(body, ApiResponse.class);
    dtoDirectingJob = apiResponse.getResponse().getPayload().getDirectingJob();
    directingJob.setState(dtoDirectingJob.getState());
    directingJob.setLocation(dtoDirectingJob.getLinks().get("self"));
    if (directingJob.getLocation() == null) {
      throw new ContractError();
    }

    // If the directing job is complete, then populate the storyboard.
    if (directingJob.isComplete()) {
      directingJob.setStoryboard(dtoDirectingJob.getLinks().get("storyboard"));
    }
  }
}
