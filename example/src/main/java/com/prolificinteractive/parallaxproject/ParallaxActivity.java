package com.prolificinteractive.parallaxproject;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import com.prolificinteractive.parallaxpager.ParallaxContextWrapper;

public class ParallaxActivity extends Activity {

  @Override protected void attachBaseContext(Context newBase) {
    super.attachBaseContext(new ParallaxContextWrapper(newBase));
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    if (savedInstanceState == null) {
      getFragmentManager().beginTransaction()
          .add(android.R.id.content, new ParallaxFragment())
          .commit();
    }
  }
}
