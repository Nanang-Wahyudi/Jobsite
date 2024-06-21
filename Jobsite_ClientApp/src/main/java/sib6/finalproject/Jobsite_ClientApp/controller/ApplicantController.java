package sib6.finalproject.Jobsite_ClientApp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ApplicantController {

    @GetMapping("/applicant")
    public String allApplicant() {
        return "/applicant/applicant-list";
    }

    @GetMapping("/applicant-history")
    public String applicantHistory() {
        return "/applicant/applicant-history";
    }

    @GetMapping("/applicant/detail")
    public String applicantDetail() {
        return "/applicant/applicant-detail";
    }

}
