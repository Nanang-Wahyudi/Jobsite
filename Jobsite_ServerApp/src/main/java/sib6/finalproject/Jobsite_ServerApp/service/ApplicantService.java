package sib6.finalproject.Jobsite_ServerApp.service;

import org.springframework.web.multipart.MultipartFile;
import sib6.finalproject.Jobsite_ServerApp.model.request.UpdateStatusApplicantRequest;

public interface ApplicantService {

    String createApplicant(MultipartFile file, String jobId, String username) throws Exception;

    String updateStatusApplicant(String applicantId, UpdateStatusApplicantRequest statusApplicantRequest);

}
