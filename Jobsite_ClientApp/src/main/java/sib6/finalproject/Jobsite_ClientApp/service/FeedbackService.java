package sib6.finalproject.Jobsite_ClientApp.service;

import sib6.finalproject.Jobsite_ClientApp.model.request.CreateFeedbackRequest;
import sib6.finalproject.Jobsite_ClientApp.model.response.FeedbackResponse;

public interface FeedbackService {

    FeedbackResponse createFeedback(CreateFeedbackRequest feedbackRequest, String applicantId);

}
