package sib6.finalproject.Jobsite_ServerApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import sib6.finalproject.Jobsite_ServerApp.model.response.Response;
import sib6.finalproject.Jobsite_ServerApp.service.ApplicantService;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RequestMapping("/api/applicant")
@RestController
public class ApplicantController {

    @Autowired
    private ApplicantService applicantService;

    @PostMapping("/create/{jobId}")
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

}
