package sib6.finalproject.Jobsite_ServerApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sib6.finalproject.Jobsite_ServerApp.model.request.CreateJobRequest;
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

}
