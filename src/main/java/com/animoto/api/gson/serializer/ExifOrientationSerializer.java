package com.animoto.api.gson.serializer;

import com.animoto.api.enums.ExifOrientation;

import com.google.gson.*;

import java.lang.reflect.Type;

public class ExifOrientationSerializer implements JsonSerializer<ExifOrientation> {
  public JsonElement serialize(ExifOrientation exifOrientation, Type typeOfExifOrientation, JsonSerializationContext context) {
    return new JsonPrimitive(exifOrientation.getValue());
  }
}
