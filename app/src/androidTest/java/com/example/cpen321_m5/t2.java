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
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
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
public class t2 {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void t2() {
        ViewInteraction bottomNavigationItemView = onView(
                allOf(withId(R.id.nav_post), withContentDescription("Post")));
        bottomNavigationItemView.perform(click());

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.editprice),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                1)));
        appCompatEditText.perform(scrollTo(), replaceText("1234"), closeSoftKeyboard());

        ViewInteraction editText = onView(
                allOf(withId(R.id.editprice), withText("1234"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.ScrollView.class),
                                        0),
                                1),
                        isDisplayed()));
        editText.check(matches(withText("1234")));

        ViewInteraction textView = onView(
                allOf(withId(android.R.id.text1), withText("Place Vanier"),
                        childAtPosition(
                                allOf(withId(R.id.location_spi),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                                3)),
                                0),
                        isDisplayed()));
        textView.check(matches(withText("Place Vanier")));

        ViewInteraction textView2 = onView(
                allOf(withId(android.R.id.text1), withText("Shared Room"),
                        childAtPosition(
                                allOf(withId(R.id.types_spi),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                                5)),
                                0),
                        isDisplayed()));
        textView2.check(matches(withText("Shared Room")));

        ViewInteraction appCompatSpinner = onView(
                allOf(withId(R.id.location_spi),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        appCompatSpinner.perform(scrollTo(), click());

        DataInteraction appCompatTextView = onData(anything())
                .inAdapterView(childAtPosition(
                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
                        0))
                .atPosition(1);
        appCompatTextView.perform(click());

        ViewInteraction textView3 = onView(
                allOf(withId(android.R.id.text1), withText("Totem Park"),
                        childAtPosition(
                                allOf(withId(R.id.location_spi),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                                3)),
                                0),
                        isDisplayed()));
        textView3.check(matches(withText("Totem Park")));

        ViewInteraction appCompatSpinner2 = onView(
                allOf(withId(R.id.types_spi),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                5)));
        appCompatSpinner2.perform(scrollTo(), click());

        DataInteraction appCompatTextView2 = onData(anything())
                .inAdapterView(childAtPosition(
                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
                        0))
                .atPosition(3);
        appCompatTextView2.perform(click());

        ViewInteraction textView4 = onView(
                allOf(withId(android.R.id.text1), withText("Six Bedroom Suite"),
                        childAtPosition(
                                allOf(withId(R.id.types_spi),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                                5)),
                                0),
                        isDisplayed()));
        textView4.check(matches(withText("Six Bedroom Suite")));

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.submit), withText("Submit"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                9)));
        appCompatButton.perform(scrollTo(), click());

        ViewInteraction bottomNavigationItemView1 = onView(
                allOf(withId(R.id.nav_post), withContentDescription("Post")));
        bottomNavigationItemView1.perform(click());

        ViewInteraction appCompatEditText1 = onView(
                allOf(withId(R.id.editprice),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                1)));
        appCompatEditText1.perform(scrollTo(), replaceText("1234"), closeSoftKeyboard());

        ViewInteraction editText1 = onView(
                allOf(withId(R.id.editprice), withText("1234"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.ScrollView.class),
                                        0),
                                1),
                        isDisplayed()));
        editText1.check(matches(withText("1234")));

        ViewInteraction textInputEditText = onView(
                allOf(withId(R.id.editphonetie)));
        textInputEditText.perform(scrollTo(), replaceText("2368670720"), closeSoftKeyboard());

        ViewInteraction editText2 = onView(
                allOf(withId(R.id.editphonetie), withText("2368670720")));
        editText2.check(matches(withText("2368670720")));


        ViewInteraction textInputEditText2 = onView(
                allOf(withId(R.id.editemailtie)));
        textInputEditText2.perform(scrollTo(), replaceText("wangshengwalter@gmail.com"), closeSoftKeyboard());

        ViewInteraction editText3 = onView(
                allOf(withText("wangshengwalter@gmail.com")));
        editText3.check(matches(withText("wangshengwalter@gmail.com")));


        ViewInteraction textInputEditText3 = onView(
                withId(R.id.editdescriptie));
        textInputEditText3.perform(scrollTo(), replaceText("test pass..."), closeSoftKeyboard());

        ViewInteraction editText4 = onView(
                allOf(withText("test pass...")));
        editText4.check(matches(withText("test pass...")));


        ViewInteraction textInputEditText4 = onView(
                allOf(withId(R.id.editphonetie), withText("2368670720")));
        textInputEditText4.perform(scrollTo(), replaceText("23686707201"));

        ViewInteraction textInputEditText5 = onView(
                allOf(withId(R.id.editphonetie), withText("23686707201")));
        textInputEditText5.perform(closeSoftKeyboard());

        ViewInteraction editText5 = onView(
                allOf(withId(R.id.editphonetie), withText("23686707201")));
        editText5.check(matches(withText("23686707201")));

        ViewInteraction textInputEditText6 = onView(
                allOf(withId(R.id.editphonetie), withText("23686707201")));
        textInputEditText6.perform(scrollTo(), replaceText("2368670720"));

        ViewInteraction textInputEditText7 = onView(
                allOf(withId(R.id.editphonetie), withText("2368670720")));
        textInputEditText7.perform(closeSoftKeyboard());

        ViewInteraction editText6 = onView(
                allOf(withId(R.id.editphonetie), withText("2368670720")));
        editText6.check(matches(withText("2368670720")));

        ViewInteraction appCompatButton1 = onView(
                allOf(withId(R.id.submit), withText("Submit"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                9)));
        appCompatButton1.perform(scrollTo(), click());

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
