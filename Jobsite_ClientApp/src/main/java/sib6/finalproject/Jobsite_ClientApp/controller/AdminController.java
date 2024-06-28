package sib6.finalproject.Jobsite_ClientApp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

import sib6.finalproject.Jobsite_ClientApp.service.AdminService;
import sib6.finalproject.Jobsite_ClientApp.util.security.AuthSessionUtil;

@Controller
public class AdminController {

    @Autowired
    private AdminService adminService;

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

    @DeleteMapping("/admin/delete")
    public String deleteUserAccount(HttpServletRequest request, HttpServletResponse response) {
        adminService.deleteAdminAccount();

        Authentication auth = AuthSessionUtil.getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }

        return "redirect:/login?logout";
    }
}
