package sib6.finalproject.Jobsite_ServerApp.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import sib6.finalproject.Jobsite_ServerApp.model.enums.RoleEnum;
import sib6.finalproject.Jobsite_ServerApp.model.request.ForgotPasswordRequest;
import sib6.finalproject.Jobsite_ServerApp.model.request.LoginRequest;
import sib6.finalproject.Jobsite_ServerApp.model.request.RegisterRequest;
import sib6.finalproject.Jobsite_ServerApp.model.request.UpdatePasswordRequest;
import sib6.finalproject.Jobsite_ServerApp.model.response.Response;
import sib6.finalproject.Jobsite_ServerApp.service.AuthService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;

@RequestMapping("/api/auth")
@RestController
public class AuthController {

    @Autowired
    private AuthService authService;


    @Operation(summary = "Register for User")
    @PostMapping("/register/user")
    public ResponseEntity<?> registerUser(HttpServletRequest servletRequest, @Valid @RequestBody RegisterRequest request) {
        Response response = Response.builder()
                    .url(servletRequest.getRequestURL().toString())
                    .status(HttpStatus.OK.toString())
                    .message(authService.register(request, RoleEnum.USER))
                .build();
        response.setTimestamp(new Date());
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Register for Admin")
    @PostMapping("/register/admin")
    public ResponseEntity<?> registerAdmin(HttpServletRequest servletRequest, @Valid @RequestBody RegisterRequest request) {
        Response response = Response.builder()
                .url(servletRequest.getRequestURL().toString())
                .status(HttpStatus.OK.toString())
                .message(authService.register(request, RoleEnum.ADMIN))
                .build();
        response.setTimestamp(new Date());
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Register for Company")
    @PostMapping("/register/company")
    public ResponseEntity<?> registerCompany(HttpServletRequest servletRequest, @Valid @RequestBody RegisterRequest request) {
        Response response = Response.builder()
                .url(servletRequest.getRequestURL().toString())
                .status(HttpStatus.OK.toString())
                .message(authService.register(request, RoleEnum.COMPANY))
                .build();
        response.setTimestamp(new Date());
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Login for all role")
    @PostMapping("/login")
    public ResponseEntity<?> login (@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @Operation(summary = "Updated password based on username login")
    @PreAuthorize("hasAnyAuthority('UPDATE_ADMIN', 'UPDATE_USER', 'UPDATE_COMPANY')")
    @PutMapping("/update-password")
    public ResponseEntity<?> updatePassword(HttpServletRequest servletRequest, @Valid @RequestBody UpdatePasswordRequest updatePasswordRequest) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        try {
            String message = authService.updatePassword(username, updatePasswordRequest);
            Response response = Response.builder()
                    .url(servletRequest.getRequestURL().toString())
                    .status(HttpStatus.OK.toString())
                    .message(message)
                    .build();
            response.setTimestamp(new Date());
            return ResponseEntity.ok(response);
        } catch (ResponseStatusException e) {
            Response response = Response.builder()
                .url(servletRequest.getRequestURL().toString())
                .status(e.getStatus().toString())
                .message(e.getReason())
                .build();
            response.setTimestamp(new Date());
            return ResponseEntity.status(e.getStatus()).body(response);
        }
    }

    @Operation(summary = "Forgot password")
    @PutMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(HttpServletRequest servletRequest, @Valid @RequestBody ForgotPasswordRequest forgotPasswordRequest) {
        Response response = Response.builder()
                .url(servletRequest.getRequestURL().toString())
                .status(HttpStatus.OK.toString())
                .message(authService.forgotPassword(forgotPasswordRequest))
                .build();
        response.setTimestamp(new Date());
        return ResponseEntity.ok(response);
    }

}
