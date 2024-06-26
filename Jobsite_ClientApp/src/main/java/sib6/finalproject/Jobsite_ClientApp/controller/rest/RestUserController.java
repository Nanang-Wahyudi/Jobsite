package sib6.finalproject.Jobsite_ClientApp.controller.rest;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import sib6.finalproject.Jobsite_ClientApp.model.request.UpdateUserDetailRequest;
import sib6.finalproject.Jobsite_ClientApp.model.response.UserDetailResponse;
import sib6.finalproject.Jobsite_ClientApp.service.UserService;

@RequestMapping("/api/client/talent")
@RestController
public class RestUserController {
    
    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    public UserDetailResponse getUserProfile(){
        return userService.getUserProfile();
    }

    @PutMapping("/profile/update")
    public UserDetailResponse updateUserDetail(
        @RequestParam("name") String name,
        @RequestParam("email") String email,
        @RequestParam("address") String address,
        @RequestParam("description") String description,
        @RequestParam(value = "picture", required = false) MultipartFile picture,
        @RequestParam("instansiName") String instansiName,
        @RequestParam("major") String major,
        @RequestParam("avgScore") String avgScore,
        @RequestParam("skillName") String skillName) throws IOException {

            UpdateUserDetailRequest userDetailRequest = new UpdateUserDetailRequest();
            
            userDetailRequest.setName(name);
            userDetailRequest.setEmail(email);
            userDetailRequest.setAddress(address);
            userDetailRequest.setDescription(description);
            userDetailRequest.setPicture(picture);
            userDetailRequest.setInstansiName(instansiName);
            userDetailRequest.setMajor(major);
            userDetailRequest.setAvgScore(avgScore);
            userDetailRequest.setSkillName(skillName);

        return userService.updateUserDetail(userDetailRequest);
    }
}
