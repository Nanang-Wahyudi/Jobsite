package sib6.finalproject.Jobsite_ServerApp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import sib6.finalproject.Jobsite_ServerApp.entity.Applicant;
import sib6.finalproject.Jobsite_ServerApp.entity.Feedback;
import sib6.finalproject.Jobsite_ServerApp.model.enums.StatusApplicantEnum;
import sib6.finalproject.Jobsite_ServerApp.model.request.CreateFeedbackRequest;
import sib6.finalproject.Jobsite_ServerApp.repository.ApplicantRepository;
import sib6.finalproject.Jobsite_ServerApp.repository.FeedbackRepository;
import sib6.finalproject.Jobsite_ServerApp.service.FeedbackService;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private ApplicantRepository applicantRepository;

    @Override
    public String createFeedback(String applicantId, String username, CreateFeedbackRequest feedbackRequest) {
        Applicant applicant = applicantRepository.findById(applicantId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Applicant Not Found with ID: " + applicantId));

        if (applicant.getStatus().equals(StatusApplicantEnum.ACCEPTED) && applicant.getUserDetail().getUser().getUsername().equals(username)) {
            Feedback feedback = Feedback.builder()
                    .rating(feedbackRequest.getRating())
                    .comment(feedbackRequest.getComment())
                    .applicant(applicant)
                    .userDetail(applicant.getUserDetail())
                    .build();
            feedbackRepository.save(feedback);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unable To Provide Feedback, As You Have Not Yet Applied And Been Accepted For The Job");
        }

        return "Feedback Successfully Given to Job Name: " + applicant.getJob().getTitle() + " with Username: " + username;
    }


}
