package com.interview.prep.repo;

import com.interview.prep.domain.Consultation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "consultations")
public interface ConsultationRepo extends JpaRepository<Consultation, Long> {
}
