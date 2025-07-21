package org.madhawaa.security;

import io.quarkus.security.identity.SecurityIdentity;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import lombok.Getter;

import java.util.Set;

@RequestScoped
@Getter
public class AuthenticatedUser {

    @Inject
    SecurityIdentity identity;

    public String getUsername() {
        return identity.getPrincipal().getName();
    }

    public Set<String> getRoles() {
        return identity.getRoles();
    }

    public boolean hasRole(String role) {
        return identity.getRoles().contains(role);
    }
}
