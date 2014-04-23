package com.prolific.parallaxproject;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import com.prolific.parallaxpager.ParallaxContainer;

public class ParallaxActivity extends FragmentActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_parallax);

    // find the parallax container
    ParallaxContainer parallaxContainer = (ParallaxContainer) findViewById(R.id.parallax_container_1);

    // list the layout for each page (in order)
    int[] parallaxLayoutIds = {
        R.layout.parallax_view_1, R.layout.parallax_view_2, R.layout.parallax_view_3
    };

    // attach fragment manager, layout inflater, and children. specify whether pager will loop.
    if (parallaxContainer != null) {
      parallaxContainer.setupChildren(getSupportFragmentManager(), getLayoutInflater(), parallaxLayoutIds, true);
    }
  }
}
