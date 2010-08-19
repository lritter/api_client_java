package com.animoto.api;

import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;

import com.animoto.api.job.Job;
import com.animoto.api.job.BaseJob;
import com.animoto.api.job.DirectingJob;
import com.animoto.api.job.RenderingJob;
import com.animoto.api.manifest.DirectingManifest;
import com.animoto.api.manifest.RenderingManifest;
import com.animoto.api.gettable.Gettable;
import com.animoto.api.gettable.Storyboard;

import com.animoto.api.dto.ApiResponse;

import com.animoto.api.exception.ApiException;
import com.animoto.api.exception.HttpExpectationException;
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
  public void reload(Job job) throws ApiException, HttpException {
    Map<String, String> headers = new HashMap<String, String>();
    HttpResponse httpResponse;

    try {
      headers.put("Accept", job.getAccept());
      httpResponse = doHttpGet(job.getLocation(), headers);
			((BaseJob) job).handleHttpResponse(httpResponse, 200);
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

	private HttpResponse doApiHttpPost(BaseJob baseJob, String context, String httpCallback, HttpCallbackFormat httpCallbackFormat) throws HttpException {
    HttpResponse httpResponse = null;
    Map<String, String> headers = new HashMap<String, String>();

    if (httpCallback != null) {
      baseJob.setHttpCallback(httpCallback);
    }

    if (httpCallbackFormat != null) {
      baseJob.setHttpCallbackFormat(httpCallbackFormat);
    }

    try {
      headers.put("Content-Type", baseJob.getContentType());
      headers.put("Accept", baseJob.getAccept());
      httpResponse = doHttpPost(apiHost + "/jobs/" + context, ((Jsonable) baseJob).toJson(), headers);
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
