package com.fahad.cafeteria.service.impl;

import com.fahad.cafeteria.constent.CafeConstants;
import com.fahad.cafeteria.jwt.CustomerUserDetailsService;
import com.fahad.cafeteria.jwt.JwtFilter;
import com.fahad.cafeteria.jwt.JwtUtil;
import com.fahad.cafeteria.model.User;
import com.fahad.cafeteria.repository.UserRepository;
import com.fahad.cafeteria.service.UserService;
import com.fahad.cafeteria.utils.CafeUtils;
import com.fahad.cafeteria.utils.EmailUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private CustomerUserDetailsService customerUserDetailsService;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private JwtFilter jwtFilter;

    @Autowired
    private EmailUtils emailUtils;


    @Override
    public ResponseEntity<?> signUp(Map<String, String> requestMap) {
        log.info("Inside signup {}", requestMap);
        try {
            if (validateSignUpMap(requestMap)){
                Optional<User> user = userRepository.findByEmail(requestMap.get("email"));

                if (!user.isPresent()){
                    userRepository.save(getUserFromMap(requestMap));
                    return CafeUtils.getResponseEntity("Successfully registered !!", HttpStatus.OK);
                }else {
                    return CafeUtils.getResponseEntity("Email already exists !!", HttpStatus.BAD_REQUEST);
                }
            }
            else {
                return CafeUtils.getResponseEntity(CafeConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public boolean validateSignUpMap(Map<String, String> requestMap) {
        if (requestMap.containsKey("name") && requestMap.containsKey("contactNumber")
                && requestMap.containsKey("email") && requestMap.containsKey("password")){
            return true;
        }
        return false;
    }

    @Override
    public ResponseEntity<?> getAllUser() {
        try {
            List<User>allUsers = userRepository.findAll();
            return new ResponseEntity<>(allUsers, HttpStatus.OK);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }


    private User getUserFromMap(Map<String, String> requestMap){
        User user = new User();
        user.setName(requestMap.get("name"));
        user.setContactNumber(requestMap.get("contactNumber"));
        user.setPassword(requestMap.get("password"));
        user.setEmail(requestMap.get("email"));
        user.setStatus("false");
        return user;
    }

    @Override
    public ResponseEntity<?> login(Map<String, String> requestMap) {
        log.info("Inside login");
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(requestMap.get("email"),requestMap.get("password") )
            );

            if (auth.isAuthenticated()){
                if (customerUserDetailsService.getUserDetail().getStatus().equalsIgnoreCase("true")){
                    return new ResponseEntity<String>("{\"token\":\"" +
                            jwtUtil.genarateToken(customerUserDetailsService.getUserDetail().getEmail(),
                                    customerUserDetailsService.getUserDetail().getRole()) + "\"}", HttpStatus.OK);
                }else {
                    return new ResponseEntity<String>("{\"message\":\"" + "Wait for admin approval."+"\"}",HttpStatus.BAD_REQUEST);
                }
            }
        }catch (Exception ex){
            log.error("{}", ex);
        }
        return new ResponseEntity<String>("{\"message\":\"" + "Bad Credentials"+"\"}",HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<?> update(Map<String, String> requestMap) {
        try {
            Integer id = Integer.parseInt(requestMap.get("id"));
            String status = requestMap.get("status");
            Optional<User> user = userRepository.findById(id);
            if (user.isPresent()){
                user.get().setStatus(status);
                userRepository.save(user.get());

//           email integration. will do later
//                List<String> allAdminEmails = userRepository.findAllAdminEmails("admin");
//                String email = user.get().getEmail();
//                sendMailToAllAdmin(status,email, allAdminEmails);
                return CafeUtils.getResponseEntity("User updated successfully !!" , HttpStatus.OK);

            }else{
                return CafeUtils.getResponseEntity("User id doesn't exist !!" , HttpStatus.OK);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);

    }


    private void sendMailToAllAdmin(String status, String user, List<String> allAdminsEmails) {
         allAdminsEmails.remove(jwtFilter.getCurrentUser());
         if (status != null && status.equalsIgnoreCase("true")){
            emailUtils.sendSimpleMessage(jwtFilter.getCurrentUser(),
                    "Account Approved", "USER:- " + user + " \n is approved by \nADMIN:-" + jwtFilter.getCurrentUser(), allAdminsEmails);
        }else {
             emailUtils.sendSimpleMessage(jwtFilter.getCurrentUser(),
                     "Account Disbaled", "USER:- " + user + " \n is disabled by \nADMIN:-" + jwtFilter.getCurrentUser(), allAdminsEmails);
         }
    }

//    use to validate when user travel from one page to another
//    to check whether he has valid token or not
    @Override
    public ResponseEntity<?> checkToken() {
        return CafeUtils.getResponseEntity("true", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> changePassword(Map<String, String> requestMap) {
        try {
           Optional<User>user = userRepository.findByEmail(jwtFilter.getCurrentUser());
           if (user.isPresent()){
              if (user.get().getPassword().equals(requestMap.get("oldPassword"))){
                 user.get().setPassword(requestMap.get("newPassword"));
                 userRepository.save(user.get());
                 return CafeUtils.getResponseEntity("Password updated successfully !!", HttpStatus.OK);
              }
               return CafeUtils.getResponseEntity("Incorrect old password !!", HttpStatus.INTERNAL_SERVER_ERROR);
           }
           return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
        }catch (Exception ex){
           ex.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<?> forgotPassword(Map<String, String> requestMap) {
        try {
            Optional<User> user = userRepository.findByEmail(requestMap.get("email"));

            if (user.isPresent()){
               emailUtils.forgotMail(user.get().getEmail(), "Credentials by cafe management system", user.get().getPassword());
            }
            return CafeUtils.getResponseEntity("Check your mail for credentials !! ", HttpStatus.INTERNAL_SERVER_ERROR);

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
