package edu.upb.facturas.service.impl;

import edu.upb.facturas.dao.entity.CREAdapterDto;
import edu.upb.facturas.dao.entity.InvoiceDto;
import edu.upb.facturas.dao.entity.TigoAdapterDto;
import edu.upb.facturas.dao.response.SumInvoices;
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

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


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
        String serv = "";
        if(servicio.getService().equalsIgnoreCase("001")){
            url="https://0a4fa728-7397-4a64-a444-76af5bce6169.mock.pstmn.io/tigo/v1/55800/invoice";
            serv = "tigo";
        } else {
            url="https://0a4fa728-7397-4a64-a444-76af5bce6169.mock.pstmn.io/cre/v4/socio/2200/facturas";
            serv = "cre";
        }

        RestTemplate restTemplate = new RestTemplate();
        switch (serv) {
            case "cre":
                ResponseEntity<CREAdapterDto[]> response = restTemplate.getForEntity(url, CREAdapterDto[].class);
                List<InvoiceDto> listInvoiceDTO = Arrays.stream(response.getBody()).map(item -> new InvoiceDto(
                        item.getId(),
                        item.getServicio(),
                        item.getMonto().toString(),
                        item.getEmision()
                )).collect(Collectors.toList());
                return listInvoiceDTO;

            case "tigo":
                ResponseEntity<TigoAdapterDto[]> response2 = restTemplate.getForEntity(url, TigoAdapterDto[].class);
                List<InvoiceDto> listInvoiceDTO2 = Arrays.stream(response2.getBody()).map(item -> new InvoiceDto(
                        item.getId(),
                        item.getServicio(),
                        item.getMonto(),
                        item.getFechaEmision()
                )).collect(Collectors.toList());
                return listInvoiceDTO2;
            default:
                return null;
        }
        //ResponseEntity<InvoiceDto[]> response = restTemplate.getForEntity(url, InvoiceDto[].class);

        //if (response.getStatusCode().is2xxSuccessful()) {
            //return Arrays.asList(response.getBody());
        //} else {
            //return null;
        //}
    }


    @Override
    public List<List<InvoiceDto>> getAll() {
        List<Servicio> servicios = servicioRepository.findByOwner(getCurrentUser().getId());
        List<List<InvoiceDto>> invoices = new ArrayList<>();
        for(Servicio s: servicios) {
            String url = "";
            String serv = "";
            if(s.getService().equalsIgnoreCase("001")){
                url="https://0a4fa728-7397-4a64-a444-76af5bce6169.mock.pstmn.io/tigo/v1/55800/invoice";
                serv = "tigo";
            } else {
                url="https://0a4fa728-7397-4a64-a444-76af5bce6169.mock.pstmn.io/cre/v4/socio/2200/facturas";
                serv = "cre";
            }
            RestTemplate restTemplate = new RestTemplate();
            switch (serv) {
                case "cre":
                    ResponseEntity<CREAdapterDto[]> response = restTemplate.getForEntity(url, CREAdapterDto[].class);
                    List<InvoiceDto> listInvoiceDTO = Arrays.stream(response.getBody()).map(item -> new InvoiceDto(
                            item.getId(),
                            item.getServicio(),
                            item.getMonto().toString(),
                            item.getEmision()
                    )).collect(Collectors.toList());
                    invoices.add(listInvoiceDTO);
                    break;
                case "tigo":
                    ResponseEntity<TigoAdapterDto[]> response2 = restTemplate.getForEntity(url, TigoAdapterDto[].class);
                    List<InvoiceDto> listInvoiceDTO2 = Arrays.stream(response2.getBody()).map(item -> new InvoiceDto(
                            item.getId(),
                            item.getServicio(),
                            item.getMonto().split(" ")[0],
                            item.getFechaEmision()
                    )).collect(Collectors.toList());
                    invoices.add(listInvoiceDTO2);
                    break;
                default:
                    break;
            }
            //ResponseEntity<InvoiceDto[]> response = restTemplate.getForEntity(url, InvoiceDto[].class);
            //if (response.getStatusCode().is2xxSuccessful()) {
                //invoices.add(Arrays.asList(response.getBody()));
            //}
        }
        return invoices;
    }

    @Override
    public SumInvoices getSum() {
        List<List<InvoiceDto>> facturasPorServicio = this.getAll();
        double suma = 0;
        int nro = 0;
        for(List<InvoiceDto> facturas: facturasPorServicio) {
            for(InvoiceDto factura: facturas) {
                suma+= Double.valueOf(factura.getMonto());
                nro++;
            }
        }
        DecimalFormat f = new DecimalFormat("##.00");
        suma = Double.valueOf(f.format(suma));
        SumInvoices si = new SumInvoices();
        si.setSuma(suma);
        si.setNro_facturas(nro);
        return si;
    }

    private User getCurrentUser() {
        return userRepository.findByUsername(
                SecurityContextHolder.getContext().getAuthentication().getName()
        ).orElseThrow(() -> new IllegalArgumentException("User not found"));
    }
}
