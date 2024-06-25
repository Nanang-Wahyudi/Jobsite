package sib6.finalproject.Jobsite_ClientApp.service;

import java.io.IOException;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import sib6.finalproject.Jobsite_ClientApp.model.request.UpdateStatusApplicantRequest;
import sib6.finalproject.Jobsite_ClientApp.model.response.ApplicantDetailResponse;
import sib6.finalproject.Jobsite_ClientApp.model.response.ApplicantResponse;

public interface ApplicantService {

    List<ApplicantResponse> getAllApplicantHistory();

    List<ApplicantResponse> getAllApplicant();

    ApplicantDetailResponse getApplicantDetailById(String id);

    ApplicantResponse createApplicant(MultipartFile file, String jobId) throws IOException;
    
    ResponseEntity<Resource> downloadCv(String id);

    ApplicantDetailResponse updateApplicantStatus(String id, UpdateStatusApplicantRequest statusApplicantRequest);

}
