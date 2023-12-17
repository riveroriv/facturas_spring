package edu.upb.facturas.entity.service;

import edu.upb.facturas.dao.request.SignUpRequest;
import edu.upb.facturas.dao.request.SigninRequest;
import edu.upb.facturas.dao.response.JwtAuthenticationResponse;

public interface AuthenticationService {
    JwtAuthenticationResponse signup(SignUpRequest request);

    JwtAuthenticationResponse signin(SigninRequest request);
}