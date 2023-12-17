package edu.upb.facturas.controller;

import edu.upb.facturas.dao.entity.UserDto;
import edu.upb.facturas.dao.request.ChangePasswordRequest;
import edu.upb.facturas.entity.service.AuthenticationService;
import edu.upb.facturas.entity.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@Slf4j
@RestController
@RequestMapping("${api.app.path}")
@CrossOrigin(
        origins = "*",
        methods = {
                RequestMethod.GET,
                RequestMethod.POST,
                RequestMethod.DELETE,
                RequestMethod.PUT
        })
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    SimpleResponse sr;
    @Autowired
    AuthenticationService authenticationService;


    @GetMapping("/user")
    public ResponseEntity<?> getUser(){
        try {
            log .info("User request: get info");
            return sr.get(200, userService.user());
        } catch (NoSuchElementException exception) {
            return sr.get(404);
        } catch (Exception exception) {
            return sr.get(500);
        }
    }
    @PostMapping("/user")
    public ResponseEntity<?> changePassword(
            @RequestBody ChangePasswordRequest request
            ){
        try {
            log .info("User request: updating info");
            return sr.get(200, authenticationService.changePassword(request));
        } catch (IllegalArgumentException exception) {
            return sr.get(400);
        } catch (Exception exception) {
            return sr.get(500);
        }
    }
    @PutMapping("/user")
    public ResponseEntity<?> updateUser(
            @RequestBody UserDto user
    ){
        try {
            log .info("User request: updating info");
            return sr.get(200, userService.update(user));
        } catch (NoSuchElementException exception) {
            return sr.get(404);
        } catch (Exception exception) {
            return sr.get(500);
        }
    }
    @DeleteMapping("/user")
    public ResponseEntity<?> deleteUser(){
        try {
            log .info("User request: delete info");
            return sr.get(200, userService.deleteAccount());
        } catch (NoSuchElementException exception) {
            return sr.get(404);
        } catch (Exception exception) {
            return sr.get(500);
        }
    }
}
