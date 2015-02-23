package com.prolificinteractive.parallaxpager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import java.util.ArrayList;
import java.util.List;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

@SuppressWarnings("UnusedDeclaration")
public class ParallaxContainer extends FrameLayout implements ViewPager.OnPageChangeListener {

  private List<View> parallaxViews = new ArrayList<>();
  private ViewPager viewPager;
  private int pageCount = 0;
  private int containerWidth;
  private boolean isLooping = false;
  private final ParallaxPagerAdapter adapter;
  private ViewPager.OnPageChangeListener pageChangeListener;

  public ParallaxContainer(Context context) {
    this(context, null);
  }

  public ParallaxContainer(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public ParallaxContainer(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);

    adapter = new ParallaxPagerAdapter(context);
  }

  @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    containerWidth = getMeasuredWidth();
  }

  public void setLooping(boolean looping) {
    isLooping = looping;
    updateAdapterCount();
  }

  private void updateAdapterCount() {
    adapter.setCount(isLooping ? Integer.MAX_VALUE : pageCount);
  }

  public void setupChildren(int... childIds) {
    setupChildren(LayoutInflater.from(getContext()), childIds);
  }

  public void setupChildren(LayoutInflater inflater, int... childIds) {
    if (getChildCount() > 0) {
      throw new RuntimeException(
          "setupChildren should only be called once when ParallaxContainer is empty");
    }

    if (childIds.length == 1) {
      int id = childIds[0];
      childIds = new int[2];
      childIds[0] = id;
      childIds[1] = id;
    }

    for (int childId : childIds) {
      inflater.inflate(childId, this);
    }

    // hold pageCount because it will change after we add viewpager
    pageCount = getChildCount();
    for (int i = 0; i < pageCount; i++) {
      View view = getChildAt(i);
      addParallaxView(view, i);
    }

    updateAdapterCount();

    // make view pager with same attributes as container
    viewPager = new ViewPager(getContext());
    viewPager.setLayoutParams(new LayoutParams(MATCH_PARENT, MATCH_PARENT));
    viewPager.setId(R.id.parallax_pager);
    viewPager.setAdapter(adapter);
    attachOnPageChangeListener(viewPager, this);

    addView(viewPager, 0);
  }

  /**
   * Sets the {@link ViewPager.OnPageChangeListener} to the embedded {@link ViewPager}
   * created by the container.
   *
   * This method can be overridden to add an page indicator to the parallax view. If
   * this method is overriden, make sure that the listener methods are called on this
   * class as well.
   */
  @Deprecated
  protected void attachOnPageChangeListener(ViewPager viewPager,
      ViewPager.OnPageChangeListener listener) {
    viewPager.setOnPageChangeListener(listener);
  }

  // attach attributes in tag
  private void addParallaxView(View view, int pageIndex) {
    if (view instanceof ViewGroup) {
      // recurse children
      ViewGroup viewGroup = (ViewGroup) view;
      for (int i = 0, childCount = viewGroup.getChildCount(); i < childCount; i++) {
        addParallaxView(viewGroup.getChildAt(i), pageIndex);
      }
    }

    ParallaxViewTag tag = (ParallaxViewTag) view.getTag(R.id.parallax_view_tag);
    if (tag != null) {
      // only track view if it has a parallax tag
      tag.index = pageIndex;
      parallaxViews.add(view);
    }
  }

  /**
   * <b>NOTE:</b> this is exposed for use with existing code which requires a {@linkplain android.support.v4.view.ViewPager} instance.
   * Please make sure that if you call methods like {@linkplain android.support.v4.view.ViewPager#setAdapter(android.support.v4.view.PagerAdapter) setAdapter()}
   * or {@linkplain android.support.v4.view.ViewPager#setOnPageChangeListener(android.support.v4.view.ViewPager.OnPageChangeListener) setOnPageChangeListener()}
   * on the instance returned, that you do so with forethought and good reason.
   *
   * @return the internal ViewPager, null before {@linkplain #setupChildren(int...) setupChildren()} is called
   */
  public ViewPager getViewPager() {
    return viewPager;
  }

  /**
   * Set a listener to recieve page change events
   * @see android.support.v4.view.ViewPager#setOnPageChangeListener(android.support.v4.view.ViewPager.OnPageChangeListener)
   * @param pageChangeListener the listener, or null to clear
   */
  public void setOnPageChangeListener(ViewPager.OnPageChangeListener pageChangeListener) {
    this.pageChangeListener = pageChangeListener;
  }

  @Override public void onPageScrolled(int pageIndex, float offset, int offsetPixels) {
    if (pageCount > 0) {
      pageIndex = pageIndex % pageCount;
    }

    ParallaxViewTag tag;
    for (View view : parallaxViews) {
      tag = (ParallaxViewTag) view.getTag(R.id.parallax_view_tag);
      if (tag == null) { continue; }

      if ((pageIndex == tag.index - 1
          || (isLooping && (pageIndex == tag.index - 1 + pageCount)))
          && containerWidth != 0) {

        if (!tag.overrideVisibility) {
          // make visible
          view.setVisibility(VISIBLE);
        }

        // slide in from right
        view.setTranslationX((containerWidth - offsetPixels) * tag.xIn);

        // slide in from top
        view.setTranslationY(0 - (containerWidth - offsetPixels) * tag.yIn);

        // fade in
        view.setAlpha(1.0f - (containerWidth - offsetPixels) * tag.alphaIn / containerWidth);
      } else if (pageIndex == tag.index) {
        if (!tag.overrideVisibility) {
          // make visible
          view.setVisibility(VISIBLE);
        }

        // slide out to left
        view.setTranslationX(0 - offsetPixels * tag.xOut);

        // slide out to top
        view.setTranslationY(0 - offsetPixels * tag.yOut);

        // fade out
        view.setAlpha(1.0f - offsetPixels * tag.alphaOut / containerWidth);
      } else {
        if (!tag.overrideVisibility) {
          view.setVisibility(GONE);
        }
      }
    }

    if (pageChangeListener != null) {
      pageChangeListener.onPageScrolled(pageIndex, offset, offsetPixels);
    }
  }

  @Override public void onPageSelected(int position) {
    if (pageChangeListener != null) {
      pageChangeListener.onPageSelected(position);
    }
  }

  @Override public void onPageScrollStateChanged(int i) {
    if (pageChangeListener != null) {
      pageChangeListener.onPageScrollStateChanged(i);
    }
  }
}
