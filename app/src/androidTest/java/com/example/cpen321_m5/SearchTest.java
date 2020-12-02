package com.example.cpen321_m5;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.pressImeActionButton;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class SearchTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void t1() {
        ViewInteraction appCompatImageView = onView(
                allOf(withId(R.id.search_image)));
        appCompatImageView.perform(click());
        ViewInteraction view = onView(
                allOf(withContentDescription("Google Map"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.map),
                                        0),
                                0),
                        isDisplayed()));
        view.check(matches(isDisplayed()));
        ViewInteraction spinner = onView(
                allOf(withId(R.id.search_loc_spi),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout),
                                        childAtPosition(
                                                withId(R.id.wholepage),
                                                0)),
                                1),
                        isDisplayed()));
        spinner.perform(click());
        DataInteraction checkedTextView = onData(anything())
                .inAdapterView(childAtPosition(
                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
                        0))
                .atPosition(1);
        checkedTextView.perform(click());
        ViewInteraction textView = onView(
                allOf(withId(android.R.id.text1), withText("Totem Park"),
                        childAtPosition(
                                allOf(withId(R.id.search_loc_spi),
                                        childAtPosition(
                                                withId(R.id.linearLayout),
                                                1)),
                                0),
                        isDisplayed()));
        textView.check(matches(withText("Totem Park")));
        ViewInteraction spinner2 = onView(
                allOf(withId(R.id.search_types_spi),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout),
                                        childAtPosition(
                                                withId(R.id.wholepage),
                                                0)),
                                3),
                        isDisplayed()));
        spinner2.perform(click());
        DataInteraction checkedTextView2 = onData(anything())
                .inAdapterView(childAtPosition(
                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
                        0))
                .atPosition(5);
        checkedTextView2.perform(click());
        ViewInteraction textView2 = onView(
                allOf(withId(android.R.id.text1), withText("Three Bedroom Suit"),
                        childAtPosition(
                                allOf(withId(R.id.search_types_spi),
                                        childAtPosition(
                                                withId(R.id.linearLayout),
                                                3)),
                                0),
                        isDisplayed()));
        textView2.check(matches(withText("Three Bedroom Suit")));
        half();
    }

    private void half() {
        ViewInteraction editText = onView(
                allOf(withId(R.id.search_price_edi),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout),
                                        childAtPosition(
                                                withId(R.id.wholepage),
                                                0)),
                                5),
                        isDisplayed()));
        editText.perform(replaceText("1234"), closeSoftKeyboard());
        ViewInteraction editText2 = onView(
                allOf(withId(R.id.search_price_edi), withText("1234"),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout),
                                        childAtPosition(
                                                withId(R.id.wholepage),
                                                0)),
                                5),
                        isDisplayed()));
        editText2.perform(pressImeActionButton());
        ViewInteraction editText3 = onView(
                allOf(withId(R.id.search_price_edi), withText("1234"),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout),
                                        childAtPosition(
                                                withId(R.id.wholepage),
                                                0)),
                                5),
                        isDisplayed()));
        editText3.check(matches(withText("1234")));
        ViewInteraction editText4 = onView(
                allOf(withId(R.id.search_price_edi), withText("1234"),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout),
                                        childAtPosition(
                                                withId(R.id.wholepage),
                                                0)),
                                5),
                        isDisplayed()));
        editText4.perform(replaceText("1234"));
        ViewInteraction editText5 = onView(
                allOf(withId(R.id.search_price_edi), withText("1234"),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout),
                                        childAtPosition(
                                                withId(R.id.wholepage),
                                                0)),
                                5),
                        isDisplayed()));
        editText5.perform(closeSoftKeyboard());
        ViewInteraction editText6 = onView(
                allOf(withId(R.id.search_price_edi), withText("1234"),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout),
                                        childAtPosition(
                                                withId(R.id.wholepage),
                                                0)),
                                5),
                        isDisplayed()));
        editText6.perform(pressImeActionButton());
        ViewInteraction seekBar = onView(
                allOf(withId(R.id.seekBar),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout),
                                        childAtPosition(
                                                withId(R.id.wholepage),
                                                0)),
                                6),
                        isDisplayed()));
        seekBar.check(matches(isDisplayed()));
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
