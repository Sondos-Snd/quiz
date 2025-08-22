package com.interview.prep.repo;

import com.interview.prep.domain.LegalUpdate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "legal-updates")
public interface LegalUpdateRepo extends JpaRepository<LegalUpdate, Long> {}
