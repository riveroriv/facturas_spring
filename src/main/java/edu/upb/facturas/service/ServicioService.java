package edu.upb.facturas.service;

import edu.upb.facturas.entity.model.Servicio;

import java.util.List;

public interface ServicioService {
    List<Servicio> findAll();
    List<Servicio> findByOwner();

    Servicio update(Servicio servicio);
    Servicio create(Servicio servicio);
    boolean delete(Long id);


}