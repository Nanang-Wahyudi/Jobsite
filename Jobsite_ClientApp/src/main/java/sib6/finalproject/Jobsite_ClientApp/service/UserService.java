package sib6.finalproject.Jobsite_ClientApp.service;

import java.io.IOException;
import java.util.List;

import sib6.finalproject.Jobsite_ClientApp.model.request.UpdateUserDetailRequest;
import sib6.finalproject.Jobsite_ClientApp.model.response.UserDetailResponse;
import sib6.finalproject.Jobsite_ClientApp.model.response.UserResponse;

public interface UserService {

    List<UserResponse> getAllUser();

    UserDetailResponse getUserById(String id);

    UserDetailResponse getUserProfile();

    UserDetailResponse updateUserDetail(UpdateUserDetailRequest userDetailRequest) throws IOException ;

    UserDetailResponse deleteAccount();
}
