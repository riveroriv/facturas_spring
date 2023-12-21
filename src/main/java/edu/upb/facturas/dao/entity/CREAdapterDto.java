package edu.upb.facturas.dao.entity;


import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

public class CREAdapterDto implements InvoiceAdapter {
    private Long id;
    private String servicio;
    private Double monto;
    private String emision;

    @Override
    public InvoiceDto adaptFacturas() {
        InvoiceDto io = new InvoiceDto();
        io.setId(getId());
        io.setEmision(getEmision());
        io.setServicio(getServicio());
        io.setMonto(getMonto().toString());
        return io;
    }
}
