package com.prolificinteractive.parallaxproject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import com.prolificinteractive.parallaxpager.ParallaxContainer;

public class ParallaxFragment extends Fragment implements ViewPager.OnPageChangeListener {

  ImageView mEarthImageView;

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {

    View view = inflater.inflate(R.layout.fragment_parallax, container, false);

    // find the parallax container
    ParallaxContainer parallaxContainer =
        (ParallaxContainer) view.findViewById(R.id.parallax_container);

    // specify whether pager will loop
    parallaxContainer.setLooping(true);

    // wrap the inflater and inflate children with custom attributes
    parallaxContainer.setupChildren(inflater,
        R.layout.parallax_view_1,
        R.layout.parallax_view_2,
        R.layout.parallax_view_3,
        R.layout.parallax_view_4);

    // optionally set a ViewPager.OnPageChangeListener
    parallaxContainer.setOnPageChangeListener(this);

    view.findViewById(R.id.btn_new_frag).setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        getFragmentManager().beginTransaction()
            .replace(R.id.content, new Fragment())
            .addToBackStack("test")
            .commit();
      }
    });

    // Earth ImageView has `override_visibility` set to `true` so we need to manually manage visibility
    mEarthImageView = (ImageView) view.findViewById(R.id.imageview_earth);

    return view;
  }

  @Override public void onPageScrolled(int position, float offset, int offsetPixels) {
    // example of manually setting view visibility
    if (position == 1 && offset > 0.2) {
      // just before leaving the screen, Earth will disappear
      mEarthImageView.setVisibility(View.INVISIBLE);
    } else if (position == 0 || position == 1) {
      mEarthImageView.setVisibility(View.VISIBLE);
    } else {
      mEarthImageView.setVisibility(View.GONE);
    }
  }

  @Override public void onPageSelected(int position) {
    Toast.makeText(getActivity(), "Page Selected: " + position, Toast.LENGTH_SHORT).show();
  }

  @Override public void onPageScrollStateChanged(int state) {

  }
}
