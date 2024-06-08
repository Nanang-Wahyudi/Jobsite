package sib6.finalproject.Jobsite_ServerApp.service;

import sib6.finalproject.Jobsite_ServerApp.model.request.CreateJobRequest;

public interface JobService {

    String createJob(CreateJobRequest jobRequest);

}
