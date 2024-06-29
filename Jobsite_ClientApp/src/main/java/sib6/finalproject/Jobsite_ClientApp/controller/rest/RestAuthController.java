package sib6.finalproject.Jobsite_ClientApp.controller.rest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sib6.finalproject.Jobsite_ClientApp.model.request.UpdatePasswordRequest;
import sib6.finalproject.Jobsite_ClientApp.service.AuthService;
import sib6.finalproject.Jobsite_ClientApp.util.security.AuthSessionUtil;


@RequestMapping("/api/client/auth")
@RestController
public class RestAuthController {

    @Autowired
    private AuthService authService;

    @PutMapping("/update-password")
    public ResponseEntity<String> updatePassword(@RequestBody UpdatePasswordRequest passwordRequest, HttpServletRequest request, HttpServletResponse response) {
        ResponseEntity<String> updateResponse = authService.updatePassword(passwordRequest);
        if (updateResponse.getStatusCode() == HttpStatus.OK) {
            Authentication auth = AuthSessionUtil.getAuthentication();
            if (auth != null) {
                new SecurityContextLogoutHandler().logout(request, response, auth);
            }
        }
        return updateResponse;
    }
}
