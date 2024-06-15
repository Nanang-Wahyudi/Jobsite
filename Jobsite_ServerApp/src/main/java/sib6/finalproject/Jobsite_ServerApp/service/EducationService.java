package sib6.finalproject.Jobsite_ServerApp.service;

import sib6.finalproject.Jobsite_ServerApp.model.request.UpdateEducationRequest;

public interface EducationService {

    String updateEducation(String id, UpdateEducationRequest updateEducationRequest);

    String deleteEducation(String id);

}
