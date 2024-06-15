package sib6.finalproject.Jobsite_ServerApp.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import sib6.finalproject.Jobsite_ServerApp.model.request.CreateJobRequest;
import sib6.finalproject.Jobsite_ServerApp.model.request.UpdateJobRequest;
import sib6.finalproject.Jobsite_ServerApp.model.response.Response;
import sib6.finalproject.Jobsite_ServerApp.service.JobService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;

@RequestMapping("/api/job")
@RestController
public class JobController {

    @Autowired
    private JobService jobService;


    @Operation(summary = "Display all jobs")
    @GetMapping()
    public ResponseEntity<?> getAllJob() {
        return ResponseEntity.ok(jobService.getAllJob());
    }

    @Operation(summary = "Display jobs based on job ID")
    @GetMapping("/{id}")
    public ResponseEntity<?> getJobDetailById(@PathVariable("id") String id) {
        return ResponseEntity.ok(jobService.getJobDetailById(id));
    }

    @Operation(summary = "Create and post Job Vacancies. Make sure you enter the job type from the following options FULLTIME, PARTTIME, CONTRACT")
    @PreAuthorize("hasAuthority('CREATE_COMPANY')")
    @PostMapping("/create")
    public ResponseEntity<?> createJob(HttpServletRequest servletRequest, @Valid @RequestBody CreateJobRequest jobRequest) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Response response = Response.builder()
                .url(servletRequest.getRequestURL().toString())
                .status(HttpStatus.OK.toString())
                .message(jobService.createJob(username, jobRequest))
                .build();
        response.setTimestamp(new Date());

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "updating jobs. Ensure that you enter the job type from the following options FULLTIME, PARTTIME, CONTRACT, when updating the job type.")
    @PreAuthorize("hasAuthority('UPDATE_COMPANY')")
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateJob(HttpServletRequest servletRequest,
                                       @PathVariable("id") String id,
                                       @Valid @RequestBody UpdateJobRequest updateJobRequest) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Response response = Response.builder().url(servletRequest.getRequestURL().toString())
                .status(HttpStatus.OK.toString())
                .message(jobService.updateJob(updateJobRequest, username, id))
                .build();
        response.setTimestamp(new Date());
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Update the job status, if the job is not to be displayed anymore and update again if the job is to be displayed")
    @PreAuthorize("hasAuthority('UPDATE_COMPANY')")
    @PutMapping("/update/status-job/{id}")
    public ResponseEntity<?> updateStatusJob(HttpServletRequest servletRequest, @PathVariable String id) {
        Response response = Response.builder()
                .url(servletRequest.getRequestURL().toString())
                .status(HttpStatus.OK.toString())
                .message(jobService.updateStatusJob(id))
                .build();
        response.setTimestamp(new Date());

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Delete job based on Job ID")
    @PreAuthorize("hasAuthority('DELETE_COMPANY')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteJob(HttpServletRequest servletRequest,@PathVariable String id) {
        Response response = Response.builder().url(servletRequest.getRequestURL().toString())
                .status(HttpStatus.OK.toString())
                .message(jobService.deleteJob(id))
                .build();
        response.setTimestamp(new Date());
        return ResponseEntity.ok(response);
    }

}
