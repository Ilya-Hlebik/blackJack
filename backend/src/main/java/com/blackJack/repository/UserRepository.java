package com.blackJack.repository;


import com.blackJack.dbo.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


public interface UserRepository extends AbstractRepository<User> {

    boolean existsByUsername(String username);

    User findByUsername(String username);

    @Transactional
    void deleteByUsername(String username);

    Optional<User> findByEmail(String email);
}
