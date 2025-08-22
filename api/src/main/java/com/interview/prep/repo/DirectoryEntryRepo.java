package com.interview.prep.repo;

import com.interview.prep.domain.DirectoryEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "directory-entries")
public interface DirectoryEntryRepo extends JpaRepository<DirectoryEntry, Long> {}
