package com.animoto.api.gson.serializer;

import com.animoto.api.enums.Style;

import com.google.gson.*;

import java.lang.reflect.Type;

public class StyleSerializer implements JsonSerializer<Style> {
  public JsonElement serialize(Style style, Type typeOfStyle, JsonSerializationContext context) {
    return new JsonPrimitive(style.getValue());
  }
}
