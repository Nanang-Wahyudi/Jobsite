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
import sib6.finalproject.Jobsite_ServerApp.model.request.UpdateCompanyDetailRequest;
import sib6.finalproject.Jobsite_ServerApp.model.response.CompanyDetailResponse;
import sib6.finalproject.Jobsite_ServerApp.model.response.CompanyResponse;
import sib6.finalproject.Jobsite_ServerApp.model.response.Response;
import sib6.finalproject.Jobsite_ServerApp.service.CompanyService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@RequestMapping("/api/company")
@RestController
public class CompanyController {

    @Autowired
    private CompanyService companyService;


    @Operation(summary = "Display all companies")
    @GetMapping()
    public ResponseEntity<?> getAllCompany() {
        List<CompanyResponse> companyResponses = companyService.getAllCompany();
        return ResponseEntity.ok(companyResponses);
    }

    @Operation(summary = "Display all companies for Admin")
    @PreAuthorize("hasAuthority('READ_ADMIN')")
    @GetMapping("/admin")
    public ResponseEntity<?> getAllCompanyForAdmin() {
        return ResponseEntity.ok(companyService.getAllCompanyForAdmin());
    }

    @Operation(summary = "Display companies based on company ID")
    @GetMapping("/{id}")
    public ResponseEntity<?> getCompanyDetailById(@PathVariable("id") String id) {
        CompanyDetailResponse companyDetailResponses = companyService.getCompanyDetailById(id);
        return ResponseEntity.ok(companyDetailResponses);
    }

    @Operation(summary = "Display company data based on the logged in company username")
    @PreAuthorize("hasAuthority('READ_COMPANY')")
    @GetMapping("/profile")
    public ResponseEntity<?> getCompanyDetailProfile() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        CompanyDetailResponse companyDetailResponses = companyService.getCompanyDetailProfile(username);
        return ResponseEntity.ok(companyDetailResponses);
    }

    @Operation(summary = "Update company profile based on logged-in company username")
    @PreAuthorize("hasAuthority('UPDATE_COMPANY')")
    @PutMapping(value = "/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateCompanyDetail(HttpServletRequest servletRequest, @Valid @ModelAttribute UpdateCompanyDetailRequest companyDetailRequest, BindingResult result) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Response response = Response.builder()
                .url(servletRequest.getRequestURL().toString())
                .status(HttpStatus.OK.toString())
                .message(companyService.updateCompanyDetail(username, companyDetailRequest))
                .build();
        response.setTimestamp(new Date());

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Delete company account based on login username")
    @PreAuthorize("hasAuthority('DELETE_COMPANY')")
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteCompanyByUsername(HttpServletRequest servletRequest) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Response response = Response.builder()
                .url(servletRequest.getRequestURL().toString())
                .status(HttpStatus.OK.toString())
                .message(companyService.deleteCompanyByUsername(username))
                .build();
        response.setTimestamp(new Date());

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Delete company account based on ID for Admin")
    @PreAuthorize("hasAuthority('DELETE_ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCompanyByIdForAdmin(HttpServletRequest servletRequest, @PathVariable("id") String id) {
        Response response = Response.builder()
                .url(servletRequest.getRequestURL().toString())
                .status(HttpStatus.OK.toString())
                .message(companyService.deleteCompanyByIdForAdmin(id))
                .build();
        response.setTimestamp(new Date());

        return ResponseEntity.ok(response);
    }

}
