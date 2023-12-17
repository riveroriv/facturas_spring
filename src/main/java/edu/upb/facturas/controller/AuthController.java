package edu.upb.facturas.controller;

import edu.upb.facturas.dao.request.SignUpRequest;
import edu.upb.facturas.dao.request.SigninRequest;
import edu.upb.facturas.entity.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(("${api.app.path}"+"${api.auth.path}"))
@RequiredArgsConstructor
@CrossOrigin(
        origins = "*",
        methods = {
                RequestMethod.GET,
                RequestMethod.POST
        })
public class AuthController {
    @Value("${api.response.auth-fail}")
    private String authFail;
    @Autowired
    private SimpleResponse sr;

    private final AuthenticationService authenticationService;
    @PostMapping("${api.auth.signup.path}")
    public ResponseEntity<?> signup(@RequestBody SignUpRequest request) {
        try {
            return sr.get(200, authenticationService.signup(request));
        } catch (DataIntegrityViolationException e) {
            return sr.get(409);
        } catch (Exception e) {
            return sr.get(500);
        }
    }

    @PostMapping("${api.auth.signin.path}")
    public ResponseEntity<?> signin(@RequestBody SigninRequest request) {
       try {
           return sr.get(200, authenticationService.signin(request));
       } catch (IllegalArgumentException e) {
           return sr.get(400);
       } catch (AuthenticationException e) {
           return sr.get(authFail, 400);
       }
       catch (Exception e) {
           return sr.get(500);
       }

    }
}
