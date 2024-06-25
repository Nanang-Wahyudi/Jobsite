package sib6.finalproject.Jobsite_ClientApp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import sib6.finalproject.Jobsite_ClientApp.model.response.ApplicantDetailResponse;
import sib6.finalproject.Jobsite_ClientApp.model.response.ApplicantResponse;
import sib6.finalproject.Jobsite_ClientApp.service.ApplicantService;

@Service
public class ApplicantServiceImpl implements ApplicantService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${server.base.url}/applicant")
    private String url;

    @Override
    public List<ApplicantResponse> getAllApplicantHistory() {
        ResponseEntity<List<ApplicantResponse>> response = restTemplate
                .exchange(url.concat("/history"),
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<ApplicantResponse>>() {
                        });

        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            throw new RuntimeException("Failed to Get All Applicant History");
        }
    }

    @Override
    public List<ApplicantResponse> getAllApplicant() {
        ResponseEntity<List<ApplicantResponse>> response = restTemplate
                .exchange(url,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<ApplicantResponse>>() {
                        });

        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            throw new RuntimeException("Failed to Get All Applicant");
        }
    }

    @Override
    public ApplicantDetailResponse getApplicantDetailById(String id) {
        ResponseEntity<ApplicantDetailResponse> response = restTemplate
                .exchange(url.concat("/" + id),
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<ApplicantDetailResponse>() {
                        });

        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            throw new RuntimeException("Failed to Get Applicant By ID");
        }
    }

}
