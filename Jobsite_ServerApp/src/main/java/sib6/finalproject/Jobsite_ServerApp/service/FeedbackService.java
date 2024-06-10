package sib6.finalproject.Jobsite_ServerApp.service;

import sib6.finalproject.Jobsite_ServerApp.model.request.CreateFeedbackRequest;

public interface FeedbackService {

    String createFeedback(String applicantId, String username, CreateFeedbackRequest feedbackRequest);

}
