package sib6.finalproject.Jobsite_ClientApp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/job")
@Controller
public class JobController {

    @GetMapping()
    public String allJob(Model model) {
        model.addAttribute("isActive", "job");
        return "/job/job-list";
    }

    @GetMapping("/detail")
    public String jobDetail() {
        return "/job/job-detail";
    }

    @GetMapping("/add")
    public String createJob() {
        return "/job/job-add";
    }

    @GetMapping("/update")
    public String updateJob() {
        return "/job/job-update";
    }

}
