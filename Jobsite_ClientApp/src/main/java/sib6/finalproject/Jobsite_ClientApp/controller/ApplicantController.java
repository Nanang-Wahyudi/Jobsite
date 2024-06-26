package sib6.finalproject.Jobsite_ClientApp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import sib6.finalproject.Jobsite_ClientApp.model.request.UpdateStatusApplicantRequest;
import sib6.finalproject.Jobsite_ClientApp.model.response.ApplicantDetailResponse;
import sib6.finalproject.Jobsite_ClientApp.model.response.ApplicantResponse;
import sib6.finalproject.Jobsite_ClientApp.service.ApplicantService;
import org.springframework.web.bind.annotation.PutMapping;

@Controller
public class ApplicantController {

    @Autowired
    private ApplicantService applicantService;

    @GetMapping("/applicant")
    public String allApplicant(Model model) {
        List<ApplicantResponse> applicantResponses = applicantService.getAllApplicant();
        model.addAttribute("applicants", applicantResponses);

        model.addAttribute("isActive", "applicant");
        return "/applicant/applicant-list";
    }

    @GetMapping("/applicant-history")
    public String applicantHistory(Model model) {
        List<ApplicantResponse> applicantResponses = applicantService.getAllApplicantHistory();
        model.addAttribute("applicantHistory", applicantResponses);

        model.addAttribute("isActive", "history");
        return "/applicant/applicant-history";
    }

    @GetMapping("/applicant/detail/{id}")
    public String applicantDetail(Model model, @PathVariable("id") String id) {
        ApplicantDetailResponse detailResponse = applicantService.getApplicantDetailById(id);
        model.addAttribute("applicantDetail", detailResponse);

        // For updating applicant status
        model.addAttribute("statusApplicantRequest", new UpdateStatusApplicantRequest());

        return "/applicant/applicant-detail";
    }

    @PutMapping("/applicant-status/{id}")
    public String updateApplicantStatus(@PathVariable("id") String id,
            UpdateStatusApplicantRequest statusApplicantRequest) {

        applicantService.updateApplicantStatus(id, statusApplicantRequest);
        return "redirect:/applicant/detail/" + id;
    }
}
