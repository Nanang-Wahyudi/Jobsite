package sib6.finalproject.Jobsite_ClientApp.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;
import sib6.finalproject.Jobsite_ClientApp.model.request.LoginRequest;
import sib6.finalproject.Jobsite_ClientApp.model.response.LoginResponse;
import sib6.finalproject.Jobsite_ClientApp.service.AuthService;
import sib6.finalproject.Jobsite_ClientApp.util.security.AuthSessionUtil;

@Slf4j
@Service
public class AuthServiceImpl implements AuthService {

    @Value("${server.base.url}/auth/login")
    private String url;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Boolean login(LoginRequest loginRequest) {
        try {
            ResponseEntity<LoginResponse> response = restTemplate
                    .exchange(
                            url,
                            HttpMethod.POST,
                            new HttpEntity<>(loginRequest),
                            LoginResponse.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                setPrinciple(response.getBody(), loginRequest.getPassword());

                Authentication authentication = AuthSessionUtil.getAuthentication();

                log.info("Name : {}", authentication.getName());

                return true;
            }
        } catch (Exception e) {
            return false;
        }

        return false;
    }

    @Override
    public void setPrinciple(LoginResponse loginResponse, String password) {
        List<SimpleGrantedAuthority> authorities = loginResponse
                .getAuthorities()
                .stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginResponse.getUsername(),
                password,
                authorities);

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }

}
