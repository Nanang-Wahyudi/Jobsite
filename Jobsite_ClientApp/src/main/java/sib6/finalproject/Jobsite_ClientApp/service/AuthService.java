package sib6.finalproject.Jobsite_ClientApp.service;

import sib6.finalproject.Jobsite_ClientApp.model.request.LoginRequest;
import sib6.finalproject.Jobsite_ClientApp.model.response.LoginResponse;

public interface AuthService {

    Boolean login(LoginRequest loginRequest);

    void setPrinciple(LoginResponse loginResponse, String password);

}
