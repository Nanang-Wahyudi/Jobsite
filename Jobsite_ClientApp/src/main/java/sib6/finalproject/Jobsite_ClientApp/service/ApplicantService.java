package sib6.finalproject.Jobsite_ClientApp.service;

import java.util.List;

import sib6.finalproject.Jobsite_ClientApp.model.response.ApplicantDetailResponse;
import sib6.finalproject.Jobsite_ClientApp.model.response.ApplicantResponse;

public interface ApplicantService {

    List<ApplicantResponse> getAllApplicantHistory();

    List<ApplicantResponse> getAllApplicant();

    ApplicantDetailResponse getApplicantDetailById(String id);

}
