package com.example.climaapp;

import android.app.Activity;
import android.app.Instrumentation;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.rule.ActivityTestRule;
import android.view.View;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.pressImeActionButton;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class WheatherControllerTest {

    @Rule
    //Launching WheatherController activity before executing any of the tests
    public ActivityTestRule<WheatherController> wheatherControllerActivityTestRule =
            new ActivityTestRule<>(WheatherController.class);

    //Observe the state of ChangeCityController activity:
    Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(ChangeCityController.class.getName(),
            null, false);
    Instrumentation.ActivityMonitor monitor2 = getInstrumentation().addMonitor(WheatherController.class.getName(),
            null, false);

    private WheatherController wheatherController;

    @Before
    public void setUp() throws Exception {
        wheatherController = wheatherControllerActivityTestRule.getActivity();
    }

    @Test
    public void testLaunchViews() throws Exception {
        View view = wheatherController.findViewById(R.id.changeCityButton);
        View view1 = wheatherController.findViewById(R.id.locationTV);
        View view2 = wheatherController.findViewById(R.id.tempTV);
        View view3 = wheatherController.findViewById(R.id.weatherSymbol);

        Assert.assertNotNull(view);
        Assert.assertNotNull(view1);
        Assert.assertNotNull(view2);
        Assert.assertNotNull(view3);
    }

//    @Test
//    public void testLaunchingChangeCityControllerActivity() throws Exception {
//        onView(withId(R.id.changeCityButton)).perform(click());
//        Activity ChangeCityActivity = getInstrumentation().waitForMonitorWithTimeout(monitor, 3000);
//        Assert.assertNotNull(ChangeCityActivity);
//    }

    @Test
    public void testEnteringTextIntoEditText() throws Exception {
            //Click the button:
            onView(withId(R.id.changeCityButton)).perform(click());
            //Adding name of city:
            onView(withId(R.id.queryET)).perform(typeText("Warsaw")).perform(pressImeActionButton());
            Activity WheatherControllerActivity = getInstrumentation().waitForMonitorWithTimeout(monitor2, 5000);
            Assert.assertNotNull(WheatherControllerActivity);
            //Assert.assertNull(WheatherControllerActivity);
            //onView(withText("Warsaw")).check(ViewAssertions.matches(isDisplayed()));
            Thread.sleep(5000);
            onView(withId(R.id.locationTV)).check(ViewAssertions.matches(withText("Warsaw")));
    }

    @After
    public void tearDown() throws Exception {
    }
}