package com.animoto.api.job;

import junit.framework.TestCase;

import com.animoto.api.visual.Image;
import com.animoto.api.visual.TitleCard;
import com.animoto.api.visual.Footage;
import com.animoto.api.Song;

import com.animoto.api.manifest.DirectingManifest;

import com.animoto.api.enums.HttpCallbackFormat;
import com.animoto.api.enums.ExifOrientation;
import com.animoto.api.enums.AudioMix;
import com.animoto.api.enums.Pacing;

import org.json.simple.*;
import org.json.simple.parser.*;

public class DirectingJobTest extends TestCase {
  public void testToJson() throws ParseException {
    DirectingJob directingJob = new DirectingJob();
    DirectingManifest directingManifest = new DirectingManifest();
    Image image = new Image();
    TitleCard titleCard = new TitleCard();
    Footage footage = new Footage();
    Song song = new Song();
    String json = null;

    song.setSourceUrl("http://my.com/song.mp3");
    song.setTitle("My Test Song");
    song.setArtist("Juno");
    song.setDuration(new Float(120));
    song.setStartTime(new Float(5));
    directingManifest.setSong(song);

    image.setSourceUrl("http://somewhere.com/image_1.gif");
    image.setRotation(ExifOrientation.TWO);
    directingManifest.addVisual(image);

    titleCard.setH1("hello");
    titleCard.setH2("world");
    directingManifest.addVisual(titleCard);

    footage.setSourceUrl("http://somewhere.com/my_video.mp4");
    footage.setAudioMix(AudioMix.MIX);
    directingManifest.addVisual(footage);

    directingManifest.setTitle("My Animoto Video");
    directingManifest.setProducerName("Animoto"); 
    directingManifest.setPacing(Pacing.HALF);

    directingJob.setHttpCallback("http://partner.com/callback");
    directingJob.setHttpCallbackFormat(HttpCallbackFormat.JSON);
    directingJob.setDirectingManifest(directingManifest);

    json = directingJob.toJson();
    
    /*
      Let's use JSON Simple to parse the JSON and validate it's correctness.
     */   
    JSONObject jsonObject, jsonDirectingJob, jsonDirectingManifest, jsonVisual, jsonSong;
    JSONArray jsonVisuals;

    jsonObject = (JSONObject) new JSONParser().parse(json);
    jsonDirectingJob = (JSONObject) jsonObject.get("directing_job");
    assertEquals("http://partner.com/callback", jsonDirectingJob.get("http_callback"));
    assertEquals("JSON", jsonDirectingJob.get("http_callback_format"));

    jsonDirectingManifest = (JSONObject) jsonDirectingJob.get("directing_manifest");
    assertEquals("original", jsonDirectingManifest.get("style"));
    assertEquals("My Animoto Video", jsonDirectingManifest.get("title"));
    assertEquals("Animoto", jsonDirectingManifest.get("producer_name"));
    assertEquals("HALF", jsonDirectingManifest.get("pacing"));

    jsonSong = (JSONObject) jsonDirectingManifest.get("song");
    assertEquals("http://my.com/song.mp3", jsonSong.get("source_url"));
    assertEquals("My Test Song", jsonSong.get("title"));
    assertEquals("Juno", jsonSong.get("artist"));
    assertEquals(120.0, jsonSong.get("duration"));
    assertEquals(5.0, jsonSong.get("start_time"));
    
    jsonVisuals = (JSONArray) jsonDirectingManifest.get("visuals");
    assertEquals(3, jsonVisuals.size());    
    for (int i = 0; i < jsonVisuals.size(); i++) {
      jsonVisual = (JSONObject) jsonVisuals.get(i);
      if (jsonVisual.get("type").equals("image")) {
        assertEquals(2, ((Number) jsonVisual.get("rotation")).intValue());
        assertEquals("http://somewhere.com/image_1.gif", jsonVisual.get("source_url"));
      }
      else if (jsonVisual.get("type").equals("footage")) {
        assertEquals("MIX", jsonVisual.get("audio_mix"));  
        assertEquals("http://somewhere.com/my_video.mp4", jsonVisual.get("source_url"));
      }
      else if (jsonVisual.get("type").equals("title_card")) {
        assertEquals("hello", jsonVisual.get("h1"));
        assertEquals("world", jsonVisual.get("h2"));
      }
      else {
        fail("Unknown Visual Type");
      }
    }
  } 
}
