package sib6.finalproject.Jobsite_ServerApp.service;

import sib6.finalproject.Jobsite_ServerApp.model.request.UpdateUserDetailRequest;

import java.io.IOException;

public interface UserDetailService {

    String updateUserDetail(String username, UpdateUserDetailRequest userDetailRequest) throws IOException;

}
