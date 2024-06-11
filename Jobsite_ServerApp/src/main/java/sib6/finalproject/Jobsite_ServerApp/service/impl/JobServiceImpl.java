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
        Job result = jobRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Job is not found!!!"
                ));
        return result;
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
    public String updateJob(UpdateJobRequest updateJobRequest,String id) {
        this.findById(id);
        Job job = modelMapper.map(updateJobRequest, Job.class);

        User user = userRepository.findByUsername(updateJobRequest.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Not Found Username: " + updateJobRequest.getUsername()));
        Company company = companyRepository.findById(user.getCompany().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Company Not Found with " +
                        "Username: " + updateJobRequest.getUsername()));
        job.setType(JobTypeEnum.valueOf(updateJobRequest.getType().toUpperCase()));
        job.setCompany(company);
        job.setIsactive(Boolean.TRUE);
        job.setPostDate(new Date());
        jobRepository.save(job);
        return "Update Successfully with Job Title: "+updateJobRequest.getTitle();
    }

    @Override
    public String delete(String id) {
        Job job = this.findById(id);
        jobRepository.delete(job);
        return "Delete Successfully with Job Title: "+job.getTitle();
    }

}
