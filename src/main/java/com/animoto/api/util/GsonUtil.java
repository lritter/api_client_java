package com.animoto.api.util;

import com.animoto.api.enums.*;
import com.animoto.api.gson.serializer.*;

import com.google.gson.*;

public class GsonUtil {
  public static Gson create() {
    GsonBuilder gsonBuilder = new GsonBuilder();
    gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
    gsonBuilder.registerTypeAdapter(Style.class, new SimpleEnumSerializer());
    gsonBuilder.registerTypeAdapter(VisualType.class, new SimpleEnumSerializer());
    gsonBuilder.registerTypeAdapter(ExifOrientation.class, new SimpleEnumSerializer());
    gsonBuilder.registerTypeAdapter(VerticalResolution.class, new SimpleEnumSerializer());
    gsonBuilder.registerTypeAdapter(Format.class, new SimpleEnumSerializer());
    return gsonBuilder.create();
  }
}
