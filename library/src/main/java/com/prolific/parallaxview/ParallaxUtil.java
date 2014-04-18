package com.prolific.parallaxview;

import android.support.v4.app.FragmentManager;

public class ParallaxUtil {
  protected static FragmentManager mManager;

  public static void attachFragmentManager(FragmentManager manager) {
    mManager = manager;
  }
}
