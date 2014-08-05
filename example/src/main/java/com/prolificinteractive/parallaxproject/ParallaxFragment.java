package com.prolificinteractive.parallaxproject;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.prolificinteractive.parallaxpager.ParallaxContainer;

public class ParallaxFragment extends Fragment {

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {

    View view = inflater.inflate(R.layout.fragment_parallax, container, false);

    // find the parallax container
    ParallaxContainer parallaxContainer = (ParallaxContainer) view.findViewById(
        R.id.parallax_container);

    if (parallaxContainer != null) {
      // specify whether pager will loop
      parallaxContainer.setLooping(true);

      // wrap the inflater and inflate children with custom attributes
      parallaxContainer.setupChildren(inflater,
          R.layout.parallax_view_1,
          R.layout.parallax_view_2,
          R.layout.parallax_view_3,
          R.layout.parallax_view_4);
    }

    view.findViewById(R.id.btn_new_frag).setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        getFragmentManager().beginTransaction()
            .replace(android.R.id.content, new Fragment())
            .addToBackStack("test")
            .commit();
      }
    });

    return view;
  }
}
