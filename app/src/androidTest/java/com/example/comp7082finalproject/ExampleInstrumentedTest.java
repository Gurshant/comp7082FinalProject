package com.example.comp7082finalproject;

import android.content.Context;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.comp7082finalproject.View.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ExampleInstrumentedTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void searchActivityFunctions() throws IOException {
        DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String title = "Pullups";

        onView(withId(R.id.fab)).perform(click());
        onView(withId(R.id.editTextTitle)).perform(clearText());
        onView(withId(R.id.editTextTitle)).perform(typeText(title), closeSoftKeyboard());
        onView(withId(R.id.button_save)).perform(click());
        onView(withId(R.id.textView_title)).check(matches(withText("title")));
        onView(withId(R.id.textView_count)).check(matches(withText("" + 0)));

//        onView(withId(R.id.etFromDateTime)).perform(clearText());
//        onView(withId(R.id.etToDateTime)).perform(clearText());
//        onView(withId(R.id.etFromDateTime)).perform(typeText(nowS), closeSoftKeyboard());
//        onView(withId(R.id.etToDateTime)).perform(typeText(afterS), closeSoftKeyboard());
//
//        onView(withId(R.id.etKeywords)).perform(typeText("myCaption"), closeSoftKeyboard());
//        onView(withId(R.id.go)).perform(click());
//        String path = file.getAbsolutePath();
//        onView(withId(R.id.Gallery)).check(matches(ImageViewSameFilenameMatcher.matchesImage(path)));
//
//        onView(withId(R.id.favorite)).perform(click());
//        onView(withId(R.id.Captions)).check(matches(withText("myCaption")));
//        onView(withId(R.id.RightButton)).perform(click());
//        onView(withId(R.id.Gallery)).check(matches(ImageViewSameFilenameMatcher.matchesImage(path)));
//        onView(withId(R.id.LeftButton)).perform(click());
//        onView(withId(R.id.Gallery)).check(matches(ImageViewSameFilenameMatcher.matchesImage(path)));
//        onView(withId(R.id.remove_pic)).perform(click());
    }

}
