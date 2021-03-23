package com.example;

import java.io.IOException;

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
        final Patient patient = api.findById(TestProperties.patientId);
        System.out.println(patient.getPatientId());
    }
}
