package edu.upb.facturas.service;

import edu.upb.facturas.dao.request.ChangePasswordRequest;
import edu.upb.facturas.dao.request.SignUpRequest;
import edu.upb.facturas.dao.request.SigninRequest;
import edu.upb.facturas.dao.response.JwtAuthenticationResponse;

public interface AuthenticationService {
    JwtAuthenticationResponse signup(SignUpRequest request);

    JwtAuthenticationResponse signin(SigninRequest request);


    Boolean changePassword(ChangePasswordRequest changePasswordRequest);
}