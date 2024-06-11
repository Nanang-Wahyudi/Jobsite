package sib6.finalproject.Jobsite_ServerApp.service.impl;

import org.modelmapper.ModelMapper;
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
import sib6.finalproject.Jobsite_ServerApp.model.enums.RoleEnum;
import sib6.finalproject.Jobsite_ServerApp.model.request.UpdateUserDetailRequest;
import sib6.finalproject.Jobsite_ServerApp.model.response.EducationResponse;
import sib6.finalproject.Jobsite_ServerApp.model.response.SkillResponse;
import sib6.finalproject.Jobsite_ServerApp.model.response.UserDetailResponse;
import sib6.finalproject.Jobsite_ServerApp.model.response.UserResponse;
import sib6.finalproject.Jobsite_ServerApp.repository.EducationRepository;
import sib6.finalproject.Jobsite_ServerApp.repository.SkillRepository;
import sib6.finalproject.Jobsite_ServerApp.repository.UserDetailRepository;
import sib6.finalproject.Jobsite_ServerApp.repository.UserRepository;
import sib6.finalproject.Jobsite_ServerApp.service.UserDetailService;
import sib6.finalproject.Jobsite_ServerApp.service.media.CloudinaryService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<UserResponse> getAllUser() {
        List<UserDetail> userDetails = userDetailRepository.findAllByRoleName(RoleEnum.USER);
        return userDetails.stream()
                .map(userDetail -> {
                    List<SkillResponse> skillResponses = userDetail.getSkills().stream()
                            .map(this::toSkillResponse)
                            .collect(Collectors.toList());

                    return this.toUserResponse(userDetail, skillResponses);
                })
                .collect(Collectors.toList());
    }

    @Override
    public UserDetailResponse getUserDetailById(String id) {
        UserDetail userDetail = userDetailRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User Detail Not Found with ID: " + id));

        List<EducationResponse> educationResponses = userDetail.getEducations().stream()
                .map(this::toEducationResponse)
                .collect(Collectors.toList());

        List<SkillResponse> skillResponses = userDetail.getSkills().stream()
                .map(this::toSkillResponse)
                .collect(Collectors.toList());

        return this.toUserDetailResponse(userDetail, educationResponses, skillResponses);
    }

    @Override
    public UserDetailResponse getUserDetailProfile(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found with Username: " + username));
        UserDetail userDetail = user.getUserDetail();

        List<EducationResponse> educationResponses = userDetail.getEducations().stream()
                .map(this::toEducationResponse)
                .collect(Collectors.toList());

        List<SkillResponse> skillResponses = userDetail.getSkills().stream()
                .map(this::toSkillResponse)
                .collect(Collectors.toList());

        return this.toUserDetailResponse(userDetail, educationResponses, skillResponses);
    }

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

    public UserDetailResponse toUserDetailResponse(UserDetail userDetail, List<EducationResponse> educationResponses, List<SkillResponse> skillResponses) {
        UserDetailResponse userDetailResponse = new UserDetailResponse();
        userDetailResponse.setUrlPicture(userDetail.getPicture());
        userDetailResponse.setEducationResponses(educationResponses);
        userDetailResponse.setSkillResponses(skillResponses);
        modelMapper.map(userDetail, userDetailResponse);
        return userDetailResponse;
    }

    public UserResponse toUserResponse(UserDetail userDetail, List<SkillResponse> skillResponses) {
        UserResponse userResponse = new UserResponse();
        userResponse.setUrlPicture(userDetail.getPicture());
        userResponse.setSkillResponses(skillResponses);
        modelMapper.map(userDetail, userResponse);
        return userResponse;
    }

    public EducationResponse toEducationResponse(Education education) {
        EducationResponse educationResponse = new EducationResponse();
        modelMapper.map(education, educationResponse);
        return educationResponse;
    }

    public SkillResponse toSkillResponse(Skill skill) {
        SkillResponse skillResponse = new SkillResponse();
        modelMapper.map(skill, skillResponse);
        return skillResponse;
    }

}
