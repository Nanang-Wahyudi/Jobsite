package sib6.finalproject.Jobsite_ServerApp.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import sib6.finalproject.Jobsite_ServerApp.entity.Applicant;
import sib6.finalproject.Jobsite_ServerApp.entity.Feedback;
import sib6.finalproject.Jobsite_ServerApp.model.enums.StatusApplicantEnum;
import sib6.finalproject.Jobsite_ServerApp.model.request.CreateFeedbackRequest;
import sib6.finalproject.Jobsite_ServerApp.model.response.ApplicantResponse;
import sib6.finalproject.Jobsite_ServerApp.model.response.FeedbackResponse;
import sib6.finalproject.Jobsite_ServerApp.repository.ApplicantRepository;
import sib6.finalproject.Jobsite_ServerApp.repository.FeedbackRepository;
import sib6.finalproject.Jobsite_ServerApp.service.FeedbackService;

import java.util.Date;
import java.util.List;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private ApplicantRepository applicantRepository;

    @Autowired
    private ModelMapper modelMapper;


    @Transactional
    @Override
    public String createFeedback(String applicantId, String username, CreateFeedbackRequest feedbackRequest) {
        Applicant applicant = applicantRepository.findById(applicantId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Applicant Not Found with ID: " + applicantId));

        if (applicant.getStatus().equals(StatusApplicantEnum.ACCEPTED) && applicant.getUserDetail().getUser().getUsername().equals(username)) {
            boolean feedbackExists = feedbackRepository.existsByApplicantAndUserDetail(applicant, applicant.getUserDetail());

            if (!feedbackExists) {
                Feedback feedback = Feedback.builder()
                        .rating(feedbackRequest.getRating())
                        .comment(feedbackRequest.getComment())
                        .applicant(applicant)
                        .userDetail(applicant.getUserDetail())
                        .build();
                feedback.setPostDate(new Date());
                feedbackRepository.save(feedback);
                return "Feedback Successfully Given to Job Name: " + applicant.getJob().getTitle() + " with Username: " + username;
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Feedback Already Given For This Job");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unable To Provide Feedback, As You Have Not Yet Applied And Been Accepted For The Job");
        }
    }


    public FeedbackResponse toFeedbackResponse(Feedback feedback, List<ApplicantResponse> applicantResponses) {
        FeedbackResponse feedbackResponse = new FeedbackResponse();
        feedbackResponse.setApplicantResponses(applicantResponses);
        modelMapper.map(feedback, feedbackResponse);
        return feedbackResponse;
    }

}
