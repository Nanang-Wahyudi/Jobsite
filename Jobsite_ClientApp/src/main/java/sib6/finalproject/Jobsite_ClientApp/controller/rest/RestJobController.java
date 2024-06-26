package sib6.finalproject.Jobsite_ClientApp.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sib6.finalproject.Jobsite_ClientApp.model.request.CreateJobRequest;
import sib6.finalproject.Jobsite_ClientApp.model.request.UpdateJobRequest;
import sib6.finalproject.Jobsite_ClientApp.model.response.JobDetailResponse;
import sib6.finalproject.Jobsite_ClientApp.model.response.JobResponse;
import sib6.finalproject.Jobsite_ClientApp.service.JobService;

@RequestMapping("/api/client/job")
@RestController
public class RestJobController {

    @Autowired
    private JobService jobService;

    @GetMapping("/detail/{id}")
    public JobDetailResponse getJobDetailById(@PathVariable("id") String id){
        return jobService.getJobDetailById(id);
    }

    @PostMapping("/create")
    public JobResponse addJob(@RequestBody CreateJobRequest jobRequest) {
        return jobService.addJob(jobRequest);
    }

    @PutMapping("/update/{id}")
    public JobResponse updateJob(@PathVariable("id") String id, @RequestBody UpdateJobRequest jobRequest){
        return jobService.updateJob(id, jobRequest);
    }

    @PutMapping("/update/status/{id}")
    public JobResponse updateStatusJob(@PathVariable("id") String id){
        return jobService.updateStatusJob(id);
    }

    @DeleteMapping("/delete/{id}")
    public JobResponse deleteJob(@PathVariable("id") String id){
        return jobService.deleteJob(id);
    }
    
}
