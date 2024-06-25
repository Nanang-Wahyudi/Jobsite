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

import sib6.finalproject.Jobsite_ClientApp.model.response.UserDetailResponse;
import sib6.finalproject.Jobsite_ClientApp.model.response.UserResponse;
import sib6.finalproject.Jobsite_ClientApp.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${server.base.url}/user")
    private String url;

    @Override
    public List<UserResponse> getAllUser() {
        ResponseEntity<List<UserResponse>> response = restTemplate
                .exchange(url,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<UserResponse>>() {
                        });

        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            throw new RuntimeException("Failed to Get All User");
        }
    }

    @Override
    public UserDetailResponse getUserById(String id) {
        ResponseEntity<UserDetailResponse> response = restTemplate
                .exchange(url.concat("/" + id),
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<UserDetailResponse>() {
                        });

        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            throw new RuntimeException("Failed to Get User Detail By ID");
        }
    }

    @Override
    public UserDetailResponse getUserProfile() {
        ResponseEntity<UserDetailResponse> response = restTemplate
                .exchange(url.concat("/profile"),
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<UserDetailResponse>() {
                        });

        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            throw new RuntimeException("Failed to Get User Profile");
        }
    }

}
