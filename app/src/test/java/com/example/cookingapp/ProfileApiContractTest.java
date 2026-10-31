package com.example.cookingapp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProfileApiContractTest {
    private MockWebServer server;
    private ApiService service;

    @Before
    public void setUp() throws IOException {
        server = new MockWebServer();
        server.start();
        service = new Retrofit.Builder()
                .baseUrl(server.url("/"))
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService.class);
    }

    @After
    public void tearDown() throws IOException {
        server.shutdown();
    }

    @Test
    public void getProfileUsesMeEndpointAndMapsSnakeCaseFields() throws Exception {
        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setHeader("Content-Type", "application/json")
                .setBody("{"
                        + "\"id\":1,"
                        + "\"username\":\"panda\","
                        + "\"email\":\"panda@example.com\","
                        + "\"full_name\":\"Panda Bếp\","
                        + "\"phone_number\":\"0901234567\","
                        + "\"bio\":\"Món Việt\","
                        + "\"avatar_url\":\"/uploads/avatars/panda.png\""
                        + "}"));

        Response<Profile> response = service.getMyProfile().execute();
        RecordedRequest request = server.takeRequest();

        assertTrue(response.isSuccessful());
        assertEquals("/api/profile/me", request.getPath());
        assertNotNull(response.body());
        assertEquals("Panda Bếp", response.body().getFullName());
        assertEquals("0901234567", response.body().getPhoneNumber());
        assertEquals("/uploads/avatars/panda.png", response.body().getAvatarUrl());
    }

    @Test
    public void patchProfileSendsOnlyEditableProfileFields() throws Exception {
        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setHeader("Content-Type", "application/json")
                .setBody("{\"id\":1,\"username\":\"panda\","
                        + "\"email\":\"panda@example.com\","
                        + "\"full_name\":\"Tên mới\","
                        + "\"phone_number\":null,\"bio\":null,\"avatar_url\":null}"));

        service.updateMyProfile(new UpdateProfileRequest("Tên mới", "", "")).execute();
        RecordedRequest request = server.takeRequest();
        String body = request.getBody().readUtf8();

        assertEquals("PATCH", request.getMethod());
        assertEquals("/api/profile/me", request.getPath());
        assertTrue(body.contains("\"full_name\":\"Tên mới\""));
        assertTrue(body.contains("\"phone_number\":\"\""));
        assertTrue(body.contains("\"bio\":\"\""));
        assertFalse(body.contains("email"));
        assertFalse(body.contains("username"));
    }

    @Test
    public void avatarUploadUsesExpectedMultipartEndpoint() throws Exception {
        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setHeader("Content-Type", "application/json")
                .setBody("{\"id\":1,\"username\":\"panda\","
                        + "\"email\":\"panda@example.com\","
                        + "\"full_name\":null,\"phone_number\":null,"
                        + "\"bio\":null,\"avatar_url\":\"/uploads/avatars/new.png\"}"));
        RequestBody body = RequestBody.create(
                MediaType.parse("image/png"),
                new byte[]{1, 2, 3}
        );
        MultipartBody.Part part = MultipartBody.Part.createFormData("file", "avatar.png", body);

        service.uploadMyAvatar(part).execute();
        RecordedRequest request = server.takeRequest();

        assertEquals("POST", request.getMethod());
        assertEquals("/api/profile/me/avatar", request.getPath());
        assertTrue(request.getHeader("Content-Type").startsWith("multipart/form-data"));
        assertTrue(request.getBody().readUtf8().contains("avatar.png"));
    }

    @Test
    public void relativeAndAbsoluteAvatarUrlsAreResolvedCorrectly() {
        assertEquals(
                BuildConfig.API_BASE_URL + "uploads/avatars/panda.png",
                ApiClient.resolveUrl("/uploads/avatars/panda.png")
        );
        assertEquals(
                "https://cdn.example.com/panda.png",
                ApiClient.resolveUrl("https://cdn.example.com/panda.png")
        );
    }
}
