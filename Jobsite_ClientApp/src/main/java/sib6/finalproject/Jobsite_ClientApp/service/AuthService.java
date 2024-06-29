package sib6.finalproject.Jobsite_ClientApp.service;

import org.springframework.http.ResponseEntity;

import sib6.finalproject.Jobsite_ClientApp.model.request.LoginRequest;
import sib6.finalproject.Jobsite_ClientApp.model.request.UpdatePasswordRequest;
import sib6.finalproject.Jobsite_ClientApp.model.response.LoginResponse;

public interface AuthService {

    Boolean login(LoginRequest loginRequest);

    void setPrinciple(LoginResponse loginResponse, String password);

    ResponseEntity<String> updatePassword(UpdatePasswordRequest passwordRequest);
}
