package com.example;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.web.client.RestTemplate;

import com.example.api.MavenPatientControllerApi;
import com.example.model.Patient;

@SpringBootApplication
public class TestRestTemplate implements CommandLineRunner
{
    public static void main(final String[] argv)
    {
        SpringApplication.run(TestRestTemplate.class, argv).close();
    }

    @Bean
    public RestTemplate restTemplate()
    {
        final ClientCredentialsResourceDetails clientCredentialsResourceDetails = new ClientCredentialsResourceDetails();
        clientCredentialsResourceDetails.setAccessTokenUri(TestProperties.oauthServer);
        clientCredentialsResourceDetails.setClientId(TestProperties.clientId);
        clientCredentialsResourceDetails.setClientSecret(TestProperties.clientSecret);

        return new OAuth2RestTemplate(clientCredentialsResourceDetails);
    }

    @Override
    public void run(final String[] argv)
    {
        final MavenPatientControllerApi api = new MavenPatientControllerApi(new ApiClient(restTemplate()));
        final Patient patient = api.findById(TestProperties.patientId);
        System.out.println(patient.getPatientId());
    }
}
