package sib6.finalproject.Jobsite_ServerApp.service;

import sib6.finalproject.Jobsite_ServerApp.entity.Job;
import sib6.finalproject.Jobsite_ServerApp.model.request.CreateJobRequest;
import sib6.finalproject.Jobsite_ServerApp.model.request.UpdateJobRequest;

public interface JobService {

    Job findById(String id);

    String createJob(CreateJobRequest jobRequest);

    String updateJob(UpdateJobRequest updateJobRequest, String username, String id);

    String deleteJob(String id);
}
