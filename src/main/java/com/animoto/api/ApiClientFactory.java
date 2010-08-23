package com.animoto.api;

import java.util.ResourceBundle;

/**
 * Factory to create ApiClient objects with key, secret, and host read from a properties file, animoto_api_client.properties.
 */
public class ApiClientFactory {

  public static ApiClient newInstance() {
    ResourceBundle resourceBundle = ResourceBundle.getBundle("animoto_api_client");
    ApiClient apiClient = new ApiClient();
    apiClient.setKey(resourceBundle.getString("api.key").trim());
    apiClient.setSecret(resourceBundle.getString("api.secret").trim());
    apiClient.setHost(resourceBundle.getString("api.host").trim()); 
    return apiClient;
  }
}
