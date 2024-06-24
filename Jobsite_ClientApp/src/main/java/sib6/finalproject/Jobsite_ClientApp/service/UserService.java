package sib6.finalproject.Jobsite_ClientApp.service;

import java.util.List;

import sib6.finalproject.Jobsite_ClientApp.model.response.UserDetailResponse;
import sib6.finalproject.Jobsite_ClientApp.model.response.UserResponse;

public interface UserService {

    List<UserResponse> getAllUser();

    UserDetailResponse getUserById(String id);

}
