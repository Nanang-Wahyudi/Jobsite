package sib6.finalproject.Jobsite_ServerApp.service;

import org.springframework.web.multipart.MultipartFile;
import sib6.finalproject.Jobsite_ServerApp.entity.Applicant;
import sib6.finalproject.Jobsite_ServerApp.model.request.UpdateStatusApplicantRequest;
import sib6.finalproject.Jobsite_ServerApp.model.response.ApplicantDetailResponse;
import sib6.finalproject.Jobsite_ServerApp.model.response.ApplicantResponse;

import java.util.List;

public interface ApplicantService {

    Applicant findById(String id);

    List<ApplicantResponse> getAllApplicant(String username);

    ApplicantDetailResponse getApplicantById(String id);

    String createApplicant(MultipartFile file, String jobId, String username) throws Exception;

    String updateStatusApplicant(String applicantId, UpdateStatusApplicantRequest statusApplicantRequest);

}
