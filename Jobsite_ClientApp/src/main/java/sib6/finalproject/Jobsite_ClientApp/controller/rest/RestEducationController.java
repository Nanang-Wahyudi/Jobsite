package sib6.finalproject.Jobsite_ClientApp.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sib6.finalproject.Jobsite_ClientApp.model.request.UpdateEducationRequest;
import sib6.finalproject.Jobsite_ClientApp.model.response.EducationResponse;
import sib6.finalproject.Jobsite_ClientApp.service.EducationService;

@RequestMapping("/api/client/education")
@RestController
public class RestEducationController {
    
    @Autowired
    private EducationService educationService;

    @PutMapping("/update/{id}")
    public EducationResponse updateEducation(@PathVariable("id") String id, @RequestBody UpdateEducationRequest educationRequest){
        return educationService.updateEducation(id, educationRequest);
    }

    @PutMapping("/delete/{id}")
    public EducationResponse deleteEducation(@PathVariable("id") String id){
        return educationService.deleteEducation(id);
    }
}
