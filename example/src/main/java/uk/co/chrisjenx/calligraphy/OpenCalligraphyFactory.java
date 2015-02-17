package uk.co.chrisjenx.calligraphy;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import com.prolificinteractive.parallaxpager.OnViewCreatedListener;

/**
 * {@linkplain uk.co.chrisjenx.calligraphy.CalligraphyFactory} is a package local class,
 * so in order to get access to the
 * {@linkplain #onViewCreated(android.view.View, android.content.Context, android.util.AttributeSet)}
 * we need to subclass this in the same package.
 * This also implements {@linkplain com.prolificinteractive.parallaxpager.OnViewCreatedListener} as
 * a convenience.
 */
public class OpenCalligraphyFactory extends CalligraphyFactory implements OnViewCreatedListener {

  public OpenCalligraphyFactory() {
    super(CalligraphyConfig.get().getAttrId());
  }

  public OpenCalligraphyFactory(int attributeId) {
    super(attributeId);
  }

  @Override
  public View onViewCreated(View view, Context context, AttributeSet attrs) {
    return super.onViewCreated(view, context, attrs);
  }

}
