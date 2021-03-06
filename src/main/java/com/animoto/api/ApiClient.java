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
import com.animoto.api.exception.ContractException;

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

/**
 * ApiClient is the main class for interacting with the Animoto API. <p/>
 * 
 * To create an ApiClient using a resource bundle, animoto_api_client.properties, use the ApiClientFactory.<p/>
 * 
 * For further information visit: <p/>
 *
 * <a href="http://github.com/animoto/api_client_java">http://github.com/animoto/api_client_java</a>
 * 
 * <a href="doc-files/help.html">For more information...</a>
 * 
 * @author  SunDawg
 * @since   1.0
 * @version 1.0
 *
 * @see ApiClientFactory
 */
public class ApiClient {
  private String key;
  private String secret;
  private String host = "https://api2-staging.animoto.com";

  /**
   * Default constructor. You will need to set a key and secret.
   */
  public ApiClient() {
  }

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

  public void setHost(String host) {
    this.host = host;
  }

  public String getHost() {
    return host;
  }

  /**
   * Creates a directing job with no http callbacks.
   * 
   * @param       directingManifest           The manifest payload to direct.
   * @return      DirectingJob
   * @exception   HttpExpectationException
   * @exception   HttpException
   * @exception   ContractException
   */
  public DirectingJob direct(DirectingManifest directingManifest) throws HttpExpectationException, HttpException, ContractException {
    return direct(directingManifest, null, null);
  }

  /**
   * Creates a directing job with http callbacks.
   *
   * @param       directingManifest           The manifest payload to direct.
   * @param       httpCallback                The callback URL the API will communicate back to.
   * @param       httpCallbackFormat          The payload type when the callback is made.
   * @exception   HttpExpectationException
   * @exception   HttpExpectation
   * @exception   ContractException
   */
  public DirectingJob direct(DirectingManifest directingManifest, String httpCallback, HttpCallbackFormat httpCallbackFormat) throws HttpExpectationException, HttpException, ContractException {
    DirectingJob directingJob = new DirectingJob();
    directingJob.setDirectingManifest(directingManifest);
    direct(directingJob, httpCallback, httpCallbackFormat);
    return directingJob;
  }

  protected void direct(DirectingJob directingJob, String httpCallback, HttpCallbackFormat httpCallbackFormat) throws HttpExpectationException, HttpException, ContractException {
    HttpResponse httpResponse = doApiHttpPost(directingJob, "directing", httpCallback, httpCallbackFormat);
    try {
      directingJob.handleHttpResponse(httpResponse, 201);
    }
    catch (IOException e) {
      throw new HttpException(e);
    }
  }

  /**
   * Creates a rendering job with no http callbacks.
   *
   * @param       renderingManifest           The manifest payload to render.   
   * @exception   HttpExpectationException
   * @exception   HttpExpectation
   * @exception   ContractException
   */
  public RenderingJob render(RenderingManifest renderingManifest) throws HttpExpectationException, HttpException, ContractException {
    return render(renderingManifest, null, null);
  }

  /**
   * Creates a rendering job with http callbacks.
   *
   * @param       renderingManifest           The manifest payload to render.
   * @param       httpCallback                The callback URL the API will communicate back to.
   * @param       httpCallbackFormat          The payload type when the callback is made.
   * @exception   HttpExpectationException
   * @exception   HttpExpectation
   * @exception   ContractException
   */
  public RenderingJob render(RenderingManifest renderingManifest, String httpCallback, HttpCallbackFormat httpCallbackFormat) throws HttpExpectationException, HttpException, ContractException {
    RenderingJob renderingJob = new RenderingJob();
    renderingJob.setRenderingManifest(renderingManifest);
    render(renderingJob, httpCallback, httpCallbackFormat);
    return renderingJob;
  }

  protected void render(RenderingJob renderingJob, String httpCallback, HttpCallbackFormat httpCallbackFormat) throws HttpExpectationException, HttpException, ContractException {
    HttpResponse httpResponse = doApiHttpPost(renderingJob, "rendering", httpCallback, httpCallbackFormat);
    try {
      renderingJob.handleHttpResponse(httpResponse, 201);
    }
    catch (IOException e) {
      throw new HttpException(e);
    } 
  }


  /**
   *
   */
  public DirectingAndRenderingJob directAndRender(DirectingManifest directingManifest, RenderingManifest renderingManifest) throws HttpExpectationException, HttpException, ContractException {
    return directAndRender(directingManifest, renderingManifest, null, null);
  }

  /**
   * Creates a DirectingAndRendering job from the API.
   *
   * @param       directingManifest             The manifest payload to direct.
   * @param       renderingManifest             The manifest payload to render. 
   * @param       httpCallback                  The callback URL the API will communicate back to. 
   * @param       httpCallbackFormat            The payload type when the callback is made.
   * @exception   HttpExpectationException
   * @exception   HttpExpectation
   * @exception   ContractException
   */
  public DirectingAndRenderingJob directAndRender(DirectingManifest directingManifest, RenderingManifest renderingManifest, String httpCallback, HttpCallbackFormat httpCallbackFormat) throws HttpExpectationException, HttpException, ContractException {
    DirectingAndRenderingJob directingAndRenderingJob = new DirectingAndRenderingJob();
    directingAndRenderingJob.setDirectingManifest(directingManifest);
    directingAndRenderingJob.setRenderingManifest(renderingManifest);
    renderingManifest.setStoryboardUrl(null);
    directAndRender(directingAndRenderingJob, httpCallback, httpCallbackFormat); 
    return directingAndRenderingJob;
  }

  protected void directAndRender(DirectingAndRenderingJob directingAndRenderingJob, String httpCallback, HttpCallbackFormat httpCallbackFormat) throws HttpExpectationException, HttpException, ContractException {
    HttpResponse httpResponse = doApiHttpPost(directingAndRenderingJob, "directing_and_rendering", httpCallback, httpCallbackFormat);
    try {
      directingAndRenderingJob.handleHttpResponse(httpResponse, 201);
    }
    catch (IOException e) {
      throw new HttpException(e);
    }
  }

  /**
   * Communicates with the API to grab the latest information for a resource.
   *
   * @param       resource                    The resource to refresh with the latest information from API.
   * @exception   HttpException 
   * @exception   HttpExpectationException
   * @exception   ContractException
   */
  public void reload(Resource resource) throws HttpException, HttpExpectationException, ContractException {
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

  private HttpResponse doHttpGet(String url, Map<String, String> headers) throws IOException, UnsupportedEncodingException {
    HttpGet httpGet = new HttpGet(url);
    return doHttpRequest(httpGet, headers);
  }

  private HttpResponse doHttpPost(String url, String postBody, Map<String, String> headers) throws IOException, UnsupportedEncodingException {
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
      httpResponse = doHttpPost(host + "/jobs/" + context, ((Jsonable) baseResource).toJson(), headers);
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
