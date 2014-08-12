package com.prolificinteractive.parallaxpager;

import android.content.Context;
import android.view.LayoutInflater;

public class ParallaxLayoutInflater extends LayoutInflater {

  protected ParallaxLayoutInflater(Context context) {
    super(context);
    setUpLayoutFactory();
  }

  protected ParallaxLayoutInflater(LayoutInflater original, Context newContext) {
    super(original, newContext);
    setUpLayoutFactory();
  }

  private void setUpLayoutFactory() {
    if (!(getFactory() instanceof ParallaxFactory)) {
      setFactory(new ParallaxFactory(getFactory()));
    }
  }

  @Override
  public LayoutInflater cloneInContext(Context newContext) {
    return new ParallaxLayoutInflater(this, newContext);
  }
}