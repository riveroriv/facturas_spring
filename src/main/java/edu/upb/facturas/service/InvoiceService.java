package edu.upb.facturas.service;

import edu.upb.facturas.dao.entity.InvoiceDto;

import java.util.List;

public interface InvoiceService {

    Object getByService(Long id);

    List<List<InvoiceDto>> getAll();
}