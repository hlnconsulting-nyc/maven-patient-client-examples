package com.example;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.example.api.MavenPatientControllerApi;
import com.example.model.Patient;
import com.google.gson.Gson;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class TestOkHttp
{
    public static void main(final String[] argv) throws Exception
    {
        final OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(chain ->
        {
            Request request = chain.request();
            if (request.header("Authorization") == null)
            {
                final String accessToken = (String) new Gson().fromJson(new OkHttpClient().newCall(new Request.Builder()
                        .post(new FormBody.Builder().add("client_id", TestProperties.clientId)
                                .add("client_secret", TestProperties.clientId).add("grant_type", "client_credentials").build())
                        .url(TestProperties.oauthServer).build()).execute().body().string(), Map.class).get("access_token");

                request = request.newBuilder().addHeader("Authorization", "Bearer " + accessToken).build();
            }

            return chain.proceed(request);
        }).build();

        final MavenPatientControllerApi api = new MavenPatientControllerApi(new ApiClient(okHttpClient));
        Long patientId = null;
        String firstName = null;
        String lastName = null;
        String gender = null;
        LocalDate dob = null; 
        String street1 = null; 
        String aptNo = null; 
        String city = null; 
        String state = null; 
        String zipcode = null;
        String homePhone = null; 
        String medicaid = null; 
        String medicalRecordNo = null;
        String probabilityModel = null;
        Float mediumProbabilityThreshold = null;
        Float highProbabilityThreshold = null;
        Integer maxNumMatches = null; 
        String purpose = null; 
        Integer cirAutomaticLookup = null;
        List<Patient> patients = api.matchPatient(patientId, firstName, lastName, gender, dob, street1, aptNo, city, state, zipcode,
                        homePhone, medicaid, medicalRecordNo, probabilityModel, mediumProbabilityThreshold,
                        highProbabilityThreshold, maxNumMatches, purpose, cirAutomaticLookup);
        //final Patient patient = api.findById(TestProperties.patientId);
        //System.out.println(patient.getPatientId());
    }
}
