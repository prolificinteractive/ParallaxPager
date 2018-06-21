<img src="/bg_parallax_pager.png"/>

# Parallax Pager
[![Android Arsenal](https://img.shields.io/badge/Android%20Parallax-Pager-blue.svg?style=true)](https://android-arsenal.com/details/1/537) 
[![Release](https://jitpack.io/v/prolificinteractive/ParallaxPager.svg)

Add some depth to your Android scrolling using the parallax effect!

## Installation

Step 1. Add the JitPack repository to your build file

```groovy
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}
```

Step 2. Add the dependency

```groovy
dependencies {
  implementation 'com.github.prolificinteractive:parallaxpager:${parallaxpagerVersion}'
}
```

## Usage


There are 4 important steps:

1. Use a `ParallaxContainer` in layout XML

2. Create a layout XML file for each page

3. Wrap the Activity Context

4. Add the attachment code to `onCreate` of your Activity or `onCreateView` of your Fragment


## 1. Use a `ParallaxContainer` in layout XML

Use the class `com.prolificinteractive.parallaxpager.ParallaxContainer` in your layout XML, sizing it however you like.

Ex:

```xml
<com.prolificinteractive.parallaxpager.ParallaxContainer
      android:id="@+id/parallax_container_1"
      android:layout_width="match_parent"
      android:layout_height="match_parent"/>
```


## 2. Create a layout XML file for each page

Each page must have its own layout XML file. Use whichever Layouts or Views you like, as usual.

Ensure this line is added to the Root Element:

>xmlns:app="http://schemas.android.com/apk/res-auto"

Assign any combination of the following attributes (all floats):

* `x_in`: as the View **enters** the screen, it will translate in the horizontal direction along with user swiping, at a rate multiplied by this value. Default is `0`.

* `x_out`: as the View **leaves** the screen, it will translate in the horizontal direction along with user swiping, at a rate multiplied by this value. Default is `0`.

* `y_in`: as the View **enters** the screen, it will translate **downward** as the user swipes right to left, at a rate multiplied by this value. Default is `0`.

* `y_out`: as the View **leaves** the screen, it will translate **upward** as the user swipes right to left, at a rate multiplied by this value. Default is `0`.

* `a_in`: as the View **enters** the screen, it will **fade in** as the user swipes right to left, at a rate multiplied by this value. Default is `0`.

* `a_out`: as the View **leaves** the screen, it will **fade out** as the user swipes right to left, at a rate multiplied by this value. Default is `0`.

Ex:

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical">

  <TextView
      android:layout_width="wrap_content"
      android:layout_height="wrap_content"
      app:x_in="@dimen/parallax_speed_medium"
      app:x_out="@dimen/parallax_speed_fast"
      app:y_in="@dimen/parallax_speed_medium_rev"
      app:y_out="@dimen/parallax_speed_fast"
      app:a_in="@dimen/parallax_speed_very_fast"
      app:a_out="@dimen/parallax_speed_very_fast"
      android:text="@string/text_1"
      />

  <TextView
      android:layout_width="wrap_content"
      android:layout_height="wrap_content"
      app:x_in="@dimen/parallax_speed_medium_rev"
      app:x_out="@dimen/parallax_speed_fast"
      app:y_in="@dimen/parallax_speed_medium"
      app:y_out="@dimen/parallax_speed_fast_rev"
      app:a_in="@dimen/parallax_speed_very_fast"
      app:a_out="@dimen/parallax_speed_very_fast"
      android:text="@string/text_2"
      />
</LinearLayout>
```

Keep in mind that negative values mean a change in direction for translation effects, and have no effect for alpha. For translation effects, values between `0` and `1` will result in a high level of funkiness.


## 3. Wrap the Activity Context
Wrap the activity context using `com.prolificinteractive.parallaxpager.ParallaxContextWrapper` in your activity.

Ex:

```java
  @Override
  protected void attachBaseContext(Context newBase) {
    super.attachBaseContext(new ParallaxContextWrapper(newBase));
  }
```

**Note**: If you are using this in conjunction with another library that wraps Context, it doesn't appear to like chaining them together.
Instead, we've added the ability to hook into the View creation process to use with other libraries.
The sample project shows how to hook into Calligraphy.

Ex:

```java
  @Override
  protected void attachBaseContext(Context newBase) {
    super.attachBaseContext(
        new ParallaxContextWrapper(newBase, new OnViewCreatedListener() {
          @Override public View onViewCreated(View view, Context context, AttributeSet attrs) {
            //Setup view as needed
            return view; //Return the view passed in
          }
        })
    );
  }
```


## 4. Add the attachment code to `onCreate` of your Activity or `onCreateView` of your Fragment

Important steps in `onCreate` of an Activity (or `onCreateView` of a Fragment):

* Find the parallax container by ID

* Specify whether the pager should loop (`true` means it *will* loop)

* Submit a **Layout Inflater** and list the layouts for each page (in order). Currently there must be at least 2 in this list (repeats allowed).

Ex:

```java
// find the parallax container
ParallaxContainer parallaxContainer = (ParallaxContainer) findViewById(R.id.parallax_container);

// specify whether pager will loop
parallaxContainer.setLooping(true);

// wrap the inflater and inflate children with custom attributes
parallaxContainer.setupChildren(getLayoutInflater(),
    R.layout.parallax_view_1,
    R.layout.parallax_view_2,
    R.layout.parallax_view_3,
    R.layout.parallax_view_4);
```

Extras
======

## Extra 1. Setting a `ViewPager.OnPageChangeListener`

You can set a `ViewPager.OnPageChangeListener` after the attachment code in step 4.

```java
// optionally set a ViewPager.OnPageChangeListener
parallaxContainer.setOnPageChangeListener(this);
```

## Extra 2. `ViewPager` access

You have access to the `ViewPager` by calling:

```java
parallaxContainer.getViewPager();
```

This is exposed for use with existing code which requires a `ViewPager` instance. Please make sure that if you call methods like `setAdapter` or `setOnPageChangeListener` on the instance returned, that you do so with forethought and good reason.

## Extra 3. Overriding parallax visibility

Parallax views will be `VISIBLE` when onscreen, and `GONE` when offscreen. If you need to override this behavior, set this attribute on your View in XML:

```xml
app:override_visibility="true"
```


## Contributing

To report a bug or enhancement request, feel free to file an issue under the respective heading. If you wish to contribute to the project, fork this repo and submit a pull request. 


Code contributions should follow the standards specified in the [Prolific Android Code Style](https://github.com/prolificinteractive/android-code-styles).


## License

![prolific](https://s3.amazonaws.com/prolificsitestaging/logos/Prolific_Logo_Full_Color.png)

Copyright (c) 2018 Prolific Interactive

Parallax Pager is maintained and sponsored by Prolific Interactive. It may be redistributed under the terms specified in the [LICENSE] file.

[LICENSE]: ./LICENSE
