package sib6.finalproject.Jobsite_ClientApp.controller.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sib6.finalproject.Jobsite_ClientApp.model.response.CompanyAdminResponse;
import sib6.finalproject.Jobsite_ClientApp.model.response.UserAdminResponse;
import sib6.finalproject.Jobsite_ClientApp.service.AdminService;

@RequestMapping("/api/client/admin")
@RestController
public class RestAdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/manage-talent")
    public List<UserAdminResponse> getAllUserAdmin() {
        return adminService.getAllUserAdmin();
    }

    @GetMapping("/manage-company")
    public List<CompanyAdminResponse> getAllCompanyAdmin() {
        return adminService.getAllCompanyAdmin();
    }

    @DeleteMapping("/delete-user/{id}")
    public UserAdminResponse deleteUserByAdmin(@PathVariable("id") String id){
        return adminService.deleteUserByAdmin(id);
    }

    @DeleteMapping("/delete-company/{id}")
    public CompanyAdminResponse deleteCompanyByAdmin(@PathVariable("id") String id){
        return adminService.deleteCompanyByAdmin(id);
    }
}
