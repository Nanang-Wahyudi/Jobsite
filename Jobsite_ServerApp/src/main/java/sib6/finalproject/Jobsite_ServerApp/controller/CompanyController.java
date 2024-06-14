package sib6.finalproject.Jobsite_ServerApp.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

    @Operation(summary = "Display companies based on company ID")
    @GetMapping("/{id}")
    public ResponseEntity<?> getCompanyDetailById(@PathVariable("id") String id) {
        CompanyDetailResponse companyDetailResponses = companyService.getCompanyDetailById(id);
        return ResponseEntity.ok(companyDetailResponses);
    }

    @Operation(summary = "Display company data based on the logged in company username")
    @GetMapping("/profile/{username}")
    public ResponseEntity<?> getCompanyDetailProfile(@PathVariable("username") String username) {
        CompanyDetailResponse companyDetailResponses = companyService.getCompanyDetailProfile(username);
        return ResponseEntity.ok(companyDetailResponses);
    }

    @Operation(summary = "Update company profile based on logged-in company username")
    @PutMapping(value = "/update/{username}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateCompanyDetail(HttpServletRequest servletRequest, @PathVariable("username") String username, @Valid @ModelAttribute UpdateCompanyDetailRequest companyDetailRequest, BindingResult result) {
        Response response = Response.builder()
                .url(servletRequest.getRequestURL().toString())
                .status(HttpStatus.OK.toString())
                .message(companyService.updateCompanyDetail(username, companyDetailRequest))
                .build();
        response.setTimestamp(new Date());

        return ResponseEntity.ok(response);
    }

}
