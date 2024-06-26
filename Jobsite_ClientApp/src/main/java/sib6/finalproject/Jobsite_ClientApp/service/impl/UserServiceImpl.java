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

import sib6.finalproject.Jobsite_ClientApp.model.request.UpdateUserDetailRequest;
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

    @Override
    public UserDetailResponse updateUserDetail(UpdateUserDetailRequest userDetailRequest) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("name", userDetailRequest.getName());
        body.add("email", userDetailRequest.getEmail());
        body.add("address", userDetailRequest.getAddress());
        body.add("description", userDetailRequest.getDescription());
        body.add("instansiName", userDetailRequest.getInstansiName());
        body.add("major", userDetailRequest.getMajor());
        body.add("avgScore", userDetailRequest.getAvgScore());
        body.add("skillName", userDetailRequest.getSkillName());

        if (userDetailRequest.getPicture() != null) {
            body.add("picture", new ByteArrayResource(userDetailRequest.getPicture().getBytes()) {
                @Override
                public String getFilename() {
                    return userDetailRequest.getPicture().getOriginalFilename();
                }
            });
        }

        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(body, headers);
                
        ResponseEntity<UserDetailResponse> response = restTemplate
                .exchange(url.concat("/update"), 
                        HttpMethod.PUT,
                        request,
                        new ParameterizedTypeReference<UserDetailResponse>() {
                        });
        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            throw new RuntimeException("Failed to Update User Profile");
        }
    }
}
