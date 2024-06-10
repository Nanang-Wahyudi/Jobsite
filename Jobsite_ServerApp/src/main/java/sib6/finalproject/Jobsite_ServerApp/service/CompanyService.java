package sib6.finalproject.Jobsite_ServerApp.service;

import sib6.finalproject.Jobsite_ServerApp.model.request.UpdateCompanyDetailRequest;

public interface CompanyService {

    String updateCompanyDetail(String username, UpdateCompanyDetailRequest companyDetailRequest);

}
