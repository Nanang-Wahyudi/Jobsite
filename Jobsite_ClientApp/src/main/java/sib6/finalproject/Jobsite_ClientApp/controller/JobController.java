package sib6.finalproject.Jobsite_ClientApp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import sib6.finalproject.Jobsite_ClientApp.model.response.JobDetailResponse;
import sib6.finalproject.Jobsite_ClientApp.model.response.JobResponse;
import sib6.finalproject.Jobsite_ClientApp.service.JobService;

@RequestMapping("/job")
@Controller
public class JobController {

    @Autowired
    private JobService jobService;

    @GetMapping()
    public String allJob(Model model) {
        List<JobResponse> jobResponses = jobService.getAllJob();
        model.addAttribute("jobs", jobResponses);

        model.addAttribute("isActive", "job");
        return "/job/job-list";
    }

    @GetMapping("/detail/{id}")
    public String jobDetail(Model model, @PathVariable("id") String id) {
        JobDetailResponse jobDetailResponse = jobService.getJobDetailById(id);
        model.addAttribute("jobDetail", jobDetailResponse);
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
