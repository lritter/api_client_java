package com.animoto.api.util;

import com.animoto.api.enums.Style;
import com.animoto.api.enums.VisualType;
import com.animoto.api.enums.ExifOrientation;
import com.animoto.api.gson.serializer.StyleSerializer;
import com.animoto.api.gson.serializer.VisualTypeSerializer;
import com.animoto.api.gson.serializer.ExifOrientationSerializer;

import com.google.gson.*;

public class GsonUtil {
  public static Gson create() {
    GsonBuilder gsonBuilder = new GsonBuilder();
    gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
    gsonBuilder.registerTypeAdapter(Style.class, new StyleSerializer());
    gsonBuilder.registerTypeAdapter(VisualType.class, new VisualTypeSerializer());
    gsonBuilder.registerTypeAdapter(ExifOrientation.class, new ExifOrientationSerializer());
    return gsonBuilder.create();
  }
}
