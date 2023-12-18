package edu.upb.facturas.dao.entity;


import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceDto {
    private Long id;
    private String servicio;
    private String monto;
    private String emision;
}
