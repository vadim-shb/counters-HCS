package com.vdshb.security;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface SecurityUserRepository extends CrudRepository<SecurityUser, Long> {

    SecurityUser findByUsername(String username);

    SecurityUser findByRefreshToken(String refreshToken);

    @Modifying
    @Transactional
    @Query("update SecurityUser u set u.accessToken = null, u.refreshToken = null, u.accessTokenExpirationDateTime = null, u.refreshTokenExpirationDateTime = null where u.id = :userId")
    void dropAuthTokens(@Param("userId") Long userId);
}