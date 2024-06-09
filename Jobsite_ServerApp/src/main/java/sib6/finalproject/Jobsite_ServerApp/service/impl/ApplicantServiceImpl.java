package sib6.finalproject.Jobsite_ServerApp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import sib6.finalproject.Jobsite_ServerApp.entity.Applicant;
import sib6.finalproject.Jobsite_ServerApp.entity.Job;
import sib6.finalproject.Jobsite_ServerApp.entity.User;
import sib6.finalproject.Jobsite_ServerApp.entity.UserDetail;
import sib6.finalproject.Jobsite_ServerApp.model.enums.StatusApplicantEnum;
import sib6.finalproject.Jobsite_ServerApp.repository.ApplicantRepository;
import sib6.finalproject.Jobsite_ServerApp.repository.JobRepository;
import sib6.finalproject.Jobsite_ServerApp.repository.UserDetailRepository;
import sib6.finalproject.Jobsite_ServerApp.repository.UserRepository;
import sib6.finalproject.Jobsite_ServerApp.service.ApplicantService;

import java.io.IOException;
import java.util.Date;
import java.util.Objects;

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


}
