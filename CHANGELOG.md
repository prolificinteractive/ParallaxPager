Change Log
==========

Version 2.2.1 *(2015-10-21)*
----------------------------

 * Fix: Exception caused by support library also supplying a custom LayoutInflater


Version 2.2.0 *(2015-02-23)*
----------------------------

 * New: added `override_visibility` attribute to allow for overriding the default visibility behaviour.
   When false, it will act as it has; true will not change the visibility of the View at all,
   leaving it to the implementer.


Version 2.1.0 *(2015-02-19)*
----------------------------

 * New: `ParallaxContainer.setOnPageChangeListener()` to access page change events of the
   underlying ViewPager. This means that `ParallaxContainer.attachOnPageChangeListener()` is now
   deprecated. You should use the listener setter from above.
 * New: `ParallaxContainer.getViewPager()` to allow access to the underlying ViewPager for
   existing code that relies on having a ViewPager instance.


Version 2.0.0 *(2015-02-17)*
----------------------------

 * Release should be mostly backwards compatible with v1.0.0, but the sweeping changes to the
   subsystem merited a major bump.
 * Bugfix: Fix targeting API v21
 * New: Ability to add custom listeners to the View creation process.
   This is to help work around an issue with trying to have multiple ContextWrappers meddle with
   the view creation process. See sample project for Calligraphy example.


Version 1.0.0
-------------

 * Start of ChangeLog
