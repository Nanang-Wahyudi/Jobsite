package sib6.finalproject.Jobsite_ServerApp.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sib6.finalproject.Jobsite_ServerApp.model.response.Response;
import sib6.finalproject.Jobsite_ServerApp.service.SkillService;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RequestMapping("/api/skill")
@RestController
public class SkillController {

    @Autowired
    private SkillService skillService;

    @Operation(summary = "Delete skills based on skill ID")
    @PreAuthorize("hasAuthority('DELETE_USER')")
    @GetMapping("/delete/{id}")
    public ResponseEntity<?> deleteSkill(HttpServletRequest servletRequest, @PathVariable("id") String id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Response response = Response.builder()
                .url(servletRequest.getRequestURL().toString())
                .status(HttpStatus.OK.toString())
                .message(skillService.deleteSkill(id, username))
                .build();
        response.setTimestamp(new Date());

        return ResponseEntity.ok(response);
    }

}
