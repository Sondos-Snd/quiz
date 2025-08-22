package com.interview.prep.repo;

import com.interview.prep.domain.Expert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "experts")
public interface ExpertRepo extends JpaRepository<Expert, Long> {}
