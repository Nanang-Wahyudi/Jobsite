package sib6.finalproject.Jobsite_ClientApp.controller.rest;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import sib6.finalproject.Jobsite_ClientApp.model.response.ApplicantResponse;
import sib6.finalproject.Jobsite_ClientApp.service.ApplicantService;

@RequestMapping("/api/client/applicant")
@RestController
public class RestApplicantController {

    @Autowired
    private ApplicantService applicantService;

    @PostMapping("/create/{id}")
    public ApplicantResponse createApplicant(@RequestParam("file") MultipartFile file,
            @PathVariable("id") String jobId) throws IOException {
        return applicantService.createApplicant(file, jobId);
    }

    @GetMapping("/download-cv/{id}")
    public ResponseEntity<Resource> downloadCv(@PathVariable("id") String id) {
        return applicantService.downloadCv(id);
    }

}
