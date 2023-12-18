package edu.upb.facturas.service.impl;

import edu.upb.facturas.entity.model.Servicio;
import edu.upb.facturas.entity.model.User;
import edu.upb.facturas.entity.repository.ServicioRepository;
import edu.upb.facturas.entity.repository.UserRepository;
import edu.upb.facturas.service.ServicioService;
import lombok.AllArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ServicioServiceImpl implements ServicioService {
    private final ServicioRepository servicioRepository;
    private final UserRepository userRepository;

    @Override
    public List<Servicio> findAll() {
        return servicioRepository.findAll();
    }

    @Override
    public List<Servicio> findByOwner() {
        return servicioRepository.findByOwner(getCurrentUser().getId());
    }

    @Override
    public Servicio update(Servicio servicio) {
        Servicio foundServicio = servicioRepository.findById(servicio.getId())
                .orElseThrow(() -> new NoSuchElementException("Service not found"));
        foundServicio.setService(servicio.getService());
        foundServicio.setName(servicio.getName());
        foundServicio.setClientNumber(servicio.getClientNumber());
        return servicioRepository.save(foundServicio);
    }

    @Override
    public Servicio create(Servicio servicio) {
        Optional<Servicio> foundServicio = servicioRepository.findByOwnerAndClientNumberAndService(
                getCurrentUser().getId(), servicio.getClientNumber(), servicio.getService());
        if(foundServicio.isPresent()) {
            throw new DataIntegrityViolationException("Ya existe");
        }
        Servicio newServicio = new Servicio();
        newServicio.setOwner(getCurrentUser().getId());
        newServicio.setService(servicio.getService());
        newServicio.setClientNumber(servicio.getClientNumber());
        newServicio.setName(servicio.getName());
        return servicioRepository.save(newServicio);
    }

    @Override
    public boolean delete(Long id) {
        Servicio foundServicio = servicioRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Service not found"));
        if( foundServicio.getOwner() != getCurrentUser().getId() ){
            throw new DataIntegrityViolationException("No es tuyo");
        }
        servicioRepository.delete(foundServicio);
        return true;
    }
    private User getCurrentUser() {
        return userRepository.findByUsername(
                SecurityContextHolder.getContext().getAuthentication().getName()
        ).orElseThrow(() -> new IllegalArgumentException("User not found"));
    }
}
