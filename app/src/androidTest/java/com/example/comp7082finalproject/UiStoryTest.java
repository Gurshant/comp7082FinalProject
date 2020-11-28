package com.example.comp7082finalproject;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import com.example.comp7082finalproject.R;
import com.example.comp7082finalproject.View.MainActivity;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class UiStoryTest {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityTestRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void uiStoryTest() {
        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.fab), withContentDescription("List of Counters"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_host_fragment),
                                        0),
                                2),
                        isDisplayed()));
        floatingActionButton.perform(click());

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.editTextTitle), withText("Ex: Squats"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_host_fragment),
                                        0),
                                2),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("pullups"));

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.editTextTitle), withText("pullups"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_host_fragment),
                                        0),
                                2),
                        isDisplayed()));
        appCompatEditText2.perform(closeSoftKeyboard());

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.button_save), withText("Save"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_host_fragment),
                                        0),
                                0),
                        isDisplayed()));
        appCompatButton.perform(click());

        ViewInteraction textView = onView(
                allOf(withId(R.id.textView_title), withText("pullups"),
                        withParent(withParent(withId(R.id.nav_host_fragment))),
                        isDisplayed()));
        textView.check(matches(withText("pullups")));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.textView_count), withText("0"),
                        withParent(withParent(withId(R.id.nav_host_fragment))),
                        isDisplayed()));
        textView2.check(matches(withText("0")));

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.button_add), withText("+"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_host_fragment),
                                        0),
                                5),
                        isDisplayed()));
        appCompatButton2.perform(click());

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.button_add), withText("+"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_host_fragment),
                                        0),
                                5),
                        isDisplayed()));
        appCompatButton3.perform(click());

        ViewInteraction textView4 = onView(
                allOf(withId(R.id.textView_count), withText("2"),
                        withParent(withParent(withId(R.id.nav_host_fragment))),
                        isDisplayed()));
        textView4.check(matches(withText("2")));

        ViewInteraction appCompatButton4 = onView(
                allOf(withId(R.id.button_subtract), withText("-"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_host_fragment),
                                        0),
                                4),
                        isDisplayed()));
        appCompatButton4.perform(click());

        ViewInteraction appCompatButton5 = onView(
                allOf(withId(R.id.button_subtract), withText("-"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_host_fragment),
                                        0),
                                4),
                        isDisplayed()));
        appCompatButton5.perform(click());

        ViewInteraction appCompatButton6 = onView(
                allOf(withId(R.id.button_subtract), withText("-"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_host_fragment),
                                        0),
                                4),
                        isDisplayed()));
        appCompatButton6.perform(click());

        ViewInteraction textView5 = onView(
                allOf(withId(R.id.textView_count), withText("-1"),
                        withParent(withParent(withId(R.id.nav_host_fragment))),
                        isDisplayed()));
        textView5.check(matches(withText("-1")));

        ViewInteraction appCompatButton7 = onView(
                allOf(withId(R.id.button_stats), withText("Statistics"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_host_fragment),
                                        0),
                                1),
                        isDisplayed()));
        appCompatButton7.perform(click());

        ViewInteraction textView6 = onView(
                allOf(withId(R.id.textViewTitle), withText("pullups"),
                        withParent(withParent(withId(R.id.nav_host_fragment))),
                        isDisplayed()));
        textView6.check(matches(withText("pullups")));

        ViewInteraction textView7 = onView(
                allOf(withId(R.id.textViewCount), withText("-1"),
                        withParent(withParent(withId(R.id.nav_host_fragment))),
                        isDisplayed()));
        textView7.check(matches(withText("-1")));

        ViewInteraction textView8 = onView(
                allOf(withId(R.id.textViewMinVal), withText("-1"),
                        withParent(withParent(withId(R.id.nav_host_fragment))),
                        isDisplayed()));
        textView8.check(matches(withText("-1")));

        ViewInteraction textView9 = onView(
                allOf(withId(R.id.textViewMaxVal), withText("2"),
                        withParent(withParent(withId(R.id.nav_host_fragment))),
                        isDisplayed()));
        textView9.check(matches(withText("2")));

        ViewInteraction textView10 = onView(
                allOf(withId(R.id.textViewDailyTotalVal), withText("-1"),
                        withParent(withParent(withId(R.id.nav_host_fragment))),
                        isDisplayed()));
        textView10.check(matches(withText("-1")));
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
