package com.interview.prep.repo;

import com.interview.prep.domain.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "invoices")
public interface InvoiceRepo extends JpaRepository<Invoice, Long> {}
