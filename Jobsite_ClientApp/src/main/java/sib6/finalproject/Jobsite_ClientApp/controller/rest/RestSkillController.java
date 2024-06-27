package sib6.finalproject.Jobsite_ClientApp.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sib6.finalproject.Jobsite_ClientApp.model.response.SkillResponse;
import sib6.finalproject.Jobsite_ClientApp.service.SkillService;

@RequestMapping("/api/client/skill")
@RestController
public class RestSkillController {
    
    @Autowired
    private SkillService skillService;

    @GetMapping("/delete/{id}")
    public SkillResponse deleteSkill(@PathVariable("id") String id){
        return skillService.deleteSkill(id);
    }
}
