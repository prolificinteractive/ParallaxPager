The Parallax View
=================

Add some depth to your Android scrolling.

-- a gif goes here --

Installation
============

There are 3 important steps:

1. Wrap the Activity Context

2. Attach the Fragment Manager

3. Include the `ParallaxContainer` in layout XML

1. Wrap the Activity Context
----------------------------

Add this to the relevant Activity:

>@Override
protected void attachBaseContext(Context newBase) {
    super.attachBaseContext(new CalligraphyContextWrapper(newBase));
}

2. Attach the FragmentManager
-----------------------------

In `onCreate` in the relevant Activity, add this line:

> ParallaxUtil.attachFragmentManager(getSupportFragmentManager());

3. Include the `ParallaxContainer` in layout XML
------------------------------------------------

In your layout XML, ensure this line is added to the Root Element:

> xmlns:app="http://schemas.android.com/apk/res-auto"

Then, add a View of type `com.prolific.parallaxview.ParallaxContainer`

Within this container, put all the views that you wish to give parallax properties.

*The container will consider each of its direct children to, in turn, hold all views necessary for a single page. If you want several views to be on the same page, they need to be all wrapped in a single view.*

><com.prolific.parallaxview.ParallaxContainer
>      android:layout_width="match_parent"
>      android:layout_height="match_parent">
>
>    <ViewForPage1>
>    	...
>    </ViewForPage1>
>    
>    <ViewForPage2>
>    	...
>    </ViewForPage2>
>    
>    ...
>
></com.prolific.parallaxview.ParallaxContainer>

Lastly, assign the parallax properties. Here are the current choices:

`app:x_in`

`app:x_out`

`app:y_in`

`app:y_out`

`app:fade_in`

`app:fade_out`

A boolean

*Example:*

<ViewForPage1>
	android:layout_width="wrap_content"
	android:layout_height="wrap_content"
	app:x_in="2.5"
	app:x_out="2.5"
	app:y_in="1.25"
	app:y_out="1.25"
	app:fade_in="true"
	app:fade_out="true"
</ViewForPage1>

See the sample project for another example of this.

Download
--------

Download [the latest JAR](http://www.prolificinteractive.com) or grab the dependency in Gradle:

>compile 'com.prolific:parallaxview:(insert latest version)'

Coming Soon
-----------

Working on:

* an XML attribute to set the ViewPager to loop forever or to stop when the last page is reached.

As Seen on TV
-------------

Production apps currently using this in Google Play:

[Automatic](https://play.google.com/store/apps/details?id=com.automatic)

License
-------

>Copyright 2014 Prolific Interactive
>
>Licensed under the Apache License, Version 2.0 (the "License");
>you may not use this file except in compliance with the License.
>You may obtain a copy of the License at
>
>   http://www.apache.org/licenses/LICENSE-2.0
>
>Unless required by applicable law or agreed to in writing, software
>distributed under the License is distributed on an "AS IS" BASIS,
>WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
>See the License for the specific language governing permissions and
>limitations under the License.

Questions?
==========

For questions/feedback on this library, visit [the Prolific blog](http://www.prolificinteractive.com)
