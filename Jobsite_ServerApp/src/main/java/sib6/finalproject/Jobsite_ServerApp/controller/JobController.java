package sib6.finalproject.Jobsite_ServerApp.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    @PostMapping("/create")
    public ResponseEntity<?> createJob(HttpServletRequest servletRequest, @Valid @RequestBody CreateJobRequest jobRequest) {
        Response response = Response.builder()
                .url(servletRequest.getRequestURL().toString())
                .status(HttpStatus.OK.toString())
                .message(jobService.createJob(jobRequest))
                .build();
        response.setTimestamp(new Date());

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "updating jobs. Ensure that you enter the job type from the following options FULLTIME, PARTTIME, CONTRACT, when updating the job type.")
    @PutMapping("/update/{username}/{id}")
    public ResponseEntity<?> updateJob(HttpServletRequest servletRequest,
                                       @PathVariable("username") String username,
                                       @PathVariable("id") String id,
                                       @Valid @RequestBody UpdateJobRequest updateJobRequest) {
        Response response = Response.builder().url(servletRequest.getRequestURL().toString())
                .status(HttpStatus.OK.toString())
                .message(jobService.updateJob(updateJobRequest, username, id))
                .build();
        response.setTimestamp(new Date());
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Update the job status, if the job is not to be displayed anymore and update again if the job is to be displayed")
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
