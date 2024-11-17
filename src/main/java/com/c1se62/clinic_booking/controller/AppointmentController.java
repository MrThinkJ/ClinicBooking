package com.c1se62.clinic_booking.controller;

import com.c1se62.clinic_booking.dto.request.PrescriptionCreateDTO;
import com.c1se62.clinic_booking.service.AppointmentServices.AppointmentServices;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
@AllArgsConstructor
public class AppointmentController {
    AppointmentServices appointmentServices;

    @PostMapping("/{id}/prescription")
    public ResponseEntity<Boolean> addPrescription(@PathVariable Integer id,
                                                   @RequestBody List<PrescriptionCreateDTO> prescriptions) {
        return ResponseEntity.ok(appointmentServices.addPrescriptions(prescriptions, id));
    }
}
