package sib6.finalproject.Jobsite_ClientApp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @GetMapping("/manage-talent")
    public String manageUser(Model model) {
        model.addAttribute("isActive", "manage");
        return "/admin/manage-user";
    }

    @GetMapping("/manage-company")
    public String manageCompany(Model model) {
        model.addAttribute("isActive", "manage");
        return "/admin/manage-company";
    }

}
