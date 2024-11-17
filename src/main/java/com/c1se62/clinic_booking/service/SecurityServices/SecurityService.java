package com.c1se62.clinic_booking.service.SecurityServices;

import com.c1se62.clinic_booking.entity.User;
import com.c1se62.clinic_booking.exception.APIException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public interface SecurityService {
    User getCurrentUser();
    boolean hasRole(String role);
    Integer getCurrentUserId();
    void validateUserAccess(Integer userId);
    void validateDoctorAccess(Integer doctorId);
    void validateAdminAccess();
}
