package edu.upb.facturas.dao.entity;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

public class TigoAdapterDto implements InvoiceAdapter{
    private Long id;
    private String servicio;
    private String monto;
    private String fechaEmision;
    private String fechaExpiracion;

    @Override
    public InvoiceDto adaptFacturas() {
        InvoiceDto io = new InvoiceDto();
        io.setId(getId());
        io.setEmision(getFechaEmision());
        io.setServicio(getServicio());
        io.setMonto(getMonto());
        return io;
    }
}
