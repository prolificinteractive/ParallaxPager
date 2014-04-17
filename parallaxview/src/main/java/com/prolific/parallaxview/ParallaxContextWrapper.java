package com.prolific.parallaxview;

import android.content.Context;
import android.content.ContextWrapper;
import android.view.LayoutInflater;

public class ParallaxContextWrapper extends ContextWrapper {

  private LayoutInflater mInflater;

  private final int mXInAttributeId;
  private final int mXOutAttributeId;
  private final int mYInAttributeId;
  private final int mYOutAttributeId;
  private final int mFadeInAttributeId;
  private final int mFadeOutAttributeId;

  public ParallaxContextWrapper(Context base, int xIn, int xOut, int yIn, int yOut, int fadeIn, int fadeOut) {
    super(base);
    mXInAttributeId = xIn;
    mXOutAttributeId = xOut;
    mYInAttributeId = yIn;
    mYOutAttributeId = yOut;
    mFadeInAttributeId = fadeIn;
    mFadeOutAttributeId = fadeOut;
  }

  @Override
  public Object getSystemService(String name) {
    if (LAYOUT_INFLATER_SERVICE.equals(name)) {
      if (mInflater == null) {
        mInflater = new ParallaxLayoutInflater(LayoutInflater.from(getBaseContext()),
            this,
            mXInAttributeId,
            mXOutAttributeId,
            mYInAttributeId,
            mYOutAttributeId,
            mFadeInAttributeId,
            mFadeOutAttributeId);
      }
      return mInflater;
    }
    return super.getSystemService(name);
  }
}