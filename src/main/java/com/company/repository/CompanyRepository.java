package com.company.repository;

import com.company.dao.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

    Page<Company> findAll(Pageable pageable);

    Optional<Company> findByReference(UUID reference);

}
