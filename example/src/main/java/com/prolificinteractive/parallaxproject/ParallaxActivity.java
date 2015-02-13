package com.prolificinteractive.parallaxproject;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import com.prolificinteractive.parallaxpager.ParallaxContextWrapper;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ParallaxActivity extends Activity {

  @Override protected void attachBaseContext(Context newBase) {
    super.attachBaseContext(CalligraphyContextWrapper.wrap(new ParallaxContextWrapper(newBase)));
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    CalligraphyConfig.initDefault(
        new CalligraphyConfig.Builder()
            .setDefaultFontPath("Bitter-Bold.ttf")
            .build()
    );

    if (savedInstanceState == null) {
      getFragmentManager().beginTransaction()
          .add(android.R.id.content, new ParallaxFragment())
          .commit();
    }
  }
}
