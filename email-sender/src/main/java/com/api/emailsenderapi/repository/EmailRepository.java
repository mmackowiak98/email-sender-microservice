package com.api.emailsenderapi.repository;

import com.api.emailsenderapi.model.Email;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmailRepository extends JpaRepository<Email,Long> {
    Optional<Email> findByEmailAddress(String email);
}
