package com.blackJack.repository;

import com.blackJack.dbo.EmailConfirmation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmailRepository extends CrudRepository<EmailConfirmation, String> {
    Optional<EmailConfirmation> findByToken(String token);
    void deleteAllById(String email);
}
