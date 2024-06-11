package sib6.finalproject.Jobsite_ServerApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import sib6.finalproject.Jobsite_ServerApp.model.request.UpdateUserDetailRequest;
import sib6.finalproject.Jobsite_ServerApp.model.response.Response;
import sib6.finalproject.Jobsite_ServerApp.model.response.UserDetailResponse;
import sib6.finalproject.Jobsite_ServerApp.model.response.UserResponse;
import sib6.finalproject.Jobsite_ServerApp.service.UserDetailService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@RequestMapping("/api/user")
@RestController
public class UserDetailController {

    @Autowired
    private UserDetailService userDetailService;

    @GetMapping()
    public ResponseEntity<?> getAllUser() {
        List<UserResponse> userResponse = userDetailService.getAllUser();
        return ResponseEntity.ok(userResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserDetailById(@PathVariable("id") String id) {
        UserDetailResponse userDetailResponse = userDetailService.getUserDetailById(id);
        return ResponseEntity.ok(userDetailResponse);
    }

    @GetMapping("/profile/{username}")
    public ResponseEntity<?> getUserDetailProfile(@PathVariable("username") String username) {
        UserDetailResponse userDetailResponse = userDetailService.getUserDetailProfile(username);
        return ResponseEntity.ok(userDetailResponse);
    }

    @PutMapping("/update/{username}")
    public ResponseEntity<?> updateUserDetail(HttpServletRequest servletRequest, @PathVariable("username") String username, @Valid @ModelAttribute UpdateUserDetailRequest userDetailRequest, BindingResult result) {
        Response response = Response.builder()
                .url(servletRequest.getRequestURL().toString())
                .status(HttpStatus.OK.toString())
                .message(userDetailService.updateUserDetail(username, userDetailRequest))
                .build();
        response.setTimestamp(new Date());

        return ResponseEntity.ok(response);
    }

}
