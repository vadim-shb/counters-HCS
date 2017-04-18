package com.vdshb.security;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpRequestResponseHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

@Component
public class AccessTokenSecurityContextRepository implements SecurityContextRepository {

    private final Cache<String, SecurityContext> cachedSecurityContexts;

    public AccessTokenSecurityContextRepository(@Value("${security.access-token-expiration-time}") Long accessTokenExpirationTime) {
        cachedSecurityContexts = Caffeine.newBuilder()
                .expireAfterWrite(accessTokenExpirationTime, TimeUnit.SECONDS)
                .build();
    }

    @Override
    public SecurityContext loadContext(HttpRequestResponseHolder requestResponseHolder) {
        String accessToken = requestResponseHolder.getRequest().getHeader("access-token");
        if (accessToken != null) {
            SecurityContext cachedSecurityContext = cachedSecurityContexts.getIfPresent(accessToken);
            if (cachedSecurityContext != null){
                return cachedSecurityContext;
            }
        }
        return SecurityContextHolder.createEmptyContext();
    }

    @Override
    public void saveContext(SecurityContext context, HttpServletRequest request, HttpServletResponse response) {
        if (context.getAuthentication() != null) {
            String accessToken = ((SecurityUser)context.getAuthentication().getPrincipal()).getAccessToken();
            cachedSecurityContexts.put(accessToken, context);
        }
    }

    @Override
    public boolean containsContext(HttpServletRequest request) {
        //todo: try just:| return request.getHeader("access-token") != null;
        String accessToken = request.getHeader("access-token");
        return cachedSecurityContexts.getIfPresent(accessToken) != null;
    }
}
