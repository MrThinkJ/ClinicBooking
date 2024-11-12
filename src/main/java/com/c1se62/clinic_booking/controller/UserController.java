package com.c1se62.clinic_booking.controller;

import com.c1se62.clinic_booking.dto.request.AppointmentRequest;
import com.c1se62.clinic_booking.dto.request.ForgotPasswordRequest;
import com.c1se62.clinic_booking.entity.User;
import com.c1se62.clinic_booking.repository.UserRepository;
import com.c1se62.clinic_booking.service.AppointmentServices.AppointmentServices;
import com.c1se62.clinic_booking.service.AuthenticationServices.AuthenticationServices;
import com.c1se62.clinic_booking.service.Email.EmailService;
import com.c1se62.clinic_booking.service.UserServices.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    UserServices userServices;
    @Autowired
    private AuthenticationServices authenticationService;
    @Autowired
    private AppointmentServices appointmentServices;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/booking")
    public ResponseEntity<String> addBooking(@RequestBody AppointmentRequest appointmentRequest) {
        try{
            Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
            if(!(authentication instanceof AnonymousAuthenticationToken)){
                Jwt jwt = ((JwtAuthenticationToken) authentication).getToken();
                String userIdStr = jwt.getClaim("sub");
                User user = userRepository.findByUsername(userIdStr).orElse(null);
                   appointmentServices.addAppointment(appointmentRequest,user);
                return ResponseEntity.status(HttpStatus.OK).body("Đặt lịch khám thành công");
            }else{
                return ResponseEntity.status(403).body("You must login");
            }
        }catch (Exception e){
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }
    @PostMapping("/forgotpassword")
    public ResponseEntity<String> forgotPassword(@RequestBody ForgotPasswordRequest forgotPasswordRequest) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            // Kiểm tra nếu người dùng đã đăng nhập
            if (!(authentication instanceof AnonymousAuthenticationToken)) {
                Jwt jwt = ((JwtAuthenticationToken) authentication).getToken();
                String userIdStr = jwt.getClaim("sub");

                // Tìm người dùng dựa trên username
                User user = userRepository.findByUsername(userIdStr).orElse(null);

                if (user == null) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Người dùng không tồn tại");
                }

                // Gọi phương thức forgotPassword của UserService
                String responseMessage = userServices.forgotPassword(forgotPasswordRequest, user);

                if (responseMessage.equals("Mật khẩu thay đổi thành công")) {
                    return ResponseEntity.ok().body(responseMessage);
                } else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMessage);
                }
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Bạn cần đăng nhập để thực hiện thao tác này");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi hệ thống: " + e.getMessage());
        }
    }

}
