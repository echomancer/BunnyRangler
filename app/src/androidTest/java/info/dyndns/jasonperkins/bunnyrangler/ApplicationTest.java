package info.dyndns.jasonperkins.bunnyrangler;

import android.app.Application;
import android.support.test.runner.AndroidJUnit4;
import android.test.ApplicationTestCase;
import android.test.suitebuilder.annotation.LargeTest;
import android.util.Log;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.test.ActivityInstrumentationTestCase2;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.Matchers.hasToString;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.core.IsNot.not;


import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.app.Activity;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.LargeTest;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
/*
@RunWith(AndroidJUnit4.class)
@LargeTest
public class ApplicationTest {

    public static final String STRING_TO_BE_TYPED = "Espresso";

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class);

    @Test
    public void changeText_sameActivity() {
        // Type text and then press the button.
        onView(withId(R.id.edit_text))
                .perform(typeText(STRING_TO_BE_TYPED), closeSoftKeyboard());
        onView(withId(R.id.add_bunny)).perform(click());

        // Check that the text was changed.
        onView(withId(R.id.listView)).check(matches(withText(STRING_TO_BE_TYPED)));
    }
}
*/

// Old version
@RunWith(AndroidJUnit4.class)
@LargeTest
public class ApplicationTest {
/*
public class ApplicationTest extends ActivityInstrumentationTestCase2<MainActivity> {
    public ApplicationTest() {
        super(MainActivity.class);
    }
*/
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class);

    String newBunny = String.valueOf(R.string.test_bunny_name);
    String logTag = "appTest";

    @Test
    public void canAddBunnyTest(){

        Log.i(logTag, "Add Bunny Test");
        addBunny(newBunny);

        onView(withId(R.id.listView))
                .check(matches(withText(newBunny)));

        removeBunny(newBunny);
    }

    @Test
    public void canRemoveBunnyTest(){

        Log.i(logTag, "Simple Remove Bunny Test");
        addBunny(newBunny);
        removeBunny(newBunny);

        onView(withId(R.id.listView))
                .check(matches(withText(not(containsString(newBunny)))));
    }

    @Test
    public void canRemoveCorrectBunnyTest(){

        Log.i(logTag, "Add 2 Bunnies Remove 1 Test");
        String otherBunny = "dog";
        addBunny(otherBunny);
        addBunny(newBunny);
        removeBunny(newBunny);

        onView(withId(R.id.listView))
                .check(matches(withText(not(containsString(newBunny)))));

        removeBunny(otherBunny);
    }

    // Function to add a bunny via espresso
    public void addBunny(String bun){
        onView(withId(R.id.edit_text))      // Find the edit
                .perform(typeText(bun));    // Type the name

        onView(withId(R.id.add_bunny))      // Find the add button
                .perform(click());          // Add that beautiful bean footage
    }

    // Function to remove a bunny via espresso
    public void removeBunny(String bun){
        onData(hasToString(endsWith(bun)))
                .inAdapterView(withId(R.id.listView))
                .perform(click());
    }
}
