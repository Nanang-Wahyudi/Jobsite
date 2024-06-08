package sib6.finalproject.Jobsite_ServerApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sib6.finalproject.Jobsite_ServerApp.entity.Company;


@Repository
public interface CompanyRepository extends JpaRepository<Company, String> {

    Boolean existsByName(String name);

    Boolean existsByEmail(String email);

}
