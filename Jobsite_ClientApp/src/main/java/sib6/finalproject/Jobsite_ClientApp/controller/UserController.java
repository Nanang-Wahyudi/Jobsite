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

import sib6.finalproject.Jobsite_ClientApp.model.response.UserDetailResponse;
import sib6.finalproject.Jobsite_ClientApp.model.response.UserResponse;
import sib6.finalproject.Jobsite_ClientApp.service.UserService;
import sib6.finalproject.Jobsite_ClientApp.util.security.AuthSessionUtil;

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
    public String userProfile(Model model) {
        UserDetailResponse userDetailResponse = userService.getUserProfile();
        model.addAttribute("userProfile", userDetailResponse);
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

    @DeleteMapping("/delete")
    public String deleteUserAccount(HttpServletRequest request, HttpServletResponse response) {
        userService.deleteAccount();

        Authentication auth = AuthSessionUtil.getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }

        return "redirect:/login?logout";
    }

}
