package sib6.finalproject.Jobsite_ClientApp.service;

import java.util.List;

import sib6.finalproject.Jobsite_ClientApp.model.response.CompanyAdminResponse;
import sib6.finalproject.Jobsite_ClientApp.model.response.UserAdminResponse;
import sib6.finalproject.Jobsite_ClientApp.model.response.UserDetailResponse;

public interface AdminService {

    List<UserAdminResponse> getAllUserAdmin();

    List<CompanyAdminResponse> getAllCompanyAdmin();

    UserAdminResponse deleteUserByAdmin(String id);

    CompanyAdminResponse deleteCompanyByAdmin(String id);

    UserDetailResponse deleteAdminAccount();

}
