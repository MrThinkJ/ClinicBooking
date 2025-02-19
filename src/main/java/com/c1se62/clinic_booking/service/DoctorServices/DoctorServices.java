package com.c1se62.clinic_booking.service.DoctorServices;

import com.c1se62.clinic_booking.dto.request.DoctorCreatedDTO;
import com.c1se62.clinic_booking.dto.request.DoctorUpdatedDTO;
import com.c1se62.clinic_booking.dto.response.DoctorRatingResponse;
import com.c1se62.clinic_booking.dto.response.DoctorResponse;
import com.c1se62.clinic_booking.entity.Doctor;

import java.util.List;

public interface DoctorServices {
    List<DoctorResponse> getAllDoctors() throws Exception;
    List<DoctorResponse> getAllDoctorsByDepartment(String depamentname) throws Exception;
    DoctorResponse getDoctorById(int id) throws Exception;
    List<DoctorRatingResponse> getRatingsByDoctorId(int doctorId);
    DoctorResponse addDoctor(DoctorCreatedDTO doctor);
    DoctorResponse updateDoctor(int id, DoctorUpdatedDTO doctor);
    void deleteDoctor(int id);
}
