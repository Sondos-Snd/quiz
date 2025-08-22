package com.interview.prep.repo;

import com.interview.prep.domain.Consent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(path = "consents")
public interface ConsentRepo extends JpaRepository<Consent, Long> {
    List<Consent> findByUserId(Long userId);

}
