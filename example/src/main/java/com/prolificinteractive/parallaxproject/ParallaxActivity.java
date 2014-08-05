package com.prolificinteractive.parallaxproject;

import android.app.Activity;
import android.os.Bundle;

public class ParallaxActivity extends Activity {

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
