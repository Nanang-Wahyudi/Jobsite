package sib6.finalproject.Jobsite_ServerApp.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import sib6.finalproject.Jobsite_ServerApp.entity.Role;
import sib6.finalproject.Jobsite_ServerApp.model.enums.RoleEnum;
import sib6.finalproject.Jobsite_ServerApp.repository.RoleRepository;

@Slf4j
@Component("roleConfig")
public class RoleConfig {

    RoleConfig(RoleRepository roleRepository) {
        log.info("Checking Role Config Log Presented");
        for (RoleEnum re : RoleEnum.values()) {
            try {
                Role role =  roleRepository.findByName(re)
                        .orElseThrow(() -> new IllegalStateException("Role not found"));
                log.info("Role {} Has Been Found!", role.getName());

            } catch (Exception e) {
                log.info("Role {} is Not Found, Inserting to DB . . .", re.name());
                Role role = Role.builder()
                        .name(re)
                        .build();
                roleRepository.save(role);
            }
        }
    }

}
