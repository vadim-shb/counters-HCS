package com.vdshb.security;

import com.vdshb.security.domain.entity.SecurityUser;
import com.vdshb.security.domain.request.EmailPasswordCredentials;
import com.vdshb.security.repository.SecurityUserRepository;
import com.vdshb.security.service.SecurityTokensService;
import com.vdshb.security.service.SecurityUserService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

@Component
@Qualifier("EmailPasswordAuthenticationProvider")
public class EmailPasswordAuthenticationProvider implements AuthenticationProvider {

    @Inject
    private SecurityUserService securityUserService;

    @Inject
    private SecurityTokensService securityTokensService;

    @Inject
    private SecurityUserRepository securityUserRepository;

    @Transactional
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        EmailPasswordCredentials credentials = (EmailPasswordCredentials) authentication.getCredentials();
        SecurityUser securityUser = securityUserRepository.findByEmail(credentials.getEmail());
        if (securityUser != null && securityUserService.isCorrectPassword(securityUser, credentials.getPassword())) {
            List<GrantedAuthority> authorities = securityUserService.findAuthorities(securityUser.getId());
            securityTokensService.renewTokens(securityUser);
            return new SecurityUserToken(securityUser, authorities);
        }
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(EmailPasswordAuthentication.class);
    }
}
