package com.prolificinteractive.parallaxproject;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.prolificinteractive.parallaxpager.ParallaxContextWrapper;

import uk.co.chrisjenx.calligraphy.OpenCalligraphyFactory;

public class ParallaxActivity extends AppCompatActivity {

  @Override protected void attachBaseContext(Context newBase) {
    //ParallaxPager and Calligraphy don't seem to play nicely together
    //The solution was to add a listener for view creation events so that we can hook up
    // Calligraphy to our view creation calls instead.
    super.attachBaseContext(
        new ParallaxContextWrapper(newBase, new OpenCalligraphyFactory())
    );
  }

  ////Normal Usage
  //@Override protected void attachBaseContext(Context newBase) {
  //  super.attachBaseContext(new ParallaxContextWrapper(newBase));
  //}

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_parallax);

    if (savedInstanceState == null) {
      getSupportFragmentManager().beginTransaction()
          .add(R.id.content, new ParallaxFragment())
          .commit();
    }
  }
}
