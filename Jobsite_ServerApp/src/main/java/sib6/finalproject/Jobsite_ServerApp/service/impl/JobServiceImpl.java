package sib6.finalproject.Jobsite_ServerApp.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import sib6.finalproject.Jobsite_ServerApp.entity.Company;
import sib6.finalproject.Jobsite_ServerApp.entity.Job;
import sib6.finalproject.Jobsite_ServerApp.entity.User;
import sib6.finalproject.Jobsite_ServerApp.model.enums.JobTypeEnum;
import sib6.finalproject.Jobsite_ServerApp.model.request.CreateJobRequest;
import sib6.finalproject.Jobsite_ServerApp.model.request.UpdateJobRequest;
import sib6.finalproject.Jobsite_ServerApp.repository.CompanyRepository;
import sib6.finalproject.Jobsite_ServerApp.repository.JobRepository;
import sib6.finalproject.Jobsite_ServerApp.repository.UserRepository;
import sib6.finalproject.Jobsite_ServerApp.service.JobService;

import java.util.Date;

@Service
public class JobServiceImpl implements JobService {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @SuppressWarnings("null")
    public Job findById(String id) {
        return jobRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Job is Not Found with ID: " + id
                ));
    }

    @Override
    public String createJob(CreateJobRequest jobRequest) {
        Job job = modelMapper.map(jobRequest, Job.class);

        User user = userRepository.findByUsername(jobRequest.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found Username: " + jobRequest.getUsername()));
        Company company = companyRepository.findById(user.getCompany().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Company Not Found with Username: " + jobRequest.getUsername()));

        try {
            job.setType(JobTypeEnum.valueOf(jobRequest.getType().toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Type Value: " + jobRequest.getType());
        }

        job.setCompany(company);
        job.setIsactive(Boolean.TRUE);
        job.setPostDate(new Date());

        jobRepository.save(job);

        return "Create Successfully with Job Title: " + jobRequest.getTitle();
    }

    @Override
    public String updateJob(UpdateJobRequest updateJobRequest, String username, String id) {
        Job job = this.findById(id);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Not Found Username: " + username));
        Company company = companyRepository.findById(user.getCompany().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Company Not Found with " +
                        "Username: " + username));

        try {
            job.setType(JobTypeEnum.valueOf(updateJobRequest.getType() != null && !updateJobRequest.getType().isEmpty()
                    ? updateJobRequest.getType().toUpperCase() : job.getType().name()));
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Type Value: " + updateJobRequest.getType());
        }
        job.setTitle(updateJobRequest.getTitle() != null && !updateJobRequest.getTitle().isEmpty()
                ? updateJobRequest.getTitle() : job.getTitle());
        job.setSalary(updateJobRequest.getSalary() != null && !updateJobRequest.getSalary().isEmpty()
                ? updateJobRequest.getSalary() : job.getSalary());
        job.setDescription(updateJobRequest.getDescription() != null && !updateJobRequest.getDescription().isEmpty()
                ? updateJobRequest.getDescription() : job.getDescription());
        job.setQualification(updateJobRequest.getQualification() != null && !updateJobRequest.getQualification().isEmpty()
                ? updateJobRequest.getQualification() : job.getQualification());
        job.setCompany(company);
        job.setIsactive(Boolean.TRUE);
        job.setPostDate(new Date());

        jobRepository.save(job);

        return "Update Successfully with Job Title: " + job.getTitle();
    }

    @Override
    public String updateStatusJob(String jobId) {
        Job job = findById(jobId);
        job.setIsactive(!job.getIsactive());
        jobRepository.save(job);
        return "Job Status Successfully Updated to: " + job.getIsactive() + ", with Job ID: " + jobId;
    }

    @Override
    public String deleteJob(String id) {
        Job job = this.findById(id);
        jobRepository.delete(job);
        return "Delete Successfully with Job Title: " + job.getTitle();
    }

}
