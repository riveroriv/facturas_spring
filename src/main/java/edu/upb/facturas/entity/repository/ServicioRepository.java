package edu.upb.facturas.entity.repository;

import edu.upb.facturas.entity.model.Servicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ServicioRepository extends JpaRepository<Servicio, Long> {

    List<Servicio> findByOwner(Long owner);
    Optional<Servicio> findByOwnerAndClientNumber(Long owner, String clientNumber);

    Optional<Servicio> findByOwnerAndClientNumberAndService(Long owner, String clientNumber, String service);
}
