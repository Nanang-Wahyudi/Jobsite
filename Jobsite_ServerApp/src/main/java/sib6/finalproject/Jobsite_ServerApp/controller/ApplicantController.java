package sib6.finalproject.Jobsite_ServerApp.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import sib6.finalproject.Jobsite_ServerApp.entity.Applicant;
import sib6.finalproject.Jobsite_ServerApp.model.request.UpdateStatusApplicantRequest;
import sib6.finalproject.Jobsite_ServerApp.model.response.ApplicantDetailResponse;
import sib6.finalproject.Jobsite_ServerApp.model.response.Response;
import sib6.finalproject.Jobsite_ServerApp.service.ApplicantService;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RequestMapping("/api/applicant")
@RestController
public class ApplicantController {

    @Autowired
    private ApplicantService applicantService;


    @Operation(summary = "Display all applicants base on username company")
    @PreAuthorize("hasAuthority('READ_COMPANY')")
    @GetMapping()
    public ResponseEntity<?> getAllApplicant() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(applicantService.getAllApplicant(username));
    }

    @Operation(summary = "download cv files from applicants based on applicant ID")
    @PreAuthorize("hasAuthority('READ_COMPANY')")
    @GetMapping("/file-download/{id}")
    public ResponseEntity<Resource> downloadFile(@PathVariable("id") String id) {
        Applicant applicant = applicantService.findById(id);
        ByteArrayResource resource = new ByteArrayResource(applicant.getCv());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + applicant.getUserDetail().getName() + "_" + applicant.getJob().getTitle() + "_" + applicant.getApplicantDate() + ".pdf\"")
                .contentType(MediaType.APPLICATION_PDF)
                .contentLength(applicant.getCv().length)
                .body(resource);
    }

    @Operation(summary = "Display applicants based on applicant ID")
    @PreAuthorize("hasAuthority('READ_COMPANY')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getApplicantById(@PathVariable("id") String id) {
        ApplicantDetailResponse applicantDetailResponse = applicantService.getApplicantById(id);
        String downloadUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/applicant/file-download/")
                .path(applicantDetailResponse.getId())
                .toUriString();
        applicantDetailResponse.setUrlDownloadCv(downloadUrl);

        return ResponseEntity.ok(applicantDetailResponse);
    }

    @Operation(summary = "Display all applicants history based on username")
    @PreAuthorize("hasAuthority('READ_USER')")
    @GetMapping("/history")
    public ResponseEntity<?> getAllApplicantHistoryUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(applicantService.getAllApplicantHistoryUser(username));
    }

    @Operation(summary = "Apply to a job")
    @PreAuthorize("hasAuthority('CREATE_USER')")
    @PostMapping(value = "/create/{jobId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createApplicant(
            HttpServletRequest servletRequest,
            @RequestParam("file") MultipartFile file,
            @PathVariable("jobId") String jobId) throws Exception {

        if (file.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No file provided");
        }

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Response response = Response.builder()
                .url(servletRequest.getRequestURL().toString())
                .status(HttpStatus.OK.toString())
                .message(applicantService.createApplicant(file, jobId, username))
                .build();
        response.setTimestamp(new Date());

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Update job status. Make sure you enter the following appropriate Status PROCESSED (default), ACCEPTED, REJECTED")
    @PreAuthorize("hasAuthority('UPDATE_COMPANY')")
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
