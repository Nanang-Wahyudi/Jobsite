package sib6.finalproject.Jobsite_ServerApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sib6.finalproject.Jobsite_ServerApp.entity.UserDetail;
import sib6.finalproject.Jobsite_ServerApp.model.enums.RoleEnum;

import java.util.List;


@Repository
public interface UserDetailRepository extends JpaRepository<UserDetail, String> {

    Boolean existsByEmail(String email);

    @Query("SELECT ud FROM UserDetail ud " +
            "JOIN ud.user u " +
            "JOIN u.roles r " +
            "WHERE r.name = :roleName")
    List<UserDetail> findAllByRoleName(@Param("roleName") RoleEnum roleName);

}
