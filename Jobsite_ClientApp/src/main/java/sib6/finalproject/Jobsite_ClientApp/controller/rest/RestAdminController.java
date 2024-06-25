package sib6.finalproject.Jobsite_ClientApp.controller.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import sib6.finalproject.Jobsite_ClientApp.model.response.CompanyAdminResponse;
import sib6.finalproject.Jobsite_ClientApp.model.response.UserAdminResponse;
import sib6.finalproject.Jobsite_ClientApp.service.AdminService;

@RestController
public class RestAdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/api/client/manage-talent")
    public List<UserAdminResponse> getAllUserAdmin() {
        return adminService.getAllUserAdmin();
    }

    @GetMapping("/api/client/manage-company")
    public List<CompanyAdminResponse> getAllCompanyAdmin() {
        return adminService.getAllCompanyAdmin();
    }

}
