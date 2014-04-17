package com.prolific.parallaxview;

import android.content.Context;
import android.view.LayoutInflater;

public class ParallaxLayoutInflater extends LayoutInflater {

  private final int mXInAttributeId;
  private final int mXOutAttributeId;
  private final int mYInAttributeId;
  private final int mYOutAttributeId;
  private final int mFadeInAttributeId;
  private final int mFadeOutAttributeId;

  protected ParallaxLayoutInflater(Context context, int xIn, int xOut, int yIn, int yOut, int fadeIn, int fadeOut) {
    super(context);
    mXInAttributeId = xIn;
    mXOutAttributeId = xOut;
    mYInAttributeId = yIn;
    mYOutAttributeId = yOut;
    mFadeInAttributeId = fadeIn;
    mFadeOutAttributeId = fadeOut;
    setUpLayoutFactory();
  }

  protected ParallaxLayoutInflater(LayoutInflater original, Context newContext, int xIn, int xOut, int yIn, int yOut, int fadeIn, int fadeOut) {
    super(original, newContext);
    mXInAttributeId = xIn;
    mXOutAttributeId = xOut;
    mYInAttributeId = yIn;
    mYOutAttributeId = yOut;
    mFadeInAttributeId = fadeIn;
    mFadeOutAttributeId = fadeOut;
    setUpLayoutFactory();
  }

  private void setUpLayoutFactory() {
    if (!(getFactory() instanceof ParallaxFactory)) {
      setFactory(new ParallaxFactory(getFactory(), mXInAttributeId, mXOutAttributeId, mYInAttributeId, mYOutAttributeId, mFadeInAttributeId, mFadeOutAttributeId));
    }
  }

  @Override
  public LayoutInflater cloneInContext(Context newContext) {
    return new ParallaxLayoutInflater(this, newContext, mXInAttributeId, mXOutAttributeId, mYInAttributeId, mYOutAttributeId, mFadeInAttributeId, mFadeOutAttributeId);
  }
}