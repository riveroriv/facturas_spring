package edu.upb.facturas.controller;

import edu.upb.facturas.entity.model.Role;
import edu.upb.facturas.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping(("${api.app.path}"+"${api.admin.path}"))
@CrossOrigin(
        origins = "*",
        methods = {
                RequestMethod.GET,
                RequestMethod.POST,
                RequestMethod.DELETE,
                RequestMethod.PUT
        })
public class AdminController {
    UserService userService;
    @Autowired
    SimpleResponse sr;

    @GetMapping("/user")
    public ResponseEntity<?> findAllUsers() {
       try {
           log .info("Admin request: find all users");
           return sr.get(200, userService.findAll());
       } catch (Exception exception) {
           return sr.get(500);
       }
    }
    @GetMapping("/user/{username}")
    public ResponseEntity<?> findUserByUsername(
            @PathVariable String username
    ){
        try {
            log .info("Admin request: find all users");
            return sr.get(200, userService.findByUsername(username));
        } catch (NoSuchElementException exception) {
            return sr.get(404);
        } catch (Exception exception) {
            return sr.get(500);
        }
    }

    @PostMapping("/user/{username}/role/{role}")
    public ResponseEntity<?> grantPrivileges(
            @PathVariable String username,
            @PathVariable Role role
    ){
        try {
            log .info("Admin request: changing "+username+" privilleges to "+role);
            return sr.get(200, userService.update(username, role));
        } catch (NoSuchElementException exception) {
            return sr.get(404);
        } catch (Exception exception) {
            return sr.get(500);
        }

    }

}
