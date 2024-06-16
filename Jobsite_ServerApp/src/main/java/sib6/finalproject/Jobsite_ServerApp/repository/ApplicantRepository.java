package sib6.finalproject.Jobsite_ServerApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sib6.finalproject.Jobsite_ServerApp.entity.Applicant;
import sib6.finalproject.Jobsite_ServerApp.entity.Job;
import sib6.finalproject.Jobsite_ServerApp.entity.UserDetail;

import java.util.List;

@Repository
public interface ApplicantRepository extends JpaRepository<Applicant, String> {

    List<Applicant> findByUserDetailAndJob(UserDetail userDetail, Job job);

}
