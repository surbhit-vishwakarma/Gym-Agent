package dev.surbhit.gym.agent.utils;

import dev.surbhit.gym.agent.model.db.AppUser;
import org.apache.catalina.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;
import java.util.UUID;

public final class SecurityUtils {

    private SecurityUtils() {}

    public static UUID getCurrentUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        AppUser obj = (AppUser) auth.getPrincipal();
        return obj.getUserId();
    }
}