package com.prolificinteractive.parallaxproject;

import android.app.Application;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class SampleApplication extends Application {

  @Override public void onCreate() {
    super.onCreate();

    CalligraphyConfig.initDefault(
    new CalligraphyConfig.Builder()
        .setDefaultFontPath("Bitter-Bold.ttf")
        .build()
    );
  }
}
