package sib6.finalproject.Jobsite_ClientApp.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import sib6.finalproject.Jobsite_ClientApp.model.response.CompanyDetailResponse;
import sib6.finalproject.Jobsite_ClientApp.model.response.CompanyResponse;
import sib6.finalproject.Jobsite_ClientApp.service.CompanyService;
import sib6.finalproject.Jobsite_ClientApp.util.security.AuthSessionUtil;

@RequestMapping("/company")
@Controller
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @GetMapping()
    public String allCompany(Model model) {
        List<CompanyResponse> companyResponses = companyService.getAllCompany();
        model.addAttribute("companies", companyResponses);

        model.addAttribute("isActive", "company");
        return "/company/company-list";
    }

    @GetMapping("/profile")
    public String companyProfile(Model model) {
        CompanyDetailResponse companyDetailResponse = companyService.getCompanyProfile();
        model.addAttribute("companyProfile", companyDetailResponse);
        return "/company/company-profile";
    }

    @GetMapping("/detail/{id}")
    public String companyDetail(Model model, @PathVariable("id") String id) {
        CompanyDetailResponse companyDetailResponse = companyService.getCompanyById(id);
        model.addAttribute("companyDetail", companyDetailResponse);
        return "/company/company-detail";
    }

    @GetMapping("/profile/update")
    public String companyProfileUpdate() {
        return "/company/update-profile";
    }

    @DeleteMapping("/delete")
    public String deleteAccount(HttpServletRequest request, HttpServletResponse response){
        companyService.deleteAccount();

        Authentication auth = AuthSessionUtil.getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }

        return "redirect:/login?logout";
    }
}
