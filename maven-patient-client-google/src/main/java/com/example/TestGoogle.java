package com.example;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import com.example.api.MavenPatientControllerApi;
import com.example.model.Patient;
import com.google.api.client.auth.oauth2.BearerToken;
import com.google.api.client.auth.oauth2.ClientCredentialsTokenRequest;
import com.google.api.client.auth.oauth2.ClientParametersAuthentication;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;

public class TestGoogle
{
    static Credential getCredentials() throws IOException
    {
        return new Credential(BearerToken.authorizationHeaderAccessMethod()).setFromTokenResponse(
                new ClientCredentialsTokenRequest(new NetHttpTransport(), new GsonFactory(),
                        new GenericUrl(TestProperties.oauthServer)).setClientAuthentication(
                        new ClientParametersAuthentication(TestProperties.clientId, TestProperties.clientSecret)).execute());
    }

    public static void main(final String[] argv) throws Exception
    {
        final MavenPatientControllerApi api = new MavenPatientControllerApi(new ApiClient(null, null, getCredentials(), null));
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
