package sib6.finalproject.Jobsite_ServerApp.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
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


    @Operation(summary = "Display all Users")
    @GetMapping()
    public ResponseEntity<?> getAllUser() {
        List<UserResponse> userResponse = userDetailService.getAllUser();
        return ResponseEntity.ok(userResponse);
    }

    @Operation(summary = "Display all Users for Admin")
    @PreAuthorize("hasAuthority('READ_ADMIN')")
    @GetMapping("/admin")
    public ResponseEntity<?> getAllUserForAdmin() {
        return ResponseEntity.ok(userDetailService.getAllUserForAdmin());
    }

    @Operation(summary = "Display users based on user ID")
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserDetailById(@PathVariable("id") String id) {
        UserDetailResponse userDetailResponse = userDetailService.getUserDetailById(id);
        return ResponseEntity.ok(userDetailResponse);
    }

    @Operation(summary = "Display user data based on the logged in username")
    @PreAuthorize("hasAuthority('READ_USER')")
    @GetMapping("/profile")
    public ResponseEntity<?> getUserDetailProfile() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserDetailResponse userDetailResponse = userDetailService.getUserDetailProfile(username);
        return ResponseEntity.ok(userDetailResponse);
    }

    @Operation(summary = "Update user profile based on logged-in username")
    @PreAuthorize("hasAuthority('UPDATE_USER')")
    @PutMapping(value = "/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateUserDetail(HttpServletRequest servletRequest, @Valid @ModelAttribute UpdateUserDetailRequest userDetailRequest, BindingResult result) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Response response = Response.builder()
                .url(servletRequest.getRequestURL().toString())
                .status(HttpStatus.OK.toString())
                .message(userDetailService.updateUserDetail(username, userDetailRequest))
                .build();
        response.setTimestamp(new Date());

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Delete user account based on login username for Admin & User")
    @PreAuthorize("hasAnyAuthority('DELETE_USER', 'DELETE_ADMIN')")
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUserByUsername(HttpServletRequest servletRequest) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Response response = Response.builder()
                .url(servletRequest.getRequestURL().toString())
                .status(HttpStatus.OK.toString())
                .message(userDetailService.deleteUserByUsername(username))
                .build();
        response.setTimestamp(new Date());

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Delete user account based on ID for Admin")
    @PreAuthorize("hasAuthority('DELETE_ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUserByIdForAdmin(HttpServletRequest servletRequest, @PathVariable("id") String id) {
        Response response = Response.builder()
                .url(servletRequest.getRequestURL().toString())
                .status(HttpStatus.OK.toString())
                .message(userDetailService.deleteUserByIdForAdmin(id))
                .build();
        response.setTimestamp(new Date());

        return ResponseEntity.ok(response);
    }

}
