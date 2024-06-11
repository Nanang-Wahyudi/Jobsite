package sib6.finalproject.Jobsite_ServerApp.service;

import sib6.finalproject.Jobsite_ServerApp.model.request.UpdateUserDetailRequest;
import sib6.finalproject.Jobsite_ServerApp.model.response.UserDetailResponse;
import sib6.finalproject.Jobsite_ServerApp.model.response.UserResponse;

import java.util.List;

public interface UserDetailService {

    List<UserResponse> getAllUser();

    UserDetailResponse getUserDetailById(String id);

    UserDetailResponse getUserDetailProfile(String username);

    String updateUserDetail(String username, UpdateUserDetailRequest userDetailRequest);

}
