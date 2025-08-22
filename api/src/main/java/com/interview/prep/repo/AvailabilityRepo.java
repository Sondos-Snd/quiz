package com.interview.prep.repo;

import com.interview.prep.domain.Availability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "availabilities")
public interface AvailabilityRepo extends JpaRepository<Availability, Long> {}
