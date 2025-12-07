package com.woofly.courseservice.repository;

import com.woofly.courseservice.model.RefreshToken;
import com.woofly.courseservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer> {
    Optional<RefreshToken> findByToken(String token);

    // İstifadəçinin köhnə refresh tokenini tapmaq üçün
    Optional<RefreshToken> findByUser(User user);
}