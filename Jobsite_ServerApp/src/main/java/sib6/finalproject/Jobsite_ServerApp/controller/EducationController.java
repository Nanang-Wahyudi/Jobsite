package sib6.finalproject.Jobsite_ServerApp.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sib6.finalproject.Jobsite_ServerApp.model.request.UpdateEducationRequest;
import sib6.finalproject.Jobsite_ServerApp.model.response.Response;
import sib6.finalproject.Jobsite_ServerApp.service.EducationService;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RequestMapping("/api/education")
@RestController
public class EducationController {

    @Autowired
    private EducationService educationService;


    @Operation(summary = "Update educations based on education ID")
    @PreAuthorize("hasAuthority('UPDATE_USER')")
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateEducation(HttpServletRequest servletRequest, @PathVariable("id") String id, @RequestBody UpdateEducationRequest updateEducationRequest) {
        Response response = Response.builder()
                .url(servletRequest.getRequestURL().toString())
                .status(HttpStatus.OK.toString())
                .message(educationService.updateEducation(id, updateEducationRequest))
                .build();
        response.setTimestamp(new Date());

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Delete educations based on education ID")
    @PreAuthorize("hasAuthority('DELETE_USER')")
    @PutMapping("/delete/{id}")
    public ResponseEntity<?> deleteEducation(HttpServletRequest servletRequest, @PathVariable("id") String id) {
        Response response = Response.builder()
                .url(servletRequest.getRequestURL().toString())
                .status(HttpStatus.OK.toString())
                .message(educationService.deleteEducation(id))
                .build();
        response.setTimestamp(new Date());

        return ResponseEntity.ok(response);
    }

}
