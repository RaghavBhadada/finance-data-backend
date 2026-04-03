package com.raghav.desibusiness.repository;


import com.raghav.desibusiness.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email); // created predifined function will use them later During Login

    boolean existsByEmail(String email); // created predifined function will use them later During Registration

    boolean existsByPhone(String phone); // created predifined function will use them later During registration
}

