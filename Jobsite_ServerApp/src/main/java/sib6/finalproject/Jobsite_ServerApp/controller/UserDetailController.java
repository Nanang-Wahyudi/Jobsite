package sib6.finalproject.Jobsite_ServerApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import sib6.finalproject.Jobsite_ServerApp.model.request.UpdateUserDetailRequest;
import sib6.finalproject.Jobsite_ServerApp.model.response.Response;
import sib6.finalproject.Jobsite_ServerApp.service.UserDetailService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Date;

@RequestMapping("/api/user")
@RestController
public class UserDetailController {

    @Autowired
    private UserDetailService userDetailService;

    @PutMapping("/update/{username}")
    public ResponseEntity<?> updateUserDetail(HttpServletRequest servletRequest, @PathVariable("username") String username, @Valid @ModelAttribute UpdateUserDetailRequest userDetailRequest, BindingResult result) throws IOException {
        Response response = Response.builder()
                .url(servletRequest.getRequestURL().toString())
                .status(HttpStatus.OK.toString())
                .message(userDetailService.updateUserDetail(username, userDetailRequest))
                .build();
        response.setTimestamp(new Date());

        return ResponseEntity.ok(response);
    }

}
