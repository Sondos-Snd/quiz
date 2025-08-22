package com.interview.prep.repo;

import com.interview.prep.domain.Disclaimer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "disclaimers")
public interface DisclaimerRepo extends JpaRepository<Disclaimer, Long> {}
