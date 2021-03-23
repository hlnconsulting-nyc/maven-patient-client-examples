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
public class TestApplication implements CommandLineRunner
{
    private static final String oauthServer =
            "https://cir-internal-app.health.dohmh.nycnet/auth/realms/nyc-cir/protocol/openid-connect/token";
    private static final String clientId = "maven";
    private static final String clientSecret = "[secret here]";

    public static void main(final String[] argv)
    {
        SpringApplication.run(TestApplication.class, argv);
    }

    @Bean
    public RestTemplate restTemplate()
    {
        final ClientCredentialsResourceDetails clientCredentialsResourceDetails = new ClientCredentialsResourceDetails();
        clientCredentialsResourceDetails.setAccessTokenUri(oauthServer);
        clientCredentialsResourceDetails.setClientId(clientId);
        clientCredentialsResourceDetails.setClientSecret(clientSecret);

        return new OAuth2RestTemplate(clientCredentialsResourceDetails);
    }

    @Override
    public void run(final String[] argv)
    {
        final MavenPatientControllerApi api = new MavenPatientControllerApi(new ApiClient(restTemplate()));
        final Patient patient = api.findById(1L);
        System.out.println(patient.getPatientId());
    }
}
