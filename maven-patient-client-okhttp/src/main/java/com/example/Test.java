package com.example;

import java.util.Map;

import com.example.api.MavenPatientControllerApi;
import com.example.model.Patient;
import com.google.gson.Gson;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class Test
{
    private static final String oauthServer =
            "https://cir-internal-app.health.dohmh.nycnet/auth/realms/nyc-cir/protocol/openid-connect/token";
    private static final String clientId = "maven";
    private static final String clientSecret = "[secret here]";

    public static void main(final String[] argv) throws Exception
    {
        final OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(chain ->
        {
            Request request = chain.request();
            if (request.header("Authorization") == null)
            {
                final String accessToken = (String) new Gson().fromJson(new OkHttpClient().newCall(new Request.Builder()
                        .post(new FormBody.Builder().add("client_id", clientId).add("client_secret", clientSecret)
                                .add("grant_type", "client_credentials").build()).url(oauthServer).build()).execute().body()
                        .string(), Map.class).get("access_token");

                request = request.newBuilder().addHeader("Authorization", "Bearer " + accessToken).build();
            }

            return chain.proceed(request);
        }).build();

        final MavenPatientControllerApi api = new MavenPatientControllerApi(new ApiClient(okHttpClient));
        final Patient patient = api.findById(1L);
        System.out.println(patient.getPatientId());
    }
}
