package com.java04.utils;

import okhttp3.*;
import java.io.IOException;

public class MessegerUtils {
    private static final String MESSENGER_API_URL = "https://graph.facebook.com/v19.0/me/messages";
    private static final String ACCESS_TOKEN = "YOUR_MESSENGER_PAGE_ACCESS_TOKEN"; // Lấy từ Facebook Developer

    public static void sendShareMessage(String recipientId, String messageContent) throws IOException {
        OkHttpClient client = new OkHttpClient();

        String json = "{\n"
                + "  \"recipient\": {\n"
                + "    \"id\": \"" + recipientId + "\"\n"
                + "  },\n"
                + "  \"message\": {\n"
                + "    \"text\": \"" + messageContent + "\"\n"
                + "  }\n"
                + "}";

        RequestBody body = RequestBody.create(json, MediaType.parse("application/json"));
        Request request = new Request.Builder()
                .url(MESSENGER_API_URL + "?access_token=" + ACCESS_TOKEN)
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            System.out.println(response.body().string());
        }
    }
}
