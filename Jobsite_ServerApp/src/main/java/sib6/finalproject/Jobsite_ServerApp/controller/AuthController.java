package sib6.finalproject.Jobsite_ServerApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sib6.finalproject.Jobsite_ServerApp.model.enums.RoleEnum;
import sib6.finalproject.Jobsite_ServerApp.model.request.RegisterRequest;
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

    @PostMapping("/user")
    public ResponseEntity<?> registerUser(HttpServletRequest servletRequest, @Valid @RequestBody RegisterRequest request) {
        Response response = Response.builder()
                    .url(servletRequest.getRequestURL().toString())
                    .status(HttpStatus.OK.toString())
                    .message(authService.register(request, RoleEnum.USER))
                .build();
        response.setTimestamp(new Date());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/admin")
    public ResponseEntity<?> registerAdmin(HttpServletRequest servletRequest, @Valid @RequestBody RegisterRequest request) {
        Response response = Response.builder()
                .url(servletRequest.getRequestURL().toString())
                .status(HttpStatus.OK.toString())
                .message(authService.register(request, RoleEnum.ADMIN))
                .build();
        response.setTimestamp(new Date());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/company")
    public ResponseEntity<?> registerCompany(HttpServletRequest servletRequest, @Valid @RequestBody RegisterRequest request) {
        Response response = Response.builder()
                .url(servletRequest.getRequestURL().toString())
                .status(HttpStatus.OK.toString())
                .message(authService.register(request, RoleEnum.COMPANY))
                .build();
        response.setTimestamp(new Date());
        return ResponseEntity.ok(response);
    }

}
