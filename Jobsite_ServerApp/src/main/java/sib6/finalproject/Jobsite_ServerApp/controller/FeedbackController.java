package sib6.finalproject.Jobsite_ServerApp.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import sib6.finalproject.Jobsite_ServerApp.model.request.CreateFeedbackRequest;
import sib6.finalproject.Jobsite_ServerApp.model.response.Response;
import sib6.finalproject.Jobsite_ServerApp.service.FeedbackService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;

@RequestMapping("/api/feedback")
@RestController
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;


    @Operation(summary = "Provide feedback to the company on the job that has been received")
    @PreAuthorize("hasAuthority('CREATE_USER')")
    @PostMapping("/create/{applicantId}")
    public ResponseEntity<?> createFeedback(HttpServletRequest servletRequest, @PathVariable("applicantId") String applicantId, @Valid @RequestBody CreateFeedbackRequest feedbackRequest) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Response response = Response.builder()
                .url(servletRequest.getRequestURL().toString())
                .status(HttpStatus.OK.toString())
                .message(feedbackService.createFeedback(applicantId, username, feedbackRequest))
                .build();
        response.setTimestamp(new Date());

        return ResponseEntity.ok(response);
    }

}
