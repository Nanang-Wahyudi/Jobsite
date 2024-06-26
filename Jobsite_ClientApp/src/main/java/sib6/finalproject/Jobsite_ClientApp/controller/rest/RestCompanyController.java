package sib6.finalproject.Jobsite_ClientApp.controller.rest;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import sib6.finalproject.Jobsite_ClientApp.model.request.UpdateCompanyDetailRequest;
import sib6.finalproject.Jobsite_ClientApp.model.response.CompanyDetailResponse;
import sib6.finalproject.Jobsite_ClientApp.service.CompanyService;

@RequestMapping("/api/client/company")
@RestController
public class RestCompanyController {
 
    @Autowired
    private CompanyService companyService;

    @GetMapping("/profile")
    public CompanyDetailResponse getCompanyProfile(){
        return companyService.getCompanyProfile();
    }

    @PutMapping("/profile/update")
    public CompanyDetailResponse updateCompanyDetail(
        @RequestParam("name") String name,
        @RequestParam("email") String email,
        @RequestParam("address") String address,
        @RequestParam("description") String description,
        @RequestParam(value = "picture", required = false) MultipartFile picture,
        @RequestParam(value = "banner", required = false) MultipartFile banner) throws IOException{

        UpdateCompanyDetailRequest companyDetailRequest = new UpdateCompanyDetailRequest();
        
        companyDetailRequest.setName(name);
        companyDetailRequest.setEmail(email);
        companyDetailRequest.setAddress(address);
        companyDetailRequest.setDescription(description);
        companyDetailRequest.setPicture(picture);
        companyDetailRequest.setBanner(banner);
        
        return companyService.updateCompanyDetail(companyDetailRequest);
    }
}
