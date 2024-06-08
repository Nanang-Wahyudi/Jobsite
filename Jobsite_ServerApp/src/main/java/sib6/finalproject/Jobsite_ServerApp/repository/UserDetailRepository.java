package sib6.finalproject.Jobsite_ServerApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sib6.finalproject.Jobsite_ServerApp.entity.UserDetail;


@Repository
public interface UserDetailRepository extends JpaRepository<UserDetail, String> {

    Boolean existsByEmail(String email);

}
