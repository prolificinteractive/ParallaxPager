package com.prolificinteractive.parallaxproject;

import android.app.Activity;
import android.os.Bundle;
import com.prolificinteractive.parallaxpager.ParallaxContainer;

public class ParallaxActivity extends Activity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_parallax);

    // find the parallax container
    ParallaxContainer parallaxContainer = (ParallaxContainer) findViewById(R.id.parallax_container);

    if (parallaxContainer != null) {
      // specify whether pager will loop
      parallaxContainer.setLooping(true);

      // wrap the inflater and inflate children with custom attributes
      parallaxContainer.setupChildren(getLayoutInflater(),
          R.layout.parallax_view_1,
          R.layout.parallax_view_2,
          R.layout.parallax_view_3,
          R.layout.parallax_view_4);
    }
  }
}
