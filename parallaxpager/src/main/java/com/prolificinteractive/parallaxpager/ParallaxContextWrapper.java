package com.prolificinteractive.parallaxpager;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

public class ParallaxContextWrapper extends ContextWrapper {

  private final OnViewCreatedListener customListener;
  private LayoutInflater inflater;

  private final OnViewCreatedListener INTERNAL_LISTENER = new OnViewCreatedListener() {
    @Override public View onViewCreated(View view, Context context, AttributeSet attrs) {
      int[] attrIds =
          { R.attr.a_in, R.attr.a_out, R.attr.x_in, R.attr.x_out, R.attr.y_in, R.attr.y_out, };

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

      if(customListener != null) {
        view = customListener.onViewCreated(view, context, attrs);
      }

      return view;
    }
  };

  public ParallaxContextWrapper(Context base, OnViewCreatedListener onViewCreatedListener) {
    super(base);
    this.customListener = onViewCreatedListener;
  }

  public ParallaxContextWrapper(Context base) {
    this(base, null);
  }

  @Override
  public Object getSystemService(String name) {
    if (LAYOUT_INFLATER_SERVICE.equals(name)) {
      if (inflater == null) {
        inflater = new ParallaxLayoutInflater(
            LayoutInflater.from(getBaseContext()),
            this,
            INTERNAL_LISTENER
        );
      }
      return inflater;
    }
    return super.getSystemService(name);
  }
}
