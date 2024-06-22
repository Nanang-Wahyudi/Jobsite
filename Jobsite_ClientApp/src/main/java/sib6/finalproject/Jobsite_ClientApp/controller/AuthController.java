package sib6.finalproject.Jobsite_ClientApp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    @GetMapping("/login")
    public String login() {
        return "/auth/login";
    }

    @GetMapping("/register")
    public String register() {
        return "/auth/register-user";
    }

    @GetMapping("/register-company")
    public String registerCompany() {
        return "/auth/register-company";
    }

    @GetMapping("/register-admin")
    public String registerAdmin() {
        return "/auth/register-admin";
    }

}
