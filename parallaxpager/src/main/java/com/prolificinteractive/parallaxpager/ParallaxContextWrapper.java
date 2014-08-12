package com.prolificinteractive.parallaxpager;

import android.content.Context;
import android.content.ContextWrapper;
import android.view.LayoutInflater;

public class ParallaxContextWrapper extends ContextWrapper {

  private LayoutInflater inflater;

  public ParallaxContextWrapper(Context base) {
    super(base);
  }

  @Override
  public Object getSystemService(String name) {
    if (LAYOUT_INFLATER_SERVICE.equals(name)) {
      if (inflater == null) {
        inflater = new ParallaxLayoutInflater(LayoutInflater.from(getBaseContext()), this);
      }
      return inflater;
    }
    return super.getSystemService(name);
  }
}
