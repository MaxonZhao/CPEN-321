package com.example.cpen321_m5;

import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;


@RunWith(AndroidJUnit4.class)
        @LargeTest

public class ChatTest {

    @Rule
    public ActivityScenarioRule<Chat> activityRule =
            new ActivityScenarioRule<>(Chat.class);
    @Rule
    public IntentsTestRule<Chat> intentsTestRule =
            new IntentsTestRule<>(Chat.class);

//    @Test
//    public void onCreate() {
//        Espresso.onView(withId(R.id.enterBtn))
//                .perform(click())
//                .check(matches((isEnabled())));
//    }

    @Test
    public void onStart() {
        // check if enter button is displayed on the screen
        onView(withId(R.id.enterBtn)).check(matches(isDisplayed()));
        onView(withId(R.id.enterBtn))
//                .perform(click())
                .check(matches((isEnabled())));
    }

    @Test
    public void enterRoomTest() {

        onView(withId(R.id.editText))
                .perform(typeText("Maxon"), closeSoftKeyboard());
        onView(withId(R.id.editText)).check(matches(withText("Maxon")));
        onView(withId(R.id.enterBtn)).perform(click());
        intended(hasComponent(ChatRoom.class.getName()));
        onView(withId(R.id.messageEdit)).check(matches(isDisplayed()));
        onView(withId(R.id.pickImgBtn)).check(matches(isDisplayed()));
        onView(withId(R.id.recyclerView_e)).check(matches(isDisplayed()));
    }

}
