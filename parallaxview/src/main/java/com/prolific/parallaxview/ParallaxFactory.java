package com.prolific.parallaxview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

public class ParallaxFactory implements LayoutInflater.Factory {
  //private static final String[] sClassPrefixList = {
  //    "android.widget.",
  //    "android.webkit."
  //};
  //private static final Map<Class<? extends TextView>, Integer> sStyles
  //    = new HashMap<Class<? extends TextView>, Integer>() {
  //  {
  //    put(TextView.class, android.R.attr.textViewStyle);
  //    put(Button.class, android.R.attr.buttonStyle);
  //    put(EditText.class, android.R.attr.editTextStyle);
  //    put(AutoCompleteTextView.class, android.R.attr.autoCompleteTextViewStyle);
  //    put(MultiAutoCompleteTextView.class, android.R.attr.autoCompleteTextViewStyle);
  //    put(CheckBox.class, android.R.attr.checkboxStyle);
  //    put(RadioButton.class, android.R.attr.radioButtonStyle);
  //    put(ToggleButton.class, android.R.attr.buttonStyleToggle);
  //  }
  //};


  private final LayoutInflater.Factory factory;
  private final int mXInAttributeId;
  private final int mXOutAttributeId;
  private final int mYInAttributeId;
  private final int mYOutAttributeId;
  private final int mFadeInAttributeId;
  private final int mFadeOutAttributeId;

  public ParallaxFactory(LayoutInflater.Factory factory, int xIn, int xOut,
      int yIn, int yOut, int fadeIn, int fadeOut) {

    this.factory = factory;
    mXInAttributeId = xIn;
    mXOutAttributeId = xOut;
    mYInAttributeId = yIn;
    mYOutAttributeId = yOut;
    mFadeInAttributeId = fadeIn;
    mFadeOutAttributeId = fadeOut;
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

    //for (final String prefix : sClassPrefixList) {
    //  final View view = createViewOrFailQuietly(name, prefix, context, attrs);
    //
    //  if (view != null) {
    //    return view;
    //  }
    //}

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

    int[] attrIds = {mXInAttributeId, mXOutAttributeId, mYInAttributeId, mYOutAttributeId, mFadeInAttributeId, mFadeOutAttributeId};

    TypedArray a = context.obtainStyledAttributes(attrs, attrIds);

    ParallaxViewTag parallaxViewTag = new ParallaxViewTag();

    parallaxViewTag.xIn = a.getFloat(android.R.styleable.x_in, 0.0f);
    parallaxViewTag.xOut = a.getFloat(android.R.styleable.x_out, 0.0f);
    parallaxViewTag.yIn = a.getFloat(android.R.styleable.y_in, 0.0f);
    parallaxViewTag.yOut = a.getFloat(android.R.styleable.y_out, 0.0f);
    parallaxViewTag.fadeIn = a.getBoolean(android.R.styleable.fade_in, false);
    parallaxViewTag.fadeOut = a.getBoolean(android.R.styleable.fade_out, false);

    a.recycle();

    view.setTag(ParallaxViewTag.TAG, parallaxViewTag);
  }
}