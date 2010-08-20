package com.animoto.api.resource;

/**
 * A Video represents the video metadata of the video Animoto generated for you.<p/>
 *
 * You will need to call ApiClient.reload() in order to obtain the latest information from API.<p/>
 *
 * @see com.animoto.api.ApiClient
 */
public class Video extends BaseHttpGetOnlyResource {
  public String getAccept() {
    return "application/vnd.animoto.video-v1+json";
  }
}
