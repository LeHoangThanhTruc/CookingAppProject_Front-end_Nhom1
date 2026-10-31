package com.example.cookingapp;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import android.view.View;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

@RunWith(AndroidJUnit4.class)
public class ProfileIntegrationTest {
    private static final String TEST_TOKEN =
            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9."
                    + "eyJzdWIiOiIxIiwiZXhwIjo0MTAyNDQ0ODAwfQ."
                    + "btMCeFNlV2YpRaR-pmrOobzvfzTDhTzSYzT8eiAxseo";
    private static final String INITIAL_PROFILE = "{"
            + "\"id\":1,\"username\":\"panda\",\"email\":\"panda@example.com\","
            + "\"full_name\":\"Panda Bếp\",\"phone_number\":\"0901234567\","
            + "\"bio\":\"Yêu các món Việt\",\"avatar_url\":null}";
    private static final String UPDATED_PROFILE = "{"
            + "\"id\":1,\"username\":\"panda\",\"email\":\"panda@example.com\","
            + "\"full_name\":\"Bếp Nhà Panda\",\"phone_number\":\"+84901234567\","
            + "\"bio\":\"Món Việt mỗi ngày\",\"avatar_url\":null}";

    private MockWebServer server;

    @Before
    public void setUp() throws IOException {
        server = new MockWebServer();
        server.start(18080);
        SessionManager.saveAccessToken(
                InstrumentationRegistry.getInstrumentation().getTargetContext(),
                TEST_TOKEN
        );
    }

    @After
    public void tearDown() throws IOException {
        server.shutdown();
    }

    @Test
    public void profileLoadsUpdatesAndRefreshesMyProfileHeader() throws Exception {
        server.enqueue(jsonResponse(INITIAL_PROFILE));
        server.enqueue(jsonResponse(UPDATED_PROFILE));
        server.enqueue(jsonResponse(UPDATED_PROFILE));

        try (ActivityScenario<Frame26BioActivity> ignored =
                     ActivityScenario.launch(Frame26BioActivity.class)) {
            onView(isRoot()).perform(waitFor(withText("Panda Bếp"), 5_000));
            onView(withId(R.id.edtFullname)).perform(replaceText("Bếp Nhà Panda"));
            onView(withId(R.id.edtPhone)).perform(replaceText("+84901234567"));
            onView(withId(R.id.edtBio)).perform(replaceText("Món Việt mỗi ngày"));
            closeSoftKeyboard();
            onView(withId(R.id.btnSubmit)).perform(click());
            onView(isRoot()).perform(waitFor(withText("Bếp Nhà Panda"), 5_000));
            onView(withId(R.id.tvProfileUsername)).check(matches(withText("@panda")));
        }

        RecordedRequest getRequest = server.takeRequest();
        RecordedRequest patchRequest = server.takeRequest();
        RecordedRequest refreshRequest = server.takeRequest();

        assertEquals("/api/profile/me", getRequest.getPath());
        assertTrue(getRequest.getHeader("Authorization").startsWith("Bearer "));
        assertEquals("PATCH", patchRequest.getMethod());
        assertTrue(patchRequest.getBody().readUtf8().contains("Bếp Nhà Panda"));
        assertEquals("/api/profile/me", refreshRequest.getPath());
    }

    private static MockResponse jsonResponse(String body) {
        return new MockResponse()
                .setResponseCode(200)
                .setHeader("Content-Type", "application/json")
                .setBody(body);
    }

    private static ViewAction waitFor(Matcher<View> matcher, long timeoutMs) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isRoot();
            }

            @Override
            public String getDescription() {
                return "wait up to " + timeoutMs + " ms for " + matcher;
            }

            @Override
            public void perform(UiController uiController, View view) {
                long deadline = System.currentTimeMillis() + timeoutMs;
                do {
                    for (View child : androidx.test.espresso.util.TreeIterables
                            .breadthFirstViewTraversal(view)) {
                        if (matcher.matches(child)) {
                            return;
                        }
                    }
                    uiController.loopMainThreadForAtLeast(50);
                } while (System.currentTimeMillis() < deadline);
                throw new AssertionError(getDescription());
            }
        };
    }
}
