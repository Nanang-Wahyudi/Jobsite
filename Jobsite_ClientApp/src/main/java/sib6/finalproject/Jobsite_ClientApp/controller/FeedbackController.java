package sib6.finalproject.Jobsite_ClientApp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FeedbackController {

    @GetMapping("/create-feedback")
    public String createFeedback() {
        return "/feedback/create-feedback";
    }

}
