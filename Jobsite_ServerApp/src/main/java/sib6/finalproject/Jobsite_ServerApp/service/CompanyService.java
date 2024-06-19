package sib6.finalproject.Jobsite_ServerApp.service;

import sib6.finalproject.Jobsite_ServerApp.model.request.UpdateCompanyDetailRequest;
import sib6.finalproject.Jobsite_ServerApp.model.response.CompanyAdminResponse;
import sib6.finalproject.Jobsite_ServerApp.model.response.CompanyDetailResponse;
import sib6.finalproject.Jobsite_ServerApp.model.response.CompanyResponse;

import java.util.List;

public interface CompanyService {

    List<CompanyResponse> getAllCompany();

    List<CompanyAdminResponse> getAllCompanyForAdmin();

    CompanyDetailResponse getCompanyDetailById(String id);

    CompanyDetailResponse getCompanyDetailProfile(String username);

    String updateCompanyDetail(String username, UpdateCompanyDetailRequest companyDetailRequest);

    String deleteCompanyByUsername(String username);

    String deleteCompanyByIdForAdmin(String id);

}
