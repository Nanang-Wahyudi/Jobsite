package sib6.finalproject.Jobsite_ServerApp.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import sib6.finalproject.Jobsite_ServerApp.entity.*;
import sib6.finalproject.Jobsite_ServerApp.model.enums.RoleEnum;
import sib6.finalproject.Jobsite_ServerApp.model.request.UpdateCompanyDetailRequest;
import sib6.finalproject.Jobsite_ServerApp.model.response.*;
import sib6.finalproject.Jobsite_ServerApp.repository.CompanyRepository;
import sib6.finalproject.Jobsite_ServerApp.repository.UserRepository;
import sib6.finalproject.Jobsite_ServerApp.service.CompanyService;
import sib6.finalproject.Jobsite_ServerApp.service.media.CloudinaryService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CloudinaryService cloudinaryService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ApplicantServiceImpl applicantServiceImpl;

    @Autowired
    private FeedbackServiceImpl feedbackServiceImpl;

    @Autowired
    private JobServiceImpl jobServiceImpl;


    @Override
    public List<CompanyResponse> getAllCompany() {
        List<Company> companies = companyRepository.findAllByRoleName(RoleEnum.COMPANY);
        return companies.stream()
                .map(this::toCompanyResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CompanyDetailResponse getCompanyDetailById(String id) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Company Not Found with ID: " + id));

        List<Job> jobs = company.getJobs();
        List<FeedbackResponse> feedbackResponses = new ArrayList<>();

        for (Job job : jobs) {
            List<Applicant> applicants = job.getApplicants();

            for (Applicant applicant : applicants) {
                List<Feedback> feedbacks = applicant.getFeedbacks();

                for (Feedback feedback : feedbacks) {
                    ApplicantResponse applicantResponse = applicantServiceImpl.toApplicantResponse(applicant);
                    FeedbackResponse feedbackResponse = feedbackServiceImpl.toFeedbackResponse(feedback, Collections.singletonList(applicantResponse));
                    feedbackResponses.add(feedbackResponse);
                }
            }
        }

        List<JobResponse> jobResponses = company.getJobs().stream()
                .filter(Job::getIsactive)
                .map(job -> jobServiceImpl.toJobResponse(job))
                .collect(Collectors.toList());

        return this.toCompanyDetailResponse(company, feedbackResponses, jobResponses);
    }

    @Override
    public CompanyDetailResponse getCompanyDetailProfile(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found with Username: " + username));
        Company company = companyRepository.findById(user.getCompany().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Company Not Found with ID: " + user.getCompany().getId()));

        List<Job> jobs = company.getJobs();
        List<FeedbackResponse> feedbackResponses = new ArrayList<>();

        for (Job job : jobs) {
            List<Applicant> applicants = job.getApplicants();

            for (Applicant applicant : applicants) {
                List<Feedback> feedbacks = applicant.getFeedbacks();

                for (Feedback feedback : feedbacks) {
                    ApplicantResponse applicantResponse = applicantServiceImpl.toApplicantResponse(applicant);
                    FeedbackResponse feedbackResponse = feedbackServiceImpl.toFeedbackResponse(feedback, Collections.singletonList(applicantResponse));
                    feedbackResponses.add(feedbackResponse);
                }
            }
        }

        List<JobResponse> jobResponses = company.getJobs().stream()
                .map(job -> jobServiceImpl.toJobResponse(job))
                .collect(Collectors.toList());

        return this.toCompanyDetailResponse(company, feedbackResponses, jobResponses);
    }

    @Override
    public String updateCompanyDetail(String username, UpdateCompanyDetailRequest companyDetailRequest) {
        String pictureUrl = null, bannerUrl = null;

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found with Username: " + username));
        Company company = user.getCompany();

        if (company == null) {
            company = new Company();
            company.setUser(user);
        }

        MultipartFile pictureFile = companyDetailRequest.getPicture();
        if (pictureFile != null && !pictureFile.isEmpty()) {
            if (!isImageFile(pictureFile)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid file type for picture. Only image files are allowed.");
            }
            try {
                if (company.getPicture() != null) cloudinaryService.deleteFile(company.getPicture());
                pictureUrl = cloudinaryService.uploadFile(pictureFile);
            } catch (IOException e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error uploading picture: " + e.getMessage());
            }
        }

        MultipartFile bannerFile = companyDetailRequest.getBanner();
        if (bannerFile != null && !bannerFile.isEmpty()) {
            if (!isImageFile(bannerFile)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid file type for banner. Only image files are allowed.");
            }
            try {
                if (company.getBanner() != null) cloudinaryService.deleteFile(company.getBanner());
                bannerUrl = cloudinaryService.uploadFile(bannerFile);
            } catch (IOException e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error uploading banner: " + e.getMessage());
            }
        }

        company.setName(companyDetailRequest.getName() != null && !companyDetailRequest.getName().isEmpty()
                ? companyDetailRequest.getName() : company.getName());
        company.setEmail(companyDetailRequest.getEmail() != null && !companyDetailRequest.getEmail().isEmpty()
                ? companyDetailRequest.getEmail() : company.getEmail());
        company.setAddress(companyDetailRequest.getAddress() != null && !companyDetailRequest.getAddress().isEmpty()
                ? companyDetailRequest.getAddress() : company.getAddress());
        company.setPicture(pictureUrl != null ? pictureUrl : company.getPicture());
        company.setBanner(bannerUrl != null ? bannerUrl : company.getBanner());
        company.setDescription(companyDetailRequest.getDescription() != null && !companyDetailRequest.getDescription().isEmpty()
                ? companyDetailRequest.getDescription() : company.getDescription());

        companyRepository.save(company);

        return "Company Detail Successfully Updated with Username: " + username;
    }

    @Override
    public String deleteCompanyByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found with Username: " + username));
        Company company = user.getCompany();

        companyRepository.delete(company);
        return "Delete Company Successfully with Username: " + username;
    }


    public CompanyResponse toCompanyResponse(Company company) {
        CompanyResponse companyResponse = new CompanyResponse();
        companyResponse.setUrlPicture(company.getPicture());
        modelMapper.map(company, companyResponse);
        return companyResponse;
    }

    public CompanyDetailResponse toCompanyDetailResponse(Company company, List<FeedbackResponse> feedbackResponses, List<JobResponse> jobResponses) {
        CompanyDetailResponse companyDetailResponse = new CompanyDetailResponse();
        companyDetailResponse.setUrlPicture(company.getPicture());
        companyDetailResponse.setUrlBanner(company.getBanner());
        companyDetailResponse.setFeedbackResponses(feedbackResponses);
        companyDetailResponse.setJobResponses(jobResponses);
        modelMapper.map(company, companyDetailResponse);
        return companyDetailResponse;
    }

    private boolean isImageFile(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType != null && (contentType.startsWith("image/jpeg") || contentType.startsWith("image/png") || contentType.startsWith("image/gif"));
    }

}
