package edu.upb.facturas.dao.request;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SigninRequest {
    private String username;
    private String password;
}