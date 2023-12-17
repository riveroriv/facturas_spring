package edu.upb.facturas.dao.entity;


import lombok.*;

import java.time.LocalDate;
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

public class UserDto {
    private Long id;
    private String username;
    private String name;
    private String email;
    private LocalDate birthday;
}
