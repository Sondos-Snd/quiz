package com.interview.prep.repo;

import com.interview.prep.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "messages")
public interface MessageRepo extends JpaRepository<Message, Long> {}
