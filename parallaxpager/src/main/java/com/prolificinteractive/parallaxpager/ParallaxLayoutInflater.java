package com.prolificinteractive.parallaxpager;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.lang.reflect.Method;
import org.xmlpull.v1.XmlPullParser;

public class ParallaxLayoutInflater extends LayoutInflater {

  private final LayoutInflater fallback;
  private final OnViewCreatedListener postView;
  private boolean mSetPrivateFactory = false;

  protected ParallaxLayoutInflater(LayoutInflater original, Context newContext, OnViewCreatedListener postView) {
    super(original, newContext);
    this.fallback = original;
    this.postView = postView;
    setUpLayoutFactory();
  }

  private void setUpLayoutFactory() {
      if (getFactory2() != null && !(getFactory2() instanceof ParallaxFactory)) {
        // Sets both Factory/Factory2
        setFactory2(getFactory2());
      }

    // We can do this as setFactory2 is used for both methods.
    if (getFactory() != null && !(getFactory() instanceof ParallaxFactory)) {
      setFactory(getFactory());
    }
  }

  @Override
  public void setFactory(Factory factory) {
    // Only set our factory and wrap calls to the Factory trying to be set!
    if (!(factory instanceof ParallaxFactory)) {
      super.setFactory(new ParallaxFactory(factory, fallback));
    } else {
      super.setFactory(factory);
    }
  }

  @Override
  @TargetApi(Build.VERSION_CODES.HONEYCOMB)
  public void setFactory2(Factory2 factory2) {
    // Only set our factory and wrap calls to the Factory2 trying to be set!
    if (!(factory2 instanceof ParallaxFactory)) {
      super.setFactory2(new ParallaxFactory(factory2, fallback));
    } else {
      super.setFactory2(factory2);
    }
  }

  @Override
  public View inflate(XmlPullParser parser, ViewGroup root, boolean attachToRoot) {
    setPrivateFactoryInternal();
    return super.inflate(parser, root, attachToRoot);
  }

  private void setPrivateFactoryInternal() {
    // Already tried to set the factory.
    if (mSetPrivateFactory) return;
    // Skip if not attached to an activity.
    if (!(getContext() instanceof Factory2)) {
      mSetPrivateFactory = true;
      return;
    }

    final Method setPrivateFactoryMethod = ReflectionUtils
        .getMethod(LayoutInflater.class, "setPrivateFactory");

    if (setPrivateFactoryMethod != null) {
      ReflectionUtils.invokeMethod(this,
          setPrivateFactoryMethod,
          new ParallaxFactory(getFactory2(), fallback)
      );
    }
    mSetPrivateFactory = true;
  }

  @Override
  public LayoutInflater cloneInContext(Context newContext) {
    return new ParallaxLayoutInflater(this, newContext, postView);
  }

  @Override protected View onCreateView(String name, AttributeSet attrs) throws ClassNotFoundException {

    // This mimics the {@code PhoneLayoutInflater} in the way it tries to inflate the base
    // classes, if this fails its pretty certain the app will fail at this point.
    View view = null;
    for (String prefix : sClassPrefixList) {
      try {
        view = super.createView(name, prefix, attrs);
      } catch (ClassNotFoundException ignored) {
      }
    }
    // In this case we want to let the base class take a crack
    // at it.
    if (view == null) {
      view = super.onCreateView(name, attrs);
    }

    postView.onViewCreated(view, getContext(), attrs);

    return view;
  }

  @Override protected View onCreateView(View parent, String name, AttributeSet attrs) throws ClassNotFoundException {
    View view = super.onCreateView(parent, name, attrs);
    postView.onViewCreated(super.onCreateView(parent, name, attrs), getContext(), attrs);
    return view;
  }

  static final String[] sClassPrefixList = {
      "android.widget.",
      "android.webkit.",
      "android.view."
  };

  private class ParallaxFactory implements Factory2 {

    private final Factory factory;

    private final LayoutInflater fallback;

    public ParallaxFactory(Factory factory, LayoutInflater fallback) {
      this.factory = factory;
      this.fallback = fallback;
    }

    @Override public View onCreateView(String name, Context context, AttributeSet attrs) {
      return onCreateView(null, name, context, attrs);
    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
      View view = null;

      if (factory instanceof Factory2) {
        view = ((Factory2) factory).onCreateView(parent, name, context, attrs);
      }

      if (factory != null && view == null) {
        view = factory.onCreateView(name, context, attrs);
      }

      if (view == null) {
        view = createViewOrFailQuietly(name, context, attrs);
      }

      if (view != null) {
        postView.onViewCreated(view, context, attrs);
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
        return fallback.createView(name, prefix, attrs);
      } catch (Exception ignore) {
        return null;
      }
    }
  }
}