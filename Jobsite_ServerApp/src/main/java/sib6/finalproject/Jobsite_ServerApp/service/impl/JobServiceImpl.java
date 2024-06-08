package sib6.finalproject.Jobsite_ServerApp.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import sib6.finalproject.Jobsite_ServerApp.entity.Company;
import sib6.finalproject.Jobsite_ServerApp.entity.Job;
import sib6.finalproject.Jobsite_ServerApp.model.enums.JobTypeEnum;
import sib6.finalproject.Jobsite_ServerApp.model.request.CreateJobRequest;
import sib6.finalproject.Jobsite_ServerApp.repository.CompanyRepository;
import sib6.finalproject.Jobsite_ServerApp.repository.JobRepository;
import sib6.finalproject.Jobsite_ServerApp.service.JobService;

import java.util.Date;

@Service
public class JobServiceImpl implements JobService {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public String createJob(CreateJobRequest jobRequest) {
        Job job = modelMapper.map(jobRequest, Job.class);

        String companyId = companyRepository.findIdByName(jobRequest.getCompanyName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Company Not Found with Name: " + jobRequest.getCompanyName()));
        Company company = companyRepository.findById(String.valueOf(companyId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Company Not Found with Name: " + jobRequest.getCompanyName()));

        job.setType(JobTypeEnum.valueOf(jobRequest.getType().toUpperCase()));
        job.setCompany(company);
        job.setIsactive(Boolean.TRUE);
        job.setPostDate(new Date());

        jobRepository.save(job);

        return "Create Successfully with Job Title: " + jobRequest.getTitle();
    }

}
