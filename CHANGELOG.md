Change Log
==========
Version 3.0.0 *(2018-5-9)*
----------------------------
 #### Updated
 * Gradle has been updated
 * ReadMe has been updated
 * The project has been moved to the Jitpack repository.
   These changes allow the user to add the library to a project in only two steps.
   1. Add the following to the root build.gradle list of repositories
   ```
   maven { url 'https://jitpack.io' }
   ```
   2. Add the dependency
   ```
   implementation 'com.github.User:Repo:Tag'
   ```

Version 2.2.0 *(2015-02-23)*
----------------------------

#### Added
 * Added `override_visibility` attribute to allow for overriding the default visibility behaviour.
   When false, it will act as it has; true will not change the visibility of the View at all,
   leaving it to the implementer.


Version 2.1.0 *(2015-02-19)*
----------------------------

#### Added
 * `ParallaxContainer.setOnPageChangeListener()` to access page change events of the
   underlying ViewPager. This means that `ParallaxContainer.attachOnPageChangeListener()` is now
   deprecated. You should use the listener setter from above.
 * `ParallaxContainer.getViewPager()` to allow access to the underlying ViewPager for
   existing code that relies on having a ViewPager instance.


Version 2.0.0 *(2015-02-17)*
----------------------------

 * Release should be mostly backwards compatible with v1.0.0, but the sweeping changes to the
   subsystem merited a major bump.
 * Bugfix: Fix targeting API v21
 
 #### Added
 * New: Ability to add custom listeners to the View creation process.
   This is to help work around an issue with trying to have multiple ContextWrappers meddle with
   the view creation process. See sample project for Calligraphy example.


Version 1.0.0
-------------

 * Start of ChangeLog
