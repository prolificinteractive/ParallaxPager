package com.prolificinteractive.parallaxpager;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/**
 * A listener for view creation
 */
public interface OnViewCreatedListener {
  /**
   * Called when a View is created in the LayoutInflater
   * @param view View created
   * @param context Context
   * @param attrs View attributes
   * @return The same view that is passed in. This is mostly to match up with Calligraphy
   */
  View onViewCreated(View view, Context context, AttributeSet attrs);
}
