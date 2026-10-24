package com.example.cookingapp;

import static org.junit.Assert.assertTrue;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

public class ManifestCoverageTest {
    private static final String[] ACTIVITIES = {
            "SplashActivity", "LoginActivity", "RegisterActivity", "ForgotPasswordActivity",
            "ResetPasswordActivity", "ManHinhChinhActivity", "SearchActivity", "RecipeDetailActivity",
            "ReviewActivity", "MainActivity", "PlaceholderActivity", "Frame7SettingActivity",
            "Frame8Language", "Frame9Contactus", "Frame10Myactivity", "Frame11Dadanhgia",
            "Frame12FavoritesList", "Frame13CookingSchedule", "Frame14SavedList", "Frame15ProfileDetails",
            "Frame16ChangeAccountName", "Frame17ChangeGmailAddress", "Frame18EnterGmailOTP",
            "Frame19ChangePhoneNumber", "Frame20SelectAMethod", "Frame21OTPZalo", "Frame22OTPSDT",
            "Frame23EnterCurrentPassword", "Frame24OtherProfileActivity", "Frame25MyProfileActivity",
            "Frame26BioActivity", "Frame27FriendsActivity", "Frame28FollowingActivity",
            "Frame29MessagesActivity", "Frame30ChatDetailActivity", "Frame31CreateRecipeActivity",
            "Frame32PrivacyActivity", "Frame33NotificationsActivity", "Frame34OtherBioActivity"
    };

    @Test
    public void everyActivityExistsAndIsDeclared() throws Exception {
        Path manifest = Paths.get("src/main/AndroidManifest.xml");
        if (!Files.exists(manifest)) {
            manifest = Paths.get("app/src/main/AndroidManifest.xml");
        }
        String xml = new String(Files.readAllBytes(manifest), StandardCharsets.UTF_8);

        for (String activity : ACTIVITIES) {
            Class.forName("com.example.cookingapp." + activity);
            assertTrue(activity + " is missing from AndroidManifest.xml",
                    xml.contains("android:name=\"." + activity + "\""));
        }
    }
}
