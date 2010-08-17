package com.animoto.api;
 
public class ApiClient {
  private String key;
  private String secret;

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
}
