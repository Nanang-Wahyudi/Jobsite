package sib6.finalproject.Jobsite_ServerApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sib6.finalproject.Jobsite_ServerApp.entity.Applicant;
import sib6.finalproject.Jobsite_ServerApp.entity.Feedback;
import sib6.finalproject.Jobsite_ServerApp.entity.UserDetail;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, String> {

    boolean existsByApplicantAndUserDetail(Applicant applicant, UserDetail userDetail);

}
