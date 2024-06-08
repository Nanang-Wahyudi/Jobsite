package sib6.finalproject.Jobsite_ServerApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sib6.finalproject.Jobsite_ServerApp.entity.Privilege;
import sib6.finalproject.Jobsite_ServerApp.model.enums.PrivilegeEnum;

import java.util.Optional;

@Repository
public interface PrivilegeRepository extends JpaRepository<Privilege, String> {

    Optional<Privilege> findByName(PrivilegeEnum privilegeName);

}
