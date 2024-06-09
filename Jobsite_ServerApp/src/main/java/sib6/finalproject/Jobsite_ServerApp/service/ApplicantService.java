package sib6.finalproject.Jobsite_ServerApp.service;

import org.springframework.web.multipart.MultipartFile;

public interface ApplicantService {

    String createApplicant(MultipartFile file, String jobId, String username) throws Exception;

}
