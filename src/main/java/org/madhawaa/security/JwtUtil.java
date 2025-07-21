package org.madhawaa.security;

import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import java.time.Duration;
import java.util.Set;

@ApplicationScoped
public class JwtUtil {

    public String generateToken(String username, Set<String> roles , Integer userId) {
        return Jwt.issuer("course-app")
                .upn(username)
                .groups(roles)
                .claim("userId", userId)
                .expiresIn(Duration.ofHours(6))
                .sign();
    }
}
