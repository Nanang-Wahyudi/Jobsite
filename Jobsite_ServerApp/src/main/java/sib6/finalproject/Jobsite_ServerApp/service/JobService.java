package sib6.finalproject.Jobsite_ServerApp.service;

import sib6.finalproject.Jobsite_ServerApp.entity.Job;
import sib6.finalproject.Jobsite_ServerApp.model.request.CreateJobRequest;
import sib6.finalproject.Jobsite_ServerApp.model.request.UpdateJobRequest;
import sib6.finalproject.Jobsite_ServerApp.model.response.JobDetailResponse;
import sib6.finalproject.Jobsite_ServerApp.model.response.JobResponse;

import java.util.List;

public interface JobService {

    Job findById(String id);

    List<JobResponse> getAllJob();

    JobDetailResponse getJobDetailById(String id);

    String createJob(CreateJobRequest jobRequest);

    String updateJob(UpdateJobRequest updateJobRequest, String username, String id);

    String updateStatusJob(String jobId);

    String deleteJob(String id);
}
