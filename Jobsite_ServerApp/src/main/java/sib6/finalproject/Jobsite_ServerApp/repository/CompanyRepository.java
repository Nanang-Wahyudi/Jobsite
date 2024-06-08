package sib6.finalproject.Jobsite_ServerApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sib6.finalproject.Jobsite_ServerApp.entity.Company;

import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, String> {

    Boolean existsByName(String name);

    Boolean existsByEmail(String email);

    @Query("select c.id from Company c where c.name = :name")
    Optional<String> findIdByName(@Param("name") String name);

}
