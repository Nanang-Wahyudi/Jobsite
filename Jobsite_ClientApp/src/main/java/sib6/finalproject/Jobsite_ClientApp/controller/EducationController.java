package sib6.finalproject.Jobsite_ClientApp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EducationController {

    @GetMapping("/talent/profile/update-education")
    public String updateEducation() {
        return "/education/education-update";
    }

}
