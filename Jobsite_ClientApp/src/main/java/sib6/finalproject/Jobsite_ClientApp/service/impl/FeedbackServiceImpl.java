package sib6.finalproject.Jobsite_ClientApp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import sib6.finalproject.Jobsite_ClientApp.model.request.CreateFeedbackRequest;
import sib6.finalproject.Jobsite_ClientApp.model.response.FeedbackResponse;
import sib6.finalproject.Jobsite_ClientApp.service.FeedbackService;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${server.base.url}/feedback")
    private String url;

    @Override
    public FeedbackResponse createFeedback(CreateFeedbackRequest feedbackRequest, String applicantId) {
        ResponseEntity<FeedbackResponse> response = restTemplate
                .exchange(url.concat("/create/" + applicantId),
                        HttpMethod.POST,
                        new HttpEntity<>(feedbackRequest),
                        FeedbackResponse.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            throw new RuntimeException("Failed to Create Feedback");
        }
    }

}
