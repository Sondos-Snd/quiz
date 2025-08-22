package com.interview.prep.repo;

import com.interview.prep.domain.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "audit-logs")
public interface AuditLogRepo extends JpaRepository<AuditLog, Long> {}
