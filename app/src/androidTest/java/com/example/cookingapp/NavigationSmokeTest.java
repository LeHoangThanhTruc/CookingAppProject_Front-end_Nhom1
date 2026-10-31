package com.example.cookingapp;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.view.View;
import android.app.Activity;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.hamcrest.Matcher;

import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;

import androidx.test.platform.app.InstrumentationRegistry;

@RunWith(AndroidJUnit4.class)
public class NavigationSmokeTest {
    private static final String PROFILE_TEST_TOKEN =
            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9."
                    + "eyJzdWIiOiIxIiwiZXhwIjo0MTAyNDQ0ODAwfQ."
                    + "btMCeFNlV2YpRaR-pmrOobzvfzTDhTzSYzT8eiAxseo";

    @Before
    public void seedAuthenticatedProfileSession() {
        SessionManager.saveAccessToken(
                InstrumentationRegistry.getInstrumentation().getTargetContext(),
                PROFILE_TEST_TOKEN
        );
    }

    private static final ViewAction FORCE_CLICK = new ViewAction() {
        @Override
        public Matcher<View> getConstraints() {
            return isEnabled();
        }

        @Override
        public String getDescription() {
            return "click regardless of edge-to-edge clipping";
        }

        @Override
        public void perform(UiController uiController, View view) {
            view.performClick();
            uiController.loopMainThreadUntilIdle();
        }
    };

    @Test
    public void allRegisteredActivitiesInflate() {
        launchAndClose(LoginActivity.class);
        launchAndClose(RegisterActivity.class);
        launchAndClose(ForgotPasswordActivity.class);
        launchAndClose(ResetPasswordActivity.class);
        launchAndClose(ManHinhChinhActivity.class);
        launchAndClose(SearchActivity.class);
        launchAndClose(RecipeDetailActivity.class);
        launchAndClose(ReviewActivity.class);
        launchAndClose(MainActivity.class);
        launchAndClose(PlaceholderActivity.class);
        launchAndClose(Frame7SettingActivity.class);
        launchAndClose(Frame8Language.class);
        launchAndClose(Frame9Contactus.class);
        launchAndClose(Frame10Myactivity.class);
        launchAndClose(Frame11Dadanhgia.class);
        launchAndClose(Frame12FavoritesList.class);
        launchAndClose(Frame13CookingSchedule.class);
        launchAndClose(Frame14SavedList.class);
        launchAndClose(Frame15ProfileDetails.class);
        launchAndClose(Frame16ChangeAccountName.class);
        launchAndClose(Frame17ChangeGmailAddress.class);
        launchAndClose(Frame18EnterGmailOTP.class);
        launchAndClose(Frame19ChangePhoneNumber.class);
        launchAndClose(Frame20SelectAMethod.class);
        launchAndClose(Frame21OTPZalo.class);
        launchAndClose(Frame22OTPSDT.class);
        launchAndClose(Frame23EnterCurrentPassword.class);
        launchAndClose(Frame24OtherProfileActivity.class);
        launchAndClose(Frame25MyProfileActivity.class);
        launchAndClose(Frame26BioActivity.class);
        launchAndClose(Frame27FriendsActivity.class);
        launchAndClose(Frame28FollowingActivity.class);
        launchAndClose(Frame29MessagesActivity.class);
        launchAndClose(Frame30ChatDetailActivity.class);
        launchAndClose(Frame31CreateRecipeActivity.class);
        launchAndClose(Frame32PrivacyActivity.class);
        launchAndClose(Frame33NotificationsActivity.class);
        launchAndClose(Frame34OtherBioActivity.class);
    }

    private <T extends Activity> void launchAndClose(Class<T> activityClass) {
        try (ActivityScenario<T> ignored = ActivityScenario.launch(activityClass)) {
            // Launching and reaching RESUMED verifies manifest registration and layout inflation.
        }
    }

    @Test
    public void homeSearchRecipeReviewFlowWorks() {
        try (ActivityScenario<ManHinhChinhActivity> ignored = ActivityScenario.launch(ManHinhChinhActivity.class)) {
            onView(withId(R.id.btnQuickSearch)).perform(FORCE_CLICK);
            onView(withId(R.id.btnViewRecipe)).perform(FORCE_CLICK);
            onView(withId(R.id.btnWriteReview)).perform(FORCE_CLICK);
        }
    }

    @Test
    public void backNavigationKeepsAppUsable() {
        try (ActivityScenario<ManHinhChinhActivity> ignored = ActivityScenario.launch(ManHinhChinhActivity.class)) {
            onView(withId(R.id.btnQuickSearch)).perform(FORCE_CLICK);
            onView(withId(R.id.btnHome)).perform(FORCE_CLICK);
            onView(withId(R.id.btnQuickSearch)).perform(FORCE_CLICK);
            onView(withId(R.id.btnViewRecipe)).perform(FORCE_CLICK);
            pressBack();
            onView(withId(R.id.btnViewRecipe)).perform(FORCE_CLICK);
            onView(withId(R.id.btnBack)).perform(FORCE_CLICK);
            onView(withId(R.id.btnHome)).perform(FORCE_CLICK);
            onView(withId(R.id.btnQuickSettings)).perform(FORCE_CLICK);
            pressBack();
            onView(withId(R.id.btnQuickProfile)).perform(FORCE_CLICK);
        }
    }

    @Test
    public void settingsActivityLinksWork() {
        try (ActivityScenario<Frame7SettingActivity> ignored = ActivityScenario.launch(Frame7SettingActivity.class)) {
            onView(withId(R.id.btnActivity)).perform(FORCE_CLICK);
            onView(withId(R.id.btnSavedRecipes)).perform(click());
            onView(withId(R.id.btnScheduleCooking)).perform(click());
        }
    }

    @Test
    public void profileSocialLinksWork() {
        try (ActivityScenario<Frame25MyProfileActivity> ignored = ActivityScenario.launch(Frame25MyProfileActivity.class)) {
            onView(withId(R.id.menu_friends)).perform(scrollTo(), click());
            onView(withId(R.id.friend_an)).perform(click());
        }
        try (ActivityScenario<Frame24OtherProfileActivity> ignored = ActivityScenario.launch(Frame24OtherProfileActivity.class)) {
            onView(withId(R.id.menu_bio)).perform(scrollTo(), click());
            onView(withId(R.id.tvFullname)).check(matches(withText("Thảo Vy")));
            onView(withId(R.id.tvPhone)).check(matches(withText("0901 234 567")));
            onView(withId(R.id.tvEmail)).check(matches(withText("thaovy@example.com")));
            onView(withId(R.id.btnBack)).perform(click());
            onView(withId(R.id.menu_messages)).perform(scrollTo(), click());
        }
    }

    @Test
    public void bioBackReturnsToMyProfile() {
        try (ActivityScenario<Frame25MyProfileActivity> ignored = ActivityScenario.launch(Frame25MyProfileActivity.class)) {
            onView(withId(R.id.menu_bio)).perform(scrollTo(), click());
            onView(withId(R.id.btnBack)).perform(click());
            onView(withId(R.id.menu_bio)).check(matches(isDisplayed()));
        }
    }

    @Test
    public void notificationRowsOpenMatchingDestinations() {
        try (ActivityScenario<Frame33NotificationsActivity> ignored = ActivityScenario.launch(Frame33NotificationsActivity.class)) {
            onView(withId(R.id.notification_friend_huy)).perform(FORCE_CLICK);
            onView(withId(R.id.menu_messages)).perform(scrollTo(), click());
            pressBack();
            pressBack();
            onView(withId(R.id.notification_share_linh)).perform(FORCE_CLICK);
            onView(withId(R.id.btnWriteReview)).perform(FORCE_CLICK);
            pressBack();
            pressBack();
            onView(withId(R.id.notification_like_minh)).perform(FORCE_CLICK);
            onView(withId(R.id.btnWriteReview)).perform(FORCE_CLICK);
        }
    }

    @Test
    public void passwordResetKeepsSourceAndBackStack() {
        try (ActivityScenario<ForgotPasswordActivity> ignored = ActivityScenario.launch(ForgotPasswordActivity.class)) {
            onView(withId(R.id.edtEmailOrPhone)).perform(replaceText("panda@example.com"));
            onView(withId(R.id.btnSubmit)).perform(FORCE_CLICK);
            onView(withId(R.id.edtNewPassword)).perform(replaceText("panda123"));
            onView(withId(R.id.edtConfirmPassword)).perform(replaceText("panda123"));
            onView(withId(R.id.btnBack)).perform(FORCE_CLICK);
            onView(withId(R.id.edtEmailOrPhone)).perform(replaceText("0901234567"));
        }

        try (ActivityScenario<Frame23EnterCurrentPassword> ignored = ActivityScenario.launch(Frame23EnterCurrentPassword.class)) {
            onView(withId(R.id.etCurrentPassword)).perform(replaceText("panda123"));
            onView(withId(R.id.btnNext)).perform(FORCE_CLICK);
            onView(withId(R.id.edtNewPassword)).perform(replaceText("panda456"));
            onView(withId(R.id.edtConfirmPassword)).perform(replaceText("panda456"));
            onView(withId(R.id.btnSubmitReset)).perform(FORCE_CLICK);
            onView(withId(R.id.btnChangePasswordRow)).perform(FORCE_CLICK);
        }
    }

    @Test
    public void createRecipePublishFlowWorks() {
        try (ActivityScenario<Frame31CreateRecipeActivity> ignored = ActivityScenario.launch(Frame31CreateRecipeActivity.class)) {
            onView(withId(R.id.input_recipe_name)).perform(replaceText("Phở bò"));
            onView(withId(R.id.input_recipe_description)).perform(replaceText("Công thức bản mẫu"));
            closeSoftKeyboard();
            onView(withId(R.id.button_next_recipe)).perform(FORCE_CLICK);
            onView(withId(R.id.privacy_public)).perform(click());
            onView(withId(R.id.button_publish_recipe)).perform(click());
        }
    }
}
