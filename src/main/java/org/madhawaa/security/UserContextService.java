package org.madhawaa.security;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.jwt.JsonWebToken;

@RequestScoped
public class UserContextService {

    @Inject
    JsonWebToken jwt;

    public Integer getUserId() {
        Object claim = jwt.getClaim("userId");
        return claim != null ? Integer.parseInt(claim.toString()) : null;
    }

    public String getUsername() {
        return jwt.getClaim("preferred_username");
    }

    public String getRole() {
        return jwt.getGroups().stream().findFirst().orElse(null);
    }

    public boolean isLoggedIn() {
        return jwt != null && getUserId() != null;
    }
}
