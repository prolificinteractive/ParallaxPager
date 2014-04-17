package com.prolific.parallaxview;

import android.content.Context;
import android.content.ContextWrapper;
import android.view.LayoutInflater;

public class ParallaxContextWrapper extends ContextWrapper {

  private LayoutInflater mInflater;

  public ParallaxContextWrapper(Context base) {
    super(base);
  }

  @Override
  public Object getSystemService(String name) {
    if (LAYOUT_INFLATER_SERVICE.equals(name)) {
      if (mInflater == null) {
        mInflater = new ParallaxLayoutInflater(LayoutInflater.from(getBaseContext()), this);
      }
      return mInflater;
    }
    return super.getSystemService(name);
  }
}