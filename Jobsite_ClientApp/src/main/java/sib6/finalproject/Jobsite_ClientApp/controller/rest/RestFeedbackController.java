package sib6.finalproject.Jobsite_ClientApp.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import sib6.finalproject.Jobsite_ClientApp.model.request.CreateFeedbackRequest;
import sib6.finalproject.Jobsite_ClientApp.model.response.FeedbackResponse;
import sib6.finalproject.Jobsite_ClientApp.service.FeedbackService;

@RestController
public class RestFeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @PostMapping("/api/client/create-feedback/{id}")
    public FeedbackResponse createFeedback(@RequestBody CreateFeedbackRequest feedbackRequest,
            @PathVariable("id") String applicantId) {
        return feedbackService.createFeedback(feedbackRequest, applicantId);
    }

}
