package sib6.finalproject.Jobsite_ServerApp.service;

import sib6.finalproject.Jobsite_ServerApp.model.enums.RoleEnum;
import sib6.finalproject.Jobsite_ServerApp.model.request.RegisterRequest;

public interface AuthService {

    String register(RegisterRequest request, RoleEnum roleName);

}
