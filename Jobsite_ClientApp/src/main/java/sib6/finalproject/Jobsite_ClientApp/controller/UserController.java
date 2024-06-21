package sib6.finalproject.Jobsite_ClientApp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/talent")
@Controller
public class UserController {

    @GetMapping()
    public String allUser(Model model) {
        model.addAttribute("isActive", "talent");
        return "/user/user-list";
    }

    @GetMapping("/profile")
    public String userProfile() {
        return "/user/user-profile";
    }

    @GetMapping("/detail")
    public String userDetail() {
        return "/user/user-detail";
    }

}
