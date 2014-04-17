package com.prolific.parallaxview;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import java.util.ArrayList;
import java.util.List;

public class ParallaxContainer extends FrameLayout implements ViewPager.OnPageChangeListener {

  protected int mChildCount;
  protected int mCurrentChild;
  protected List<View> mParallaxViewList = new ArrayList<View>();
  protected ViewPager mViewPager;
  protected SpaceFragment mSpaceFragment1;
  protected SpaceFragment mSpaceFragment2;
  protected int mScreenWidth;

  public ParallaxContainer(Context context) {
    super(context);
    init(context, null);
  }

  public ParallaxContainer(Context context, AttributeSet attrs) {
    super(context, attrs);
    init(context, attrs);
  }

  public ParallaxContainer(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    init(context, attrs);
  }

  private void init(Context context, AttributeSet attrs) {

    // make view pager with same attributes as container
    mViewPager = new ViewPager(context, attrs);
    mViewPager.setOnPageChangeListener(this);

    // two empty fragments
    mSpaceFragment1 = new SpaceFragment();
    mSpaceFragment2 = new SpaceFragment();

    // how many steps before the viewpager loops
    mChildCount = getChildCount();

    // how wide is the screen
    mScreenWidth = context.getResources().getDisplayMetrics().widthPixels;

    addChildrenToParallaxViewList();

    addView(mViewPager);
  }

  // top-level children only
  private void addChildrenToParallaxViewList() {
    for (mCurrentChild = 0; mCurrentChild < mChildCount; mCurrentChild++) {
      View view = getChildAt(mCurrentChild);

      if (view instanceof ViewGroup) {
        addGrandChildrenToParallaxViewList((ViewGroup) view);
      } else {
        addViewToParallaxViewList(view);
      }
    }
  }

  // recursively add all children UNDER top-level
  private void addGrandChildrenToParallaxViewList(ViewGroup viewGroup) {
    int count = viewGroup.getChildCount();
    for (int i = 0; i < count; i++) {
      View view = getChildAt(i);
      if (view instanceof ViewGroup) {
        addGrandChildrenToParallaxViewList((ViewGroup) view);
      } else {
        addViewToParallaxViewList(view);
      }
    }
  }

  // attach attributes in tag
  private void addViewToParallaxViewList(View view) {
    ParallaxViewTag tag = (ParallaxViewTag) view.getTag(R.id.TAG_ID);
    tag.position = mCurrentChild;
    mParallaxViewList.add(view);
  }

  @Override public void onPageScrolled(int position, float offSet, int offsetPixels) {

    position = position % mChildCount;

    for (View view : mParallaxViewList) {
      applyParallaxEffects(view, position, offsetPixels);
    }
  }

  @Override public void onPageSelected(int position) {
    // recycle whichever fragment isn't active
    if (position % 2 == 1) {
      mSpaceFragment2 = new SpaceFragment();
    } else {
      mSpaceFragment1 = new SpaceFragment();
    }
  }

  @Override public void onPageScrollStateChanged(int i) {}

  private void applyParallaxEffects(View view, int position, float offsetPixels) {

    ParallaxViewTag tag = (ParallaxViewTag) view.getTag(R.id.TAG_ID);

    if (position == tag.position - 1 || position == tag.position + (mChildCount-1)) {

      // make visible
      setVisibility(VISIBLE);

      // slide in from right
      setTranslationX((mScreenWidth - offsetPixels) * tag.xIn);

      // slide in from top
      setTranslationY(0 - (mScreenWidth - offsetPixels) * tag.yIn);

      // fade in
      if (tag.fadeIn) {
        setAlpha(1.0f - (mScreenWidth - offsetPixels) / mScreenWidth);
      }
    } else if (position == tag.position) {

      // make visible
      setVisibility(VISIBLE);

      // slide out to left
      setTranslationX(0 - offsetPixels * tag.xOut);

      // slide out to top
      setTranslationY(0 - offsetPixels * tag.yOut);

      // fade out
      if (tag.fadeOut) {
        setAlpha(1.0f - offsetPixels / mScreenWidth);
      }
    } else {

      // remove
      setVisibility(GONE);
    }
  }
}
