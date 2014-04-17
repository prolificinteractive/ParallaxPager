package com.prolific.parallaxview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

public class ParallaxFactory implements LayoutInflater.Factory {

  private final LayoutInflater.Factory factory;

  public ParallaxFactory(LayoutInflater.Factory factory) {

    this.factory = factory;
  }

  @Override
  public View onCreateView(String name, Context context, AttributeSet attrs) {
    View view = null;

    if (context instanceof LayoutInflater.Factory) {
      view = ((LayoutInflater.Factory) context).onCreateView(name, context, attrs);
    }

    if (factory != null && view == null) {
      view = factory.onCreateView(name, context, attrs);
    }

    if (view == null) {
      view = createViewOrFailQuietly(name, context, attrs);
    }

    if (view != null) {
      onViewCreated(view, name, context, attrs);
    }

    return view;
  }

  protected View createViewOrFailQuietly(String name, Context context, AttributeSet attrs) {
    if (name.contains(".")) {
      return createViewOrFailQuietly(name, null, context, attrs);
    }

    return null;
  }

  protected View createViewOrFailQuietly(String name, String prefix, Context context, AttributeSet attrs) {
    try {
      return LayoutInflater.from(context).createView(name, prefix, attrs);
    } catch (Exception ignore) {
      return null;
    }
  }

  protected void onViewCreated(View view, String name, Context context, AttributeSet attrs) {

    int[] attrIds = {R.attr.x_in, R.attr.x_out, R.attr.y_in, R.attr.y_out, R.attr.fade_in, R.attr.fade_out};

    TypedArray a = context.obtainStyledAttributes(attrs, attrIds);

    ParallaxViewTag parallaxViewTag = new ParallaxViewTag();

    parallaxViewTag.xIn = a.getFloat(0, 0.0f);
    parallaxViewTag.xOut = a.getFloat(1, 0.0f);
    parallaxViewTag.yIn = a.getFloat(2, 0.0f);
    parallaxViewTag.yOut = a.getFloat(3, 0.0f);
    parallaxViewTag.fadeIn = a.getBoolean(4, false);
    parallaxViewTag.fadeOut = a.getBoolean(5, false);

    a.recycle();

    view.setTag(R.id.TAG_ID, parallaxViewTag);
  }
}