package sib6.finalproject.Jobsite_ServerApp.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import sib6.finalproject.Jobsite_ServerApp.entity.*;
import sib6.finalproject.Jobsite_ServerApp.model.enums.StatusApplicantEnum;
import sib6.finalproject.Jobsite_ServerApp.model.request.UpdateStatusApplicantRequest;
import sib6.finalproject.Jobsite_ServerApp.model.response.*;
import sib6.finalproject.Jobsite_ServerApp.repository.*;
import sib6.finalproject.Jobsite_ServerApp.service.ApplicantService;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ApplicantServiceImpl implements ApplicantService {

    @Autowired
    private ApplicantRepository applicantRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDetailRepository userDetailRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private JobServiceImpl jobServiceImpl;

    @Autowired
    private UserDetailServiceImpl userDetailServiceImpl;

    @Autowired
    private SkillServiceImpl skillServiceImpl;


    @Override
    public Applicant findById(String id) {
        return applicantRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Applicant Not Found with ID: " + id));
    }

    @Override
    public List<ApplicantResponse> getAllApplicant(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found with Username: " + username));
        Company company = user.getCompany();

        List<Applicant> allApplicants = new ArrayList<>();

        for (Job job : company.getJobs()) {
            allApplicants.addAll(job.getApplicants());
        }

        return allApplicants.stream()
                .sorted(Comparator.comparing(Applicant::getApplicantDate).reversed())
                .map(this::toApplicantResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ApplicantDetailResponse getApplicantById(String id) {
        Applicant applicant = this.findById(id);

        Job job = applicant.getJob();
        JobResponse jobResponse = jobServiceImpl.toJobResponse(job);

        UserDetail userDetail = applicant.getUserDetail();

        List<SkillResponse> skillResponses = userDetail.getSkills().stream()
                .map(skill -> skillServiceImpl.toSkillResponse(skill))
                .collect(Collectors.toList());

        UserResponse userResponse = userDetailServiceImpl.toUserResponse(userDetail, skillResponses);

        return this.toApplicantDetailResponse(applicant, jobResponse, userResponse);
    }

    @Override
    public List<ApplicantResponse> getAllApplicantHistoryUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found with Username: " + username));
        UserDetail userDetail = user.getUserDetail();

        List<Applicant> applicants = userDetail.getApplicants();

        return applicants.stream()
                .sorted(Comparator.comparing(Applicant::getApplicantDate).reversed())
                .map(this::toApplicantHistoryResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public String createApplicant(MultipartFile file, String jobId, String username) throws IOException {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

        if (!Objects.equals(file.getContentType(), "application/pdf")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Only PDF files are allowed");
        }

        if (fileName.contains("..")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Filename contains invalid path sequence " + fileName);
        }
        if (file.getSize() > (5 * 1024 * 1024)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "File size exceeds maximum limit of 5MB");
        }

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found Username: " + username));
        UserDetail userDetail = userDetailRepository.findById(user.getUserDetail().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User Detail Not Found with Username: " + username));
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Job Not Found with ID: " + jobId));

        List<Applicant> existingApplicants = applicantRepository.findByUserDetailAndJob(userDetail, job);
        boolean hasActiveApplication = existingApplicants.stream()
                .anyMatch(applicant -> !applicant.getStatus().equals(StatusApplicantEnum.REJECTED));
        if (hasActiveApplication) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You have already applied for this job");
        }

        Applicant applicant = Applicant.builder()
                .cv(file.getBytes())
                .status(StatusApplicantEnum.PROCESSED)
                .job(job)
                .userDetail(userDetail)
                .build();
        applicant.setApplicantDate(new Date());

        try {
            applicantRepository.save(applicant);
        } catch (DataAccessException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Database access error: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred: " + e.getMessage(), e);
        }

        return "Applicant Successfully Applied";
    }

    @Override
    public String updateStatusApplicant(String applicantId, UpdateStatusApplicantRequest statusApplicantRequest) {
        Applicant applicant = applicantRepository.findById(applicantId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Applicant Not Found with ID: " + applicantId));

        if (statusApplicantRequest.getStatus() != null && !statusApplicantRequest.getStatus().isEmpty()) {
            try {
                StatusApplicantEnum status = StatusApplicantEnum.valueOf(statusApplicantRequest.getStatus().toUpperCase());
                applicant.setStatus(status);
                applicantRepository.save(applicant);
            } catch (IllegalArgumentException e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Status Value: " + statusApplicantRequest.getStatus());
            }
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Status is required and cannot be empty");
        }

        return "Applicant Status Successfully Updated with ID: " + applicantId;
    }


    public ApplicantResponse toApplicantResponse(Applicant applicant) {
        ApplicantResponse applicantResponse = new ApplicantResponse();
        applicantResponse.setUrlPicture(applicant.getUserDetail().getPicture());
        applicantResponse.setCompanyName(applicant.getJob().getCompany().getName());
        applicantResponse.setNameUser(applicant.getUserDetail().getName());
        applicantResponse.setTitleJob(applicant.getJob().getTitle());
        modelMapper.map(applicant, applicantResponse);
        return applicantResponse;
    }

    public ApplicantResponse toApplicantHistoryResponse(Applicant applicant) {
        ApplicantResponse applicantResponse = new ApplicantResponse();
        applicantResponse.setUrlPicture(applicant.getJob().getCompany().getPicture());
        applicantResponse.setCompanyName(applicant.getJob().getCompany().getName());
        applicantResponse.setNameUser(applicant.getUserDetail().getName());
        applicantResponse.setTitleJob(applicant.getJob().getTitle());
        modelMapper.map(applicant, applicantResponse);
        return applicantResponse;
    }

    public ApplicantDetailResponse toApplicantDetailResponse(Applicant applicant, JobResponse jobResponse, UserResponse userResponse) {
        ApplicantDetailResponse applicantDetailResponse = new ApplicantDetailResponse();
        applicantDetailResponse.setJobResponse(jobResponse);
        applicantDetailResponse.setUserResponse(userResponse);
        modelMapper.map(applicant, applicantDetailResponse);
        return applicantDetailResponse;
    }

}
