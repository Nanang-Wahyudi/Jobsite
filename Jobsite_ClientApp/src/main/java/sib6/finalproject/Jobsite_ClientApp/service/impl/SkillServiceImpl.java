package sib6.finalproject.Jobsite_ClientApp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import sib6.finalproject.Jobsite_ClientApp.model.response.SkillResponse;
import sib6.finalproject.Jobsite_ClientApp.service.SkillService;

@Service
public class SkillServiceImpl implements SkillService{
    
    @Autowired
    private RestTemplate restTemplate;

    @Value("${server.base.url}/skill")
    private String url;

	@Override
	public SkillResponse deleteSkill(String id) {
		ResponseEntity<SkillResponse> response = restTemplate
                .exchange(url.concat("/delete/" + id),
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<SkillResponse>() {
                        });

        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            throw new RuntimeException("Failed to Delete Skill");
        }
	}
    
}
