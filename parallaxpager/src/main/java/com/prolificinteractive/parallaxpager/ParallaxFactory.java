package com.prolificinteractive.parallaxpager;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import java.util.Arrays;
import java.util.List;

public class ParallaxFactory {

  private final List<OnViewCreatedListener> otherListeners;

  public ParallaxFactory(OnViewCreatedListener... others) {
    otherListeners = Arrays.asList(others);
  }

  /**
   * Handle the created view
   *
   * @param view nullable.
   * @param context shouldn't be null.
   * @param attrs shouldn't be null.
   * @return null if null is passed in.
   */

  public View onViewCreated(View view, Context context, AttributeSet attrs) {
    if (view == null) {
      return null;
    }

    view = onViewCreatedInternal(view, context, attrs);
    for (OnViewCreatedListener listener : otherListeners) {
      if (listener != null) {
        view = listener.onViewCreated(view, context, attrs);
      }
    }
    return view;
  }

  private View onViewCreatedInternal(View view, final Context context, AttributeSet attrs) {

    int[] attrIds =
        { R.attr.a_in, R.attr.a_out, R.attr.x_in, R.attr.x_out, R.attr.y_in, R.attr.y_out, R.attr.override_visibility };

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
        tag.overrideVisibility = a.getBoolean(6, false);
        view.setTag(R.id.parallax_view_tag, tag);
      }
      a.recycle();
    }

    return view;
  }
}
