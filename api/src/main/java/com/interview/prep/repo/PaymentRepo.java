package com.interview.prep.repo;

import com.interview.prep.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "payments")
public interface PaymentRepo extends JpaRepository<Payment, Long> {}
