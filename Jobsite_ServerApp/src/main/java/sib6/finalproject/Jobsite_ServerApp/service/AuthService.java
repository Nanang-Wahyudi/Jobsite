package sib6.finalproject.Jobsite_ServerApp.service;

import sib6.finalproject.Jobsite_ServerApp.model.enums.RoleEnum;
import sib6.finalproject.Jobsite_ServerApp.model.request.ForgotPasswordRequest;
import sib6.finalproject.Jobsite_ServerApp.model.request.LoginRequest;
import sib6.finalproject.Jobsite_ServerApp.model.request.RegisterRequest;
import sib6.finalproject.Jobsite_ServerApp.model.request.UpdatePasswordRequest;
import sib6.finalproject.Jobsite_ServerApp.model.response.LoginResponse;

public interface AuthService {

    String register(RegisterRequest request, RoleEnum roleName);

    LoginResponse login(LoginRequest loginRequest);

    String updatePassword(String username, UpdatePasswordRequest updatePasswordRequest);

    String forgotPassword(ForgotPasswordRequest forgotPasswordRequest);

}
