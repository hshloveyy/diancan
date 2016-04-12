package com.example;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.zdp.family.cookbook.image.ImageLoaderConfig;
import com.zdp.family.cookbook.util.Constants;

public class BaseApplication extends Application {
	private String jumpType = "";
	private static BaseApplication instance;

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		ImageLoaderConfig.initImageLoader(this, Constants.BASE_IMAGE_CACHE);
		queues = Volley.newRequestQueue(getApplicationContext());
		context = getApplicationContext();

	}

	@Override
	public void onLowMemory() {
		// TODO Auto-generated method stub
		super.onLowMemory();
	}

	@Override
	public void onTerminate() {
		// TODO Auto-generated method stub
		super.onTerminate();
	}

	public String getJumpType() {
		return jumpType;
	}

	public void setJumpType(String jumpType) {
		this.jumpType = jumpType;
	}

	public static RequestQueue queues;

	private static Context context;

	public static RequestQueue getRequestQueue() {
		return queues;
	}

	public static Context getcontext() {
		return context;
	}

}
