package sib6.finalproject.Jobsite_ClientApp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import sib6.finalproject.Jobsite_ClientApp.model.request.UpdateEducationRequest;
import sib6.finalproject.Jobsite_ClientApp.model.response.EducationResponse;
import sib6.finalproject.Jobsite_ClientApp.service.EducationService;

@Service
public class EducationServiceImpl implements EducationService {
    
    @Value("${server.base.url}/education")
    private String url;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public EducationResponse updateEducation(String id, UpdateEducationRequest educationRequest) {
        HttpEntity<UpdateEducationRequest> request = new HttpEntity<UpdateEducationRequest>(educationRequest);

        ResponseEntity<EducationResponse> response = restTemplate
                .exchange(url.concat("/update/" + id),
                        HttpMethod.PUT,
                        request,
                        new ParameterizedTypeReference<EducationResponse>() {
                        });

        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            throw new RuntimeException("Failed to Update Education");
        }
    }

    @Override
    public EducationResponse deleteEducation(String id) {
        ResponseEntity<EducationResponse> response = restTemplate
                .exchange(url.concat("/delete/" + id),
                        HttpMethod.PUT,
                        null,
                        new ParameterizedTypeReference<EducationResponse>() {
                        });

        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            throw new RuntimeException("Failed to Delete Education");
        }
    }
    
}
