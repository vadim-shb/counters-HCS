package com.vdshb.security.domain;

import com.vdshb.security.SecurityUserToken;
import org.springframework.security.core.Authentication;

public class AuthenticatedUserResponse {

    private AuthenticationSession session;
    private PublicUser user;

    public AuthenticatedUserResponse(Authentication authentication) {
        SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
        session = new AuthenticationSession();
        session.setAccessToken(securityUser.getAccessToken());
        session.setRefreshToken(securityUser.getRefreshToken());
        session.setAccessTokenExpirationTime(securityUser.getAccessTokenExpirationDateTime());
        session.setRefreshTokenExpirationTime(securityUser.getRefreshTokenExpirationDateTime());
        user = new PublicUser((SecurityUserToken) authentication);
    }

    public AuthenticationSession getSession() {
        return session;
    }

    public PublicUser getUser() {
        return user;
    }
}