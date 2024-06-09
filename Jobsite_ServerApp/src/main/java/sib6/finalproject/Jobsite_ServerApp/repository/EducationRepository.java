package sib6.finalproject.Jobsite_ServerApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sib6.finalproject.Jobsite_ServerApp.entity.Education;

@Repository
public interface EducationRepository extends JpaRepository<Education, String> {

   Education findByName(String name);

}
