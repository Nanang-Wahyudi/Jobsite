package sib6.finalproject.Jobsite_ClientApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import sib6.finalproject.Jobsite_ClientApp.model.request.LoginRequest;
import sib6.finalproject.Jobsite_ClientApp.service.AuthService;
import sib6.finalproject.Jobsite_ClientApp.util.security.AuthSessionUtil;

@Controller
public class AuthController {

    @Autowired
    private AuthService authService;

    @GetMapping("/login")
    public String loginView(LoginRequest loginRequest) {
        Authentication auth = AuthSessionUtil.getAuthentication();

        if (auth instanceof AnonymousAuthenticationToken) {
            return "/auth/login";
        }
        return "redirect:/";
    }

    @PostMapping("/login")
    public String login(LoginRequest loginRequest) {
        Boolean auth = authService.login(loginRequest);
        if (!auth) {
            return "redirect:/login?error=true";
        }
        return "redirect:/";
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

    @GetMapping("/forgot-password")
    public String forgotPassword() {
        return "/auth/forgot-password";
    }

    @GetMapping("/update-password")
    public String updatePassword() {
        return "/auth/update-password";
    }

}
