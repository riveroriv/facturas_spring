package edu.upb.facturas.dao.request;

import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {
    private String email;
    private String password;
    private String username;
    private String name;
    private LocalDate birthday;
}