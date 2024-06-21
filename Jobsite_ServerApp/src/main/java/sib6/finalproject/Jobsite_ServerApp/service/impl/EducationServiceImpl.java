package sib6.finalproject.Jobsite_ServerApp.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import sib6.finalproject.Jobsite_ServerApp.entity.Education;
import sib6.finalproject.Jobsite_ServerApp.entity.UserDetail;
import sib6.finalproject.Jobsite_ServerApp.model.request.UpdateEducationRequest;
import sib6.finalproject.Jobsite_ServerApp.model.response.EducationResponse;
import sib6.finalproject.Jobsite_ServerApp.repository.EducationRepository;
import sib6.finalproject.Jobsite_ServerApp.repository.UserDetailRepository;
import sib6.finalproject.Jobsite_ServerApp.service.EducationService;

@Service
public class EducationServiceImpl implements EducationService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private EducationRepository educationRepository;

    @Autowired
    private UserDetailRepository userDetailRepository;


    @Transactional
    @Override
    public String updateEducation(String id, UpdateEducationRequest updateEducationRequest) {
        Education education = educationRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Education Not Found with ID: " + id));

        education.setName(updateEducationRequest.getInstansiName() != null && !updateEducationRequest.getInstansiName().isEmpty()
                            ? updateEducationRequest.getInstansiName() : education.getName());
        education.setMajor(updateEducationRequest.getMajor() != null && !updateEducationRequest.getMajor().isEmpty()
                            ? updateEducationRequest.getMajor() : education.getMajor());
        education.setAvgScore(updateEducationRequest.getAvgScore() != null && !updateEducationRequest.getAvgScore().isEmpty()
                                ? updateEducationRequest.getAvgScore() : education.getAvgScore());

        educationRepository.save(education);
        return "Updated Education Successfully with ID: " + id;
    }

    @Transactional
    @Override
    public String deleteEducation(String id) {
        Education education = educationRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Education Not Found with id: " + id));

        for (UserDetail userDetail : education.getUserDetails()) {
            userDetail.getEducations().remove(education);
            userDetailRepository.save(userDetail);
        }

        educationRepository.delete(education);
        return "Deleted Education Successfully with ID: " + id;
    }


    public EducationResponse toEducationResponse(Education education) {
        EducationResponse educationResponse = new EducationResponse();
        educationResponse.setInstansiName(education.getName());
        modelMapper.map(education, educationResponse);
        return educationResponse;
    }

}
