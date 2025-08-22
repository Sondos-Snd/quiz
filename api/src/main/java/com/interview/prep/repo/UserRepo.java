package com.interview.prep.repo;

import com.interview.prep.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource(path = "users")
public interface UserRepo extends JpaRepository<User, Long> {
    // uses citext case-insensitive equality
    Optional<User> findByEmail(String email);

    // keep the old signature, but avoid query derivation for "IgnoreCase"
    default Optional<User> findByEmailIgnoreCase(String email) {
        return findByEmail(email);
    }

//    @Query(value = "select * from \"user\" where email = CAST(:email as citext) limit 1", nativeQuery = true)
//    Optional<User> findByEmailIgnoreCase(@Param("email") String email);
}




