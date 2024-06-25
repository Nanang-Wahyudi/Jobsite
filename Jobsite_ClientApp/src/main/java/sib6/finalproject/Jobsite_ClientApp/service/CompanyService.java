package sib6.finalproject.Jobsite_ClientApp.service;

import java.util.List;

import sib6.finalproject.Jobsite_ClientApp.model.response.CompanyDetailResponse;
import sib6.finalproject.Jobsite_ClientApp.model.response.CompanyResponse;

public interface CompanyService {

    List<CompanyResponse> getAllCompany();

    CompanyDetailResponse getCompanyById(String id);

    CompanyDetailResponse getCompanyProfile();

}
