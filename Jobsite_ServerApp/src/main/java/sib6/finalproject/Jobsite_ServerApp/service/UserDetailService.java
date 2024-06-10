package sib6.finalproject.Jobsite_ServerApp.service;

import sib6.finalproject.Jobsite_ServerApp.model.request.UpdateUserDetailRequest;

public interface UserDetailService {

    String updateUserDetail(String username, UpdateUserDetailRequest userDetailRequest);

}
