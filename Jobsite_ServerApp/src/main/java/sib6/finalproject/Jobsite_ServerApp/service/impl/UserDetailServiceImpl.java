package sib6.finalproject.Jobsite_ServerApp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import sib6.finalproject.Jobsite_ServerApp.entity.Education;
import sib6.finalproject.Jobsite_ServerApp.entity.Skill;
import sib6.finalproject.Jobsite_ServerApp.entity.User;
import sib6.finalproject.Jobsite_ServerApp.entity.UserDetail;
import sib6.finalproject.Jobsite_ServerApp.model.request.UpdateUserDetailRequest;
import sib6.finalproject.Jobsite_ServerApp.repository.EducationRepository;
import sib6.finalproject.Jobsite_ServerApp.repository.SkillRepository;
import sib6.finalproject.Jobsite_ServerApp.repository.UserDetailRepository;
import sib6.finalproject.Jobsite_ServerApp.repository.UserRepository;
import sib6.finalproject.Jobsite_ServerApp.service.UserDetailService;
import sib6.finalproject.Jobsite_ServerApp.service.media.CloudinaryService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserDetailServiceImpl implements UserDetailService {

    @Autowired
    private UserDetailRepository userDetailRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EducationRepository educationRepository;

    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private CloudinaryService cloudinaryService;

    @Override
    @Transactional
    public String updateUserDetail(String username, UpdateUserDetailRequest userDetailRequest) {
        String imageUrl;

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found with Username: " + username));
        UserDetail userDetail = user.getUserDetail();

        if (userDetail == null) {
            userDetail = new UserDetail();
            userDetail.setUser(user);
        }

        Optional<MultipartFile> requestPicture = Optional.ofNullable(userDetailRequest.getPicture());
        if(requestPicture.isPresent()) {
            if (!isImageFile(requestPicture.get())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid file type for picture. Only image files are allowed.");
            }
            try {
                if(userDetail.getPicture() != null) cloudinaryService.deleteFile(userDetail.getPicture());
                imageUrl = cloudinaryService.uploadFile(userDetailRequest.getPicture());
            } catch (IOException e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error uploading picture: " + e.getMessage());
            }
        } else{
            imageUrl=null;
        }

        userDetail.setName(userDetailRequest.getName() != null && !userDetailRequest.getName().isEmpty()
                ? userDetailRequest.getName() : userDetail.getName());
        userDetail.setEmail(userDetailRequest.getEmail() != null && !userDetailRequest.getEmail().isEmpty()
                ? userDetailRequest.getEmail() : userDetail.getEmail());
        userDetail.setAddress(userDetailRequest.getAddress() != null && !userDetailRequest.getAddress().isEmpty()
                ? userDetailRequest.getAddress() : userDetail.getAddress());
        userDetail.setDescription(userDetailRequest.getDescription() != null && !userDetailRequest.getDescription().isEmpty()
                ? userDetailRequest.getDescription() : userDetail.getDescription());
        userDetail.setPicture(imageUrl != null ? imageUrl : userDetail.getPicture());

        if (userDetail.getEducations() == null) {
            userDetail.setEducations(new ArrayList<>());
        }
        if (userDetail.getSkills() == null) {
            userDetail.setSkills(new ArrayList<>());
        }

        if (userDetailRequest.getInstansiName() != null && !userDetailRequest.getInstansiName().isEmpty()) {
            Education existingEducation = educationRepository.findByName(userDetailRequest.getInstansiName());
            if (existingEducation == null) {
                Education newEducation = Education.builder()
                        .name(userDetailRequest.getInstansiName())
                        .major(userDetailRequest.getMajor())
                        .avgScore(userDetailRequest.getAvgScore())
                        .build();
                educationRepository.save(newEducation);
                userDetail.getEducations().add(newEducation);
            } else {
                if (!userDetail.getEducations().contains(existingEducation)) {
                    userDetail.getEducations().add(existingEducation);
                }
            }
        }

        if (userDetailRequest.getSkillName() != null && !userDetailRequest.getSkillName().isEmpty()) {
            Skill existingSkill = skillRepository.findByName(userDetailRequest.getSkillName());
            if (existingSkill == null) {
                Skill newSkill = Skill.builder()
                        .name(userDetailRequest.getSkillName())
                        .build();
                skillRepository.save(newSkill);
                userDetail.getSkills().add(newSkill);
            } else {
                if (!userDetail.getSkills().contains(existingSkill)) {
                    userDetail.getSkills().add(existingSkill);
                }
            }
        }

        userDetailRepository.save(userDetail);

        return "Update User Detail Successfully with Name: " + userDetail.getName();
    }

    private boolean isImageFile(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType != null && (contentType.startsWith("image/jpeg") || contentType.startsWith("image/png") || contentType.startsWith("image/gif"));
    }

}
