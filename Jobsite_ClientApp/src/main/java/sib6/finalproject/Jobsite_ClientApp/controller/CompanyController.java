package sib6.finalproject.Jobsite_ClientApp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/company")
@Controller
public class CompanyController {

    @GetMapping()
    public String allCompany(Model model) {
        model.addAttribute("isActive", "company");
        return "/company/company-list";
    }

    @GetMapping("/profile")
    public String companyProfile() {
        return "/company/company-profile";
    }

    @GetMapping("/detail")
    public String companyDetail() {
        return "/company/company-detail";
    }

}
