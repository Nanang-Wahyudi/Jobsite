package sib6.finalproject.Jobsite_ServerApp.service;

import sib6.finalproject.Jobsite_ServerApp.model.request.UpdateUserDetailRequest;
import sib6.finalproject.Jobsite_ServerApp.model.response.UserAdminResponse;
import sib6.finalproject.Jobsite_ServerApp.model.response.UserDetailResponse;
import sib6.finalproject.Jobsite_ServerApp.model.response.UserResponse;

import java.util.List;

public interface UserDetailService {

    List<UserResponse> getAllUser();

    List<UserAdminResponse> getAllUserForAdmin();

    UserDetailResponse getUserDetailById(String id);

    UserDetailResponse getUserDetailProfile(String username);

    String updateUserDetail(String username, UpdateUserDetailRequest userDetailRequest);

    String deleteUserByUsername(String username);

    String deleteUserByIdForAdmin(String id);

}
