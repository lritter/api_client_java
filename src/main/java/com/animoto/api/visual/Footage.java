package com.animoto.api.visual;

import com.animoto.api.enums.VisualType;
import com.animoto.api.enums.AudioMix;

public class Footage extends BaseVisual {
  private String sourceUrl;
  private AudioMix audioMix = AudioMix.NONE;
  private Float startTime = new Float(0);
  private Float duration;
  private VisualType visualType = VisualType.FOOTAGE;

  public void setSourceUrl(String sourceUrl) {
    this.sourceUrl = sourceUrl;
  }

  public String getSourceUrl() {
    return sourceUrl;
  }

  public void setAudioMix(AudioMix audioMix) {
    this.audioMix = audioMix;
  }

  public AudioMix getAudioMix() {
    return audioMix;
  }

  public void setStartTime(Float startTime) {
    this.startTime = startTime;
  }

  public Float getStartTime() {
    return startTime;
  }

  public void setDuration(Float duration) {
    this.duration = duration;
  }

  public Float getDuration() {
    return duration;
  }
}
  
