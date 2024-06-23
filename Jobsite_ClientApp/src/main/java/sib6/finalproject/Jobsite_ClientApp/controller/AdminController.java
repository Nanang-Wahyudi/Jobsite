package sib6.finalproject.Jobsite_ClientApp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @GetMapping("/manage-talent")
    public String manageUser() {
        return "/admin/manage-user";
    }

    @GetMapping("/manage-company")
    public String manageCompany() {
        return "/admin/manage-company";
    }

}
