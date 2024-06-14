package sib6.finalproject.Jobsite_ServerApp.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import sib6.finalproject.Jobsite_ServerApp.entity.Company;
import sib6.finalproject.Jobsite_ServerApp.entity.Role;
import sib6.finalproject.Jobsite_ServerApp.entity.User;
import sib6.finalproject.Jobsite_ServerApp.entity.UserDetail;
import sib6.finalproject.Jobsite_ServerApp.model.enums.RoleEnum;
import sib6.finalproject.Jobsite_ServerApp.model.request.LoginRequest;
import sib6.finalproject.Jobsite_ServerApp.model.request.RegisterRequest;
import sib6.finalproject.Jobsite_ServerApp.model.response.LoginResponse;
import sib6.finalproject.Jobsite_ServerApp.repository.CompanyRepository;
import sib6.finalproject.Jobsite_ServerApp.repository.RoleRepository;
import sib6.finalproject.Jobsite_ServerApp.repository.UserDetailRepository;
import sib6.finalproject.Jobsite_ServerApp.repository.UserRepository;
import sib6.finalproject.Jobsite_ServerApp.service.AuthService;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;


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

            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setRoles(Collections.singletonList(role));
            user.setCompany(company);
            company.setUser(user);

            companyRepository.save(company);

        } else {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setRoles(Collections.singletonList(role));
            user.setUserDetail(userDetail);
            userDetail.setUser(user);

            userDetailRepository.save(userDetail);
        }

        return "Registered Successfully with Username: " + request.getUsername();
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(),
                loginRequest.getPassword()
        );

        Authentication auth = authenticationManager.authenticate(authReq);
        SecurityContext sc = SecurityContextHolder.getContext();
        sc.setAuthentication(auth);

        Collection<? extends GrantedAuthority> authorities = sc.getAuthentication().getAuthorities();

        boolean isCompany = authorities.stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_COMPANY"));

        UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());
        User user = userRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found with Username: " + loginRequest.getUsername()));

        List<String> roles = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        if (isCompany) {
            if (user.getCompany() == null) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Company details not found for user: " + loginRequest.getUsername());
            }
            return LoginResponse.builder()
                    .username(userDetails.getUsername())
                    .email(user.getCompany().getEmail())
                    .authorities(roles)
                    .build();
        } else {
            if (user.getUserDetail() == null) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "User details not found for user: " + loginRequest.getUsername());
            }
            return LoginResponse.builder()
                    .username(userDetails.getUsername())
                    .email(user.getUserDetail().getEmail())
                    .authorities(roles)
                    .build();
        }
    }

}
