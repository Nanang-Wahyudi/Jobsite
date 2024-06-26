package sib6.finalproject.Jobsite_ClientApp.service.impl;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import sib6.finalproject.Jobsite_ClientApp.model.request.UpdateCompanyDetailRequest;
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

    @Override
    public CompanyDetailResponse getCompanyProfile() {
        ResponseEntity<CompanyDetailResponse> response = restTemplate
                .exchange(url.concat("/profile"),
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<CompanyDetailResponse>() {
                        });

        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            throw new RuntimeException("Failed to Get Company Profile");
        }
    }

    @Override
    public CompanyDetailResponse updateCompanyDetail(UpdateCompanyDetailRequest companyDetailRequest) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("name", companyDetailRequest.getName());
        body.add("email", companyDetailRequest.getEmail());
        body.add("address", companyDetailRequest.getAddress());
        body.add("description", companyDetailRequest.getDescription());

        if (companyDetailRequest.getPicture() != null) {
            body.add("picture", new ByteArrayResource(companyDetailRequest.getPicture().getBytes()) {
                @Override
                public String getFilename() {
                    return companyDetailRequest.getPicture().getOriginalFilename();
                }
            });
        }

        if (companyDetailRequest.getBanner() != null) {
            body.add("banner", new ByteArrayResource(companyDetailRequest.getBanner().getBytes()) {
                @Override
                public String getFilename() {
                    return companyDetailRequest.getBanner().getOriginalFilename();
                }
            });
        }

        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(body, headers);
                
        ResponseEntity<CompanyDetailResponse> response = restTemplate
                .exchange(url.concat("/update"), 
                        HttpMethod.PUT,
                        request,
                        new ParameterizedTypeReference<CompanyDetailResponse>() {
                        });
        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            throw new RuntimeException("Failed to Update Company Profile");
        }
    }

}
