package sib6.finalproject.Jobsite_ServerApp.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
import java.util.Collections;
import java.util.Comparator;
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


    @Transactional(readOnly = true)
    @Override
    public List<CompanyResponse> getAllCompany() {
        List<Company> companies = companyRepository.findAllByRoleName(RoleEnum.COMPANY);
        return companies.stream()
                .map(this::toCompanyResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public List<CompanyAdminResponse> getAllCompanyForAdmin() {
        List<Company> companies = companyRepository.findAllByRoleName(RoleEnum.COMPANY);
        return companies.stream()
                .map(this::toCompanyAdminResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public CompanyDetailResponse getCompanyDetailById(String id) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Company Not Found with ID: " + id));

        List<FeedbackResponse> feedbackResponses = company.getJobs().stream()
                .flatMap(job -> job.getApplicants().stream())
                .flatMap(applicant -> applicant.getFeedbacks().stream()
                        .map(feedback -> feedbackServiceImpl.toFeedbackResponse(feedback, Collections.singletonList(applicantServiceImpl.toApplicantResponse(applicant)))))
                .sorted(Comparator.comparing(FeedbackResponse::getPostDate).reversed())
                .collect(Collectors.toList());

        List<JobResponse> jobResponses = company.getJobs().stream()
                .filter(Job::getIsActive)
                .sorted(Comparator.comparing(Job::getPostDate).reversed())
                .map(jobServiceImpl::toJobResponse)
                .collect(Collectors.toList());

        return this.toCompanyDetailResponse(company, feedbackResponses, jobResponses);
    }

    @Transactional(readOnly = true)
    @Override
    public CompanyDetailResponse getCompanyDetailProfile(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found with Username: " + username));
        Company company = companyRepository.findById(user.getCompany().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Company Not Found with ID: " + user.getCompany().getId()));

        List<FeedbackResponse> feedbackResponses = company.getJobs().stream()
                .flatMap(job -> job.getApplicants().stream())
                .flatMap(applicant -> applicant.getFeedbacks().stream()
                        .map(feedback -> feedbackServiceImpl.toFeedbackResponse(feedback, Collections.singletonList(applicantServiceImpl.toApplicantResponse(applicant)))))
                .sorted(Comparator.comparing(FeedbackResponse::getPostDate).reversed())
                .collect(Collectors.toList());

        List<JobResponse> jobResponses = company.getJobs().stream()
                .sorted(Comparator.comparing(Job::getPostDate).reversed())
                .map(jobServiceImpl::toJobResponse)
                .collect(Collectors.toList());

        return this.toCompanyDetailResponse(company, feedbackResponses, jobResponses);
    }

    @Transactional
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

    @Transactional
    @Override
    public String deleteCompanyByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found with Username: " + username));
        Company company = user.getCompany();

        companyRepository.delete(company);
        return "Delete Company Successfully with Username: " + username;
    }

    @Transactional
    @Override
    public String deleteCompanyByIdForAdmin(String id) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Company Not Found with ID: " + id));

        companyRepository.delete(company);
        return "Delete Company Successfully with ID: " + id;
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

    public CompanyAdminResponse toCompanyAdminResponse(Company company) {
        CompanyAdminResponse companyAdminResponse = new CompanyAdminResponse();
        companyAdminResponse.setUrlPicture(company.getPicture());
        companyAdminResponse.setUsername(company.getUser().getUsername());
        modelMapper.map(company, companyAdminResponse);
        return companyAdminResponse;
    }

    private boolean isImageFile(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType != null && (contentType.startsWith("image/jpeg") || contentType.startsWith("image/png") || contentType.startsWith("image/gif"));
    }

}
