package sib6.finalproject.Jobsite_ClientApp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class EducationController {

    @GetMapping("/talent/profile/update-education/{id}")
    public String updateEducation(Model model, @PathVariable("id") String id) {
        return "/education/education-update";
    }

}
