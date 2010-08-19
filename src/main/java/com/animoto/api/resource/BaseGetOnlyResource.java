package com.animoto.api.resource;

public abstract class BaseGetOnlyResource extends BaseResource {
	public String getContentType() {
		throw new Error("Not supported for com.animoto.api.resource.BaseGetOnlyResource!");
	}
}
