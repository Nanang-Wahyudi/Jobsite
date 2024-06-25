package sib6.finalproject.Jobsite_ClientApp.service;

import java.util.List;

import sib6.finalproject.Jobsite_ClientApp.model.response.CompanyAdminResponse;
import sib6.finalproject.Jobsite_ClientApp.model.response.UserAdminResponse;

public interface AdminService {

    List<UserAdminResponse> getAllUserAdmin();

    List<CompanyAdminResponse> getAllCompanyAdmin();

}
