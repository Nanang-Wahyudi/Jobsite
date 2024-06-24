package sib6.finalproject.Jobsite_ClientApp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import sib6.finalproject.Jobsite_ClientApp.model.response.UserDetailResponse;
import sib6.finalproject.Jobsite_ClientApp.model.response.UserResponse;
import sib6.finalproject.Jobsite_ClientApp.service.UserService;

@RequestMapping("/talent")
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping()
    public String allUser(Model model) {
        List<UserResponse> userResponses = userService.getAllUser();
        model.addAttribute("users", userResponses);

        model.addAttribute("isActive", "talent");
        return "/user/user-list";
    }

    @GetMapping("/profile")
    public String userProfile() {
        return "/user/user-profile";
    }

    @GetMapping("/detail/{id}")
    public String userDetail(Model model, @PathVariable("id") String id) {
        UserDetailResponse userDetailResponse = userService.getUserById(id);
        model.addAttribute("userDetail", userDetailResponse);
        return "/user/user-detail";
    }

    @GetMapping("/profile/update")
    public String userProfileUpdate() {
        return "/user/update-profile";
    }

}
