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

import sib6.finalproject.Jobsite_ClientApp.model.response.CompanyDetailResponse;
import sib6.finalproject.Jobsite_ClientApp.model.response.CompanyResponse;
import sib6.finalproject.Jobsite_ClientApp.service.CompanyService;

@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${server.base.url}/company")
    private String url;

    @Override
    public List<CompanyResponse> getAllCompany() {
        ResponseEntity<List<CompanyResponse>> response = restTemplate
                .exchange(url,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<CompanyResponse>>() {
                        });

        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            throw new RuntimeException("Failed to Get All Company");
        }
    }

    @Override
    public CompanyDetailResponse getCompanyById(String id) {
        ResponseEntity<CompanyDetailResponse> response = restTemplate
                .exchange(url.concat("/" + id),
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<CompanyDetailResponse>() {
                        });

        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            throw new RuntimeException("Failed to Get Company Detail By ID");
        }
    }

}
