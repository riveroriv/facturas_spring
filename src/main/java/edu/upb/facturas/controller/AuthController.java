package edu.upb.facturas.controller;

import edu.upb.facturas.dao.request.SignUpRequest;
import edu.upb.facturas.dao.request.SigninRequest;
import edu.upb.facturas.dao.response.JwtAuthenticationResponse;
import edu.upb.facturas.entity.service.AuthenticationService;
import edu.upb.facturas.entity.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("${api.auth.path}")
@RequiredArgsConstructor
@CrossOrigin(
        origins = "*",
        methods = {
                RequestMethod.GET,
                RequestMethod.POST
        })
public class AuthController {

    private final AuthenticationService authenticationService;
    @PostMapping("/signup")
    public ResponseEntity<JwtAuthenticationResponse> signup(@RequestBody SignUpRequest request) {
        return ResponseEntity.ok(authenticationService.signup(request));
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtAuthenticationResponse> signin(@RequestBody SigninRequest request) {
        return ResponseEntity.ok(authenticationService.signin(request));
    }
}
