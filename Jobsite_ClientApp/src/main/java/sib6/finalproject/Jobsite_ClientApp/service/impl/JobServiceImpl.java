package sib6.finalproject.Jobsite_ClientApp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import sib6.finalproject.Jobsite_ClientApp.model.request.CreateJobRequest;
import sib6.finalproject.Jobsite_ClientApp.model.response.JobDetailResponse;
import sib6.finalproject.Jobsite_ClientApp.model.response.JobResponse;
import sib6.finalproject.Jobsite_ClientApp.service.JobService;

@Service
public class JobServiceImpl implements JobService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${server.base.url}/job")
    private String url;

    @Override
    public List<JobResponse> getAllJob() {
        ResponseEntity<List<JobResponse>> response = restTemplate
                .exchange(url,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<JobResponse>>() {
                        });

        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            throw new RuntimeException("Failed to Get All Job");
        }
    }

    @Override
    public JobDetailResponse getJobDetailById(String id) {
        ResponseEntity<JobDetailResponse> response = restTemplate
                .exchange(url.concat("/" + id),
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<JobDetailResponse>() {
                        });

        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            throw new RuntimeException("Failed to Get Detail Job By ID");
        }
    }

    @Override
    public JobResponse addJob(CreateJobRequest jobRequest) {
        ResponseEntity<JobResponse> response = restTemplate.exchange(
                url.concat("/create"),
                HttpMethod.POST,
                new HttpEntity<>(jobRequest),
                JobResponse.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            throw new RuntimeException("Failed to add Job");
        }
    }

    @Override
    public JobResponse deleteJob(String id) {
        ResponseEntity<JobResponse> response = restTemplate
                .exchange(url.concat("/delete/" + id),
                        HttpMethod.DELETE,
                        null,
                        new ParameterizedTypeReference<JobResponse>() {
                        });

        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            throw new RuntimeException("Failed to Delete Job");
        }
    }

}
