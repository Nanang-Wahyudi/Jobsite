package sib6.finalproject.Jobsite_ClientApp.service;

import sib6.finalproject.Jobsite_ClientApp.model.request.UpdateEducationRequest;
import sib6.finalproject.Jobsite_ClientApp.model.response.EducationResponse;

public interface EducationService {
   
    EducationResponse updateEducation(String id, UpdateEducationRequest educationRequest);
    
    EducationResponse deleteEducation(String id);

}
