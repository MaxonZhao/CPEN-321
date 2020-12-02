package com.example.cpen321_m5;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.rule.GrantPermissionRule;
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
public class PostTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Rule
    public GrantPermissionRule mGrantPermissionRule =
            GrantPermissionRule.grant(
                    "android.permission.READ_EXTERNAL_STORAGE");

    @Test
    public void t2() {
        ViewInteraction bottomNavigationItemView = onView(
                allOf(withId(R.id.nav_post), withContentDescription("Post"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.bottomNavigationView),
                                        0),
                                1),
                        isDisplayed()));
        bottomNavigationItemView.perform(click());

        ViewInteraction textView = onView(
                allOf(withId(R.id.pricetxt), withText("Price"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.scrollView3),
                                        0),
                                0),
                        isDisplayed()));
        textView.check(matches(withText("Price")));

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.editprice),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.scrollView3),
                                        0),
                                1)));
        appCompatEditText.perform(scrollTo(), replaceText("1234"), closeSoftKeyboard());

        ViewInteraction editText = onView(
                allOf(withId(R.id.editprice), withText("1234"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.scrollView3),
                                        0),
                                1),
                        isDisplayed()));
        editText.check(matches(withText("1234")));

        ViewInteraction textView2 = onView(
                allOf(withId(android.R.id.text1), withText("Place Vanier"),
                        childAtPosition(
                                allOf(withId(R.id.location_spi),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                                3)),
                                0),
                        isDisplayed()));
        textView2.check(matches(withText("Place Vanier")));

        ViewInteraction appCompatSpinner = onView(
                allOf(withId(R.id.location_spi),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.scrollView3),
                                        0),
                                3)));
        appCompatSpinner.perform(scrollTo(), click());
        partone();
        parttwo();
        partthree();
    }

    private void partone() {
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

        ViewInteraction textView4 = onView(
                allOf(withId(android.R.id.text1), withText("Shared Room"),
                        childAtPosition(
                                allOf(withId(R.id.types_spi),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                                5)),
                                0),
                        isDisplayed()));
        textView4.check(matches(withText("Shared Room")));

        ViewInteraction appCompatSpinner2 = onView(
                allOf(withId(R.id.types_spi),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.scrollView3),
                                        0),
                                5)));
        appCompatSpinner2.perform(scrollTo(), click());

        DataInteraction appCompatTextView2 = onData(anything())
                .inAdapterView(childAtPosition(
                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
                        0))
                .atPosition(1);
        appCompatTextView2.perform(click());

        ViewInteraction textView5 = onView(
                allOf(withId(android.R.id.text1), withText("Connect Single Room"),
                        childAtPosition(
                                allOf(withId(R.id.types_spi),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                                5)),
                                0),
                        isDisplayed()));
        textView5.check(matches(withText("Connect Single Room")));
    }

    private void partthree() {
        ViewInteraction imageView = onView(
                allOf(withId(R.id.imageView_post),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.scrollView3),
                                        0),
                                10),
                        isDisplayed()));
        imageView.check(matches(isDisplayed()));


    }

    private void parttwo() {
        ViewInteraction bottomNavigationItemView = onView(
                allOf(withId(R.id.nav_post), withContentDescription("Post"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.bottomNavigationView),
                                        0),
                                1),
                        isDisplayed()));
        bottomNavigationItemView.perform(click());

        ViewInteraction textInputEditText = onView(
                allOf(withId(R.id.editphonetie)));
        textInputEditText.perform(scrollTo(), replaceText("12345678910"), closeSoftKeyboard());

        ViewInteraction editText = onView(
                allOf(withId(R.id.editphonetie), withText("12345678910")));
        editText.check(matches(withText("12345678910")));

        ViewInteraction textInputEditText2 = onView(
                allOf(withId(R.id.editphonetie), withText("12345678910")));
        textInputEditText2.perform(scrollTo(), replaceText("1234567891"));

        ViewInteraction textInputEditText3 = onView(
                allOf(withId(R.id.editphonetie), withText("1234567891")));
        textInputEditText3.perform(closeSoftKeyboard());

        ViewInteraction editText2 = onView(
                allOf(withId(R.id.editphonetie), withText("1234567891")));
        editText2.check(matches(withText("1234567891")));




        ViewInteraction textInputEditText4 = onView(
                allOf(withId(R.id.editemailtie)));
        textInputEditText4.perform(scrollTo(), replaceText("wangshengwalter@gmail.com"), closeSoftKeyboard());

        ViewInteraction editText3 = onView(
                allOf(withId(R.id.editemailtie), withText("wangshengwalter@gmail.com")));
        editText3.check(matches(withText("wangshengwalter@gmail.com")));




        ViewInteraction textInputEditText5 = onView(
                allOf(withId(R.id.editdescriptie)));
        textInputEditText5.perform(scrollTo(), replaceText("test ..."), closeSoftKeyboard());

        ViewInteraction editText4 = onView(
                allOf(withId(R.id.editdescriptie), withText("test ...")));
        editText4.check(matches(withText("test ...")));
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
