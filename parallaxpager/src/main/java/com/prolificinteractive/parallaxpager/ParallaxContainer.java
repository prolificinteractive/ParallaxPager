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

  public ParallaxContainer(Context context) {
    super(context);
    adapter = new ParallaxPagerAdapter(context);
  }

  public ParallaxContainer(Context context, AttributeSet attrs) {
    super(context, attrs);
    adapter = new ParallaxPagerAdapter(context);
  }

  public ParallaxContainer(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    adapter = new ParallaxPagerAdapter(context);
  }

  @Override
  public void onWindowFocusChanged(boolean hasFocus) {
    containerWidth = getMeasuredWidth();
    if (viewPager != null) {
      onPageScrolled(viewPager.getCurrentItem(), 0, 0);
    }
    super.onWindowFocusChanged(hasFocus);
  }

  public void setLooping (boolean looping) {
    isLooping = looping;
    updateAdapterCount();
  }

  private void updateAdapterCount() {
    adapter.setCount(isLooping ? Integer.MAX_VALUE : pageCount);
  }

  public void setupChildren (LayoutInflater inflater, int... childIds) {
    if (getChildCount() > 0) {
      throw new RuntimeException(
          "setupChildren should only be called once when ParallaxContainer is empty");
    }

    ParallaxLayoutInflater parallaxLayoutInflater =
        new ParallaxLayoutInflater(inflater, getContext());

    if (childIds.length == 1) {
      int id = childIds[0];
      childIds = new int[2];
      childIds[0] = id;
      childIds[1] = id;
    }

    for (int childId : childIds) {
      parallaxLayoutInflater.inflate(childId, this);
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
    viewPager.setOnPageChangeListener(this);
    viewPager.setId(R.id.parallax_pager);
    viewPager.setAdapter(adapter);
    addView(viewPager, 0);
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

        // make visible
        view.setVisibility(VISIBLE);

        // slide in from right
        view.setTranslationX((containerWidth - offsetPixels) * tag.xIn);

        // slide in from top
        view.setTranslationY(0 - (containerWidth - offsetPixels) * tag.yIn);

        // fade in
        view.setAlpha(1.0f - (containerWidth - offsetPixels) * tag.alphaIn / containerWidth);

      } else if (pageIndex == tag.index) {

        // make visible
        view.setVisibility(VISIBLE);

        // slide out to left
        view.setTranslationX(0 - offsetPixels * tag.xOut);

        // slide out to top
        view.setTranslationY(0 - offsetPixels * tag.yOut);

        // fade out
        view.setAlpha(1.0f - offsetPixels * tag.alphaOut / containerWidth);

      } else {
        view.setVisibility(GONE);
      }
    }
  }
  @Override public void onPageSelected(int position) {}
  @Override public void onPageScrollStateChanged(int i) {}
}
