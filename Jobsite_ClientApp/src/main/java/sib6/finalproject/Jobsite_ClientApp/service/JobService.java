package sib6.finalproject.Jobsite_ClientApp.service;

import java.util.List;

import sib6.finalproject.Jobsite_ClientApp.model.request.CreateJobRequest;
import sib6.finalproject.Jobsite_ClientApp.model.response.JobDetailResponse;
import sib6.finalproject.Jobsite_ClientApp.model.response.JobResponse;

public interface JobService {

    List<JobResponse> getAllJob();

    JobDetailResponse getJobDetailById(String id);

    JobResponse addJob(CreateJobRequest jobRequest);

}
