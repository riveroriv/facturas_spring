package edu.upb.facturas.controller;

import edu.upb.facturas.entity.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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


    @GetMapping("/user")
    public ResponseEntity<?> user(){
        log .info("Solicitud");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("hola");
    }

    @GetMapping("/admin")
    public ResponseEntity<?> admin(){
        log .info("Solicitud");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("hola");
    }
}
