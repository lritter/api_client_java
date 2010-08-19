package com.animoto.api.gettable;

import com.animoto.api.job.BaseJob;

public abstract class BaseGettable extends BaseJob implements Gettable {
	public String getContentType() {
		throw new Error("Not supported for com.animoto.api.gettable.Gettable");
	}
}
