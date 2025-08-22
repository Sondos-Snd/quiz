package com.interview.prep.repo;

import com.interview.prep.domain.Complaint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "complaints")
public interface ComplaintRepo extends JpaRepository<Complaint, Long> {}
