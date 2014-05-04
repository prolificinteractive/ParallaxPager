package com.prolificinteractive.parallaxpager;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

public class ParallaxFactory implements LayoutInflater.Factory {

  private final LayoutInflater.Factory factory;
  private ParallaxLayoutInflater mInflater;

  private static final String[] sClassPrefixList = {
      "android.widget.",
      "android.webkit.",
      "android.view."
  };

  public ParallaxFactory(ParallaxLayoutInflater inflater, LayoutInflater.Factory factory) {
    mInflater = inflater;
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
      onViewCreated(view, context, attrs);
    }

    return view;
  }

  protected View createViewOrFailQuietly(String name, Context context, AttributeSet attrs) {
    if (name.contains(".")) {
      return createViewOrFailQuietly(name, null, context, attrs);
    }

    for (final String prefix : sClassPrefixList) {
      final View view = createViewOrFailQuietly(name, prefix, context, attrs);

      if (view != null) {
        return view;
      }
    }

    return null;
  }

  protected View createViewOrFailQuietly(String name, String prefix, Context context,
      AttributeSet attrs) {
    try {
      return mInflater.createView(name, prefix, attrs);
    } catch (Exception ignore) {
      return null;
    }
  }

  protected void onViewCreated(View view, Context context, AttributeSet attrs) {

    int[] attrIds =
        { R.attr.a_in, R.attr.a_out, R.attr.x_in, R.attr.x_out, R.attr.y_in, R.attr.y_out,  };

    TypedArray a = context.obtainStyledAttributes(attrs, attrIds);

    if (a != null) {
      if (a.length() > 0) {
        ParallaxViewTag tag = new ParallaxViewTag();
        tag.alphaIn = a.getFloat(0, 0f);
        tag.alphaOut = a.getFloat(1, 0f);
        tag.xIn = a.getFloat(2, 0f);
        tag.xOut = a.getFloat(3, 0f);
        tag.yIn = a.getFloat(4, 0f);
        tag.yOut = a.getFloat(5, 0f);
        view.setTag(R.id.parallax_view_tag, tag);
      }
      a.recycle();
    }
  }
}