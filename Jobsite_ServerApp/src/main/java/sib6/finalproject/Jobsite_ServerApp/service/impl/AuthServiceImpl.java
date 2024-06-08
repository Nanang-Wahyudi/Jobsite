package sib6.finalproject.Jobsite_ServerApp.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import sib6.finalproject.Jobsite_ServerApp.entity.Company;
import sib6.finalproject.Jobsite_ServerApp.entity.Role;
import sib6.finalproject.Jobsite_ServerApp.entity.User;
import sib6.finalproject.Jobsite_ServerApp.entity.UserDetail;
import sib6.finalproject.Jobsite_ServerApp.model.enums.RoleEnum;
import sib6.finalproject.Jobsite_ServerApp.model.request.RegisterRequest;
import sib6.finalproject.Jobsite_ServerApp.repository.CompanyRepository;
import sib6.finalproject.Jobsite_ServerApp.repository.RoleRepository;
import sib6.finalproject.Jobsite_ServerApp.repository.UserDetailRepository;
import sib6.finalproject.Jobsite_ServerApp.repository.UserRepository;
import sib6.finalproject.Jobsite_ServerApp.service.AuthService;

import java.util.Collections;
import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDetailRepository userDetailRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public String register(RegisterRequest request, RoleEnum roleName) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Already Exists Username: " + request.getUsername());
        }

        UserDetail userDetail = modelMapper.map(request, UserDetail.class);
        User user = modelMapper.map(request, User.class);
        Company company = modelMapper.map(request, Company.class);

        if (companyRepository.existsByName(company.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Already Exists Name: " + request.getName());
        } else if (companyRepository.existsByEmail(company.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Already Exists Email: " + request.getEmail());
        } else if (userDetailRepository.existsByEmail(userDetail.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Already Exists Email: " + userDetail.getEmail());
        }

        Optional<Role> roleOptional = roleRepository.findByName(roleName);
        if (!roleOptional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found: " + roleName);
        }
        Role role = roleOptional.get();

        if (role.getName().equals(RoleEnum.COMPANY)) {

            user.setRoles(Collections.singletonList(role));
            user.setCompany(company);
            company.setUser(user);

            companyRepository.save(company);

        } else {
            user.setRoles(Collections.singletonList(role));
            user.setUserDetail(userDetail);
            userDetail.setUser(user);

            userDetailRepository.save(userDetail);
        }

        return "Registered Successfully with Username: " + request.getUsername();
    }

}
