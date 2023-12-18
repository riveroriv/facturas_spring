package edu.upb.facturas.service.impl;

import edu.upb.facturas.dao.entity.InvoiceDto;
import edu.upb.facturas.entity.model.Servicio;
import edu.upb.facturas.entity.model.User;
import edu.upb.facturas.entity.repository.ServicioRepository;
import edu.upb.facturas.entity.repository.UserRepository;
import edu.upb.facturas.service.InvoiceService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.client.RestTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Service
@AllArgsConstructor
public class InvoiceServiceImpl implements InvoiceService {
    @Autowired
    private ServicioRepository servicioRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<InvoiceDto> getByService(Long id) {
        Servicio servicio = servicioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Not found"));

        String url = "";
        if(servicio.getService().equalsIgnoreCase("001")){
            url="https://d1496fc1-c1b3-4dc5-a477-3fe072ff9215.mock.pstmn.io/tigo/v1/55800/invoice";
        } else {
            url="https://d1496fc1-c1b3-4dc5-a477-3fe072ff9215.mock.pstmn.io/cre/v4/socio/2200/facturas";
        }

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<InvoiceDto[]> response = restTemplate.getForEntity(url, InvoiceDto[].class);

        if (response.getStatusCode().is2xxSuccessful()) {
            return Arrays.asList(response.getBody());
        } else {
            return null;
        }
    }


    @Override
    public List<List<InvoiceDto>> getAll() {
        List<Servicio> servicios = servicioRepository.findByOwner(getCurrentUser().getId());
        List<List<InvoiceDto>> invoices = new ArrayList<>();
        for(Servicio s: servicios) {
            String url = "";
            if(s.getService().equalsIgnoreCase("001")){
                url="https://d1496fc1-c1b3-4dc5-a477-3fe072ff9215.mock.pstmn.io/tigo/v1/55800/invoice";
            } else {
                url="https://d1496fc1-c1b3-4dc5-a477-3fe072ff9215.mock.pstmn.io/cre/v4/socio/2200/facturas";
            }
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<InvoiceDto[]> response = restTemplate.getForEntity(url, InvoiceDto[].class);
            if (response.getStatusCode().is2xxSuccessful()) {
                invoices.add(Arrays.asList(response.getBody()));
            }
        }
        return invoices;
    }

    private User getCurrentUser() {
        return userRepository.findByUsername(
                SecurityContextHolder.getContext().getAuthentication().getName()
        ).orElseThrow(() -> new IllegalArgumentException("User not found"));
    }
}
