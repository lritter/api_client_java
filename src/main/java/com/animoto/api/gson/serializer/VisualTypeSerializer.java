package com.animoto.api.gson.serializer;

import com.animoto.api.enums.VisualType;

import com.google.gson.*;

import java.lang.reflect.Type;

public class VisualTypeSerializer implements JsonSerializer<VisualType> {
  public JsonElement serialize(VisualType visualType, Type typeOfVisualType, JsonSerializationContext context) {
    return new JsonPrimitive(visualType.getValue());
  }
}
