package sib6.finalproject.Jobsite_ClientApp.service.impl;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
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
import org.springframework.web.multipart.MultipartFile;

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

    @Override
    public ApplicantResponse createApplicant(MultipartFile file, String jobId) throws IOException {
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", new ByteArrayResource(file.getBytes()) {
            @Override
            public String getFilename() {
                return file.getOriginalFilename();
            }
        });

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<ApplicantResponse> response = restTemplate.exchange(
                url.concat("/create/" + jobId),
                HttpMethod.POST,
                requestEntity,
                ApplicantResponse.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            throw new RuntimeException("Failed to create applicant");
        }
    }

    @Override
    public ResponseEntity<Resource> downloadCv(String id) {
        ResponseEntity<ByteArrayResource> response = restTemplate
                .exchange(url.concat("/file-download/" + id),
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<ByteArrayResource>() {
                        });

        if (response.getStatusCode() == HttpStatus.OK) {
            ByteArrayResource resource = response.getBody();
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=cv_" + id + ".pdf");

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(resource.contentLength())
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);
        } else {
            throw new RuntimeException("Failed to Download CV");
        }
    }

}
