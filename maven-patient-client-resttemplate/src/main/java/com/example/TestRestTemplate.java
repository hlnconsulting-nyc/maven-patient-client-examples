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

import java.time.LocalDate;
import java.util.List;

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
