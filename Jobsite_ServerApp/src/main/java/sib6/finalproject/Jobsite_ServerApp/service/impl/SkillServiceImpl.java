package sib6.finalproject.Jobsite_ServerApp.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import sib6.finalproject.Jobsite_ServerApp.entity.Skill;
import sib6.finalproject.Jobsite_ServerApp.entity.User;
import sib6.finalproject.Jobsite_ServerApp.entity.UserDetail;
import sib6.finalproject.Jobsite_ServerApp.model.response.SkillResponse;
import sib6.finalproject.Jobsite_ServerApp.repository.SkillRepository;
import sib6.finalproject.Jobsite_ServerApp.repository.UserDetailRepository;
import sib6.finalproject.Jobsite_ServerApp.repository.UserRepository;
import sib6.finalproject.Jobsite_ServerApp.service.SkillService;

@Service
public class SkillServiceImpl implements SkillService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDetailRepository userDetailRepository;

    @Autowired
    private SkillRepository skillRepository;


    @Override
    public String deleteSkill(String id, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found with Username: " + username));
        UserDetail userDetail = userDetailRepository.findById(user.getUserDetail().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User Detail Not Found with Username: " + username));

        Skill skill = skillRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Skill Not Found with id: " + id));

        userDetail.getSkills().remove(skill);

        userDetailRepository.save(userDetail);

        return "Deleted Skill Successfully";
    }


    public SkillResponse toSkillResponse(Skill skill) {
        SkillResponse skillResponse = new SkillResponse();
        modelMapper.map(skill, skillResponse);
        return skillResponse;
    }

}
