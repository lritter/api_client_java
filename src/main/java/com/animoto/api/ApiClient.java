package com.animoto.api;

import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;

import com.animoto.api.resource.Resource;
import com.animoto.api.resource.BaseResource;
import com.animoto.api.resource.DirectingJob;
import com.animoto.api.resource.RenderingJob;
import com.animoto.api.resource.DirectingAndRenderingJob;
import com.animoto.api.DirectingManifest;
import com.animoto.api.RenderingManifest;

import com.animoto.api.exception.ApiException;
import com.animoto.api.exception.HttpExpectationException;
import com.animoto.api.exception.HttpException;

import com.animoto.api.enums.HttpCallbackFormat;

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

  public void setKey(String key) {
    this.key = key;
  }

  public String getKey() {
    return key;
  }

  public void setSecret(String secret) {
    this.secret = secret;
  }

  public String getSecret() {
    return secret;
  }

  public void setApiHost(String apiHost) {
    this.apiHost = apiHost;
  }

  public String getApiHost() {
    return apiHost;
  }

  /**
   *
   */
  public DirectingJob direct(DirectingManifest directingManifest) throws HttpExpectationException, HttpException {
    return direct(directingManifest, null, null);
  }

  /**
   *
   */
  public DirectingJob direct(DirectingManifest directingManifest, String httpCallback, HttpCallbackFormat httpCallbackFormat) throws HttpExpectationException, HttpException {
    DirectingJob directingJob = new DirectingJob();
    HttpResponse httpResponse;

    directingJob.setDirectingManifest(directingManifest);
    httpResponse = doApiHttpPost(directingJob, "directing", httpCallback, httpCallbackFormat);
    try {
      directingJob.handleHttpResponse(httpResponse, 201);
    }
    catch (IOException e) {
      throw new HttpException(e);
    }
    return directingJob; 
  }

  /**
   *
   */
  public RenderingJob render(RenderingManifest renderingManifest) throws HttpExpectationException, HttpException {
    return render(renderingManifest, null, null);
  }

  /**
   *
   */
  public RenderingJob render(RenderingManifest renderingManifest, String httpCallback, HttpCallbackFormat httpCallbackFormat) throws HttpExpectationException, HttpException {
    RenderingJob renderingJob = new RenderingJob();
    HttpResponse httpResponse;

    renderingJob.setRenderingManifest(renderingManifest);
    httpResponse = doApiHttpPost(renderingJob, "rendering", httpCallback, httpCallbackFormat);
    try {
      renderingJob.handleHttpResponse(httpResponse, 201);
    }
    catch (IOException e) {
      throw new HttpException(e);
    } 
    return renderingJob;
  }


  /**
   *
   */
  public DirectingAndRenderingJob directAndRender(DirectingManifest directingManifest, RenderingManifest renderingManifest) throws HttpExpectationException, HttpException {
    return directAndRender(directingManifest, renderingManifest, null, null);
  }


  /**
   *
   */
  public DirectingAndRenderingJob directAndRender(DirectingManifest directingManifest, RenderingManifest renderingManifest, String httpCallback, HttpCallbackFormat httpCallbackFormat) throws HttpExpectationException, HttpException {
    DirectingAndRenderingJob directingAndRenderingJob = new DirectingAndRenderingJob();
    HttpResponse httpResponse;

    directingAndRenderingJob.setDirectingManifest(directingManifest);
    directingAndRenderingJob.setRenderingManifest(renderingManifest);
    renderingManifest.setStoryboardUrl(null);
    httpResponse = doApiHttpPost(directingAndRenderingJob, "directing_and_rendering", httpCallback, httpCallbackFormat);
    try {
      directingAndRenderingJob.handleHttpResponse(httpResponse, 201);
    }
    catch (IOException e) {
      throw new HttpException(e);
    }
    return directingAndRenderingJob;
  }

  /**
   *
   */
  public void reload(Resource resource) throws ApiException, HttpException {
    Map<String, String> headers = new HashMap<String, String>();
    HttpResponse httpResponse;

    try {
      headers.put("Accept", resource.getAccept());
      httpResponse = doHttpGet(resource.getLocation(), headers);
      ((BaseResource) resource).handleHttpResponse(httpResponse, 200);
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

  private HttpResponse doApiHttpPost(BaseResource baseResource, String context, String httpCallback, HttpCallbackFormat httpCallbackFormat) throws HttpException {
    HttpResponse httpResponse = null;
    Map<String, String> headers = new HashMap<String, String>();

    if (httpCallback != null) {
      baseResource.setHttpCallback(httpCallback);
    }

    if (httpCallbackFormat != null) {
      baseResource.setHttpCallbackFormat(httpCallbackFormat);
    }

    try {
      headers.put("Content-Type", baseResource.getContentType());
      headers.put("Accept", baseResource.getAccept());
      httpResponse = doHttpPost(apiHost + "/jobs/" + context, ((Jsonable) baseResource).toJson(), headers);
    }
    catch (IOException e) {
      throw new HttpException(e);
    }
    return httpResponse;
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
}
