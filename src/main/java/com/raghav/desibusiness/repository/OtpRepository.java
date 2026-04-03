package com.raghav.desibusiness.repository;

import com.raghav.desibusiness.entity.Otp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OtpRepository extends JpaRepository<Otp, Long> {

    Optional<Otp> findByUserId(Long userId);

}
