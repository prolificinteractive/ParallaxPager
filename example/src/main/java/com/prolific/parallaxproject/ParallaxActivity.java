package com.prolific.parallaxproject;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import com.prolific.parallaxview.ParallaxContainer;

public class ParallaxActivity extends FragmentActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_parallax);

    ParallaxContainer parallaxContainer = (ParallaxContainer) findViewById(R.id.parallax_container_1);

    int[] parallaxLayoutIds = {
        R.layout.parallax_view_1, R.layout.parallax_view_2, R.layout.parallax_view_3
    };

    if (parallaxContainer != null) {
      parallaxContainer.attachManager(getSupportFragmentManager());
      parallaxContainer.setupChildren(getLayoutInflater(), parallaxLayoutIds);
    }
  }
}
