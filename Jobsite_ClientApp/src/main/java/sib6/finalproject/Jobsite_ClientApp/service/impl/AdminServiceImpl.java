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

import sib6.finalproject.Jobsite_ClientApp.model.response.CompanyAdminResponse;
import sib6.finalproject.Jobsite_ClientApp.model.response.UserAdminResponse;
import sib6.finalproject.Jobsite_ClientApp.model.response.UserDetailResponse;
import sib6.finalproject.Jobsite_ClientApp.service.AdminService;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${server.base.url}")
    private String url;

    @Override
    public List<UserAdminResponse> getAllUserAdmin() {
        ResponseEntity<List<UserAdminResponse>> response = restTemplate
                .exchange(url.concat("/user/admin"),
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<UserAdminResponse>>() {
                        });

        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            throw new RuntimeException("Failed to Get All User for Admin");
        }
    }

    @Override
    public List<CompanyAdminResponse> getAllCompanyAdmin() {
        ResponseEntity<List<CompanyAdminResponse>> response = restTemplate
                .exchange(url.concat("/company/admin"),
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<CompanyAdminResponse>>() {
                        });

        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            throw new RuntimeException("Failed to Get All Company for Admin");
        }
    }

    @Override
    public UserAdminResponse deleteUserByAdmin(String id) {
        ResponseEntity<UserAdminResponse> response = restTemplate
                .exchange(url.concat("/user/delete/" + id),
                        HttpMethod.DELETE,
                        null,
                        new ParameterizedTypeReference<UserAdminResponse>() {
                        });

        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            throw new RuntimeException("Failed to Delete User by Admin");
        }
    }

    @Override
    public CompanyAdminResponse deleteCompanyByAdmin(String id) {
        ResponseEntity<CompanyAdminResponse> response = restTemplate
                .exchange(url.concat("/company/delete/" + id),
                        HttpMethod.DELETE,
                        null,
                        new ParameterizedTypeReference<CompanyAdminResponse>() {
                        });

        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            throw new RuntimeException("Failed to Delete Company by Admin");
        }
    }

    @Override
    public UserDetailResponse deleteAdminAccount() {
        ResponseEntity<UserDetailResponse> response = restTemplate
                .exchange(url.concat("/user/delete"),
                        HttpMethod.DELETE,
                        null,
                        new ParameterizedTypeReference<UserDetailResponse>() {
                        });

        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            throw new RuntimeException("Failed to Delete Account");
        }
    }
}
