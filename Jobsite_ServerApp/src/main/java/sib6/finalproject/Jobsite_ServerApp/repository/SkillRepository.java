package sib6.finalproject.Jobsite_ServerApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sib6.finalproject.Jobsite_ServerApp.entity.Skill;

@Repository
public interface SkillRepository extends JpaRepository<Skill, String> {

    Skill findByName(String name);

}
