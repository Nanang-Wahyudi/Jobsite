package sib6.finalproject.Jobsite_ServerApp.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import sib6.finalproject.Jobsite_ServerApp.model.request.UpdateStatusApplicantRequest;
import sib6.finalproject.Jobsite_ServerApp.model.response.Response;
import sib6.finalproject.Jobsite_ServerApp.service.ApplicantService;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RequestMapping("/api/applicant")
@RestController
public class ApplicantController {

    @Autowired
    private ApplicantService applicantService;


    @Operation(summary = "Apply to a job")
    @PostMapping(value = "/create/{jobId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createApplicant(
            HttpServletRequest servletRequest,
            @RequestParam("file") MultipartFile file,
            @RequestParam("username") String username,
            @PathVariable("jobId") String jobId) throws Exception {

        if (file.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No file provided");
        }

        Response response = Response.builder()
                .url(servletRequest.getRequestURL().toString())
                .status(HttpStatus.OK.toString())
                .message(applicantService.createApplicant(file, jobId, username))
                .build();
        response.setTimestamp(new Date());

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Update job status. Make sure you enter the following appropriate Status PROCESSED (default), ACCEPTED, REJECTED")
    @PutMapping("/update-status/{applicantId}")
    public ResponseEntity<?> updateStatusApplicant(HttpServletRequest servletRequest, @PathVariable("applicantId") String applicantId, @RequestBody UpdateStatusApplicantRequest statusApplicantRequest) {
        Response response = Response.builder()
                .url(servletRequest.getRequestURL().toString())
                .status(HttpStatus.OK.toString())
                .message(applicantService.updateStatusApplicant(applicantId, statusApplicantRequest))
                .build();
        response.setTimestamp(new Date());

        return ResponseEntity.ok(response);
    }

}
