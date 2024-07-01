package sib6.finalproject.Jobsite_ClientApp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import sib6.finalproject.Jobsite_ClientApp.model.response.CompanyResponse;
import sib6.finalproject.Jobsite_ClientApp.model.response.JobResponse;
import sib6.finalproject.Jobsite_ClientApp.service.CompanyService;
import sib6.finalproject.Jobsite_ClientApp.service.JobService;

@Controller
public class HomeController {

    @Autowired
    private JobService jobService;

    @Autowired
    private CompanyService companyService;

    @GetMapping("/")
    public String home(Model model) {
        List<CompanyResponse> companyResponses = companyService.getAllCompany();
        model.addAttribute("companies", companyResponses);

        List<JobResponse> jobResponses = jobService.getAllJob();
        model.addAttribute("jobs", jobResponses);

        model.addAttribute("isActive", "home");
        return "/home/home";
    }

}
