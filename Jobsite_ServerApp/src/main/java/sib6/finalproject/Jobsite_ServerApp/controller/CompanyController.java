package sib6.finalproject.Jobsite_ServerApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @GetMapping()
    public ResponseEntity<?> getAllCompany() {
        List<CompanyResponse> companyResponses = companyService.getAllCompany();
        return ResponseEntity.ok(companyResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCompanyDetailById(@PathVariable("id") String id) {
        CompanyDetailResponse companyDetailResponses = companyService.getCompanyDetailById(id);
        return ResponseEntity.ok(companyDetailResponses);
    }

    @GetMapping("/profile/{username}")
    public ResponseEntity<?> getCompanyDetailProfile(@PathVariable("username") String username) {
        CompanyDetailResponse companyDetailResponses = companyService.getCompanyDetailProfile(username);
        return ResponseEntity.ok(companyDetailResponses);
    }

    @PutMapping("/update/{username}")
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
