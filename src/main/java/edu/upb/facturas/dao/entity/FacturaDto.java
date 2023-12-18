package edu.upb.facturas.dao.entity;


import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class FacturaDto {
    private Long id;
    private String servicio;
    private Double monto;
    private LocalDate emision;
    private Integer empresa;
}
