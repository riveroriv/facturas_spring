package edu.upb.facturas.controller;

import edu.upb.facturas.service.InvoiceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@Slf4j
@RestController
@RequestMapping("${api.app.path}")
@CrossOrigin(
        origins = "*",
        methods = {
                RequestMethod.GET,
                RequestMethod.POST,
                RequestMethod.DELETE,
                RequestMethod.PUT
        })
public class InvoiceController {

    @Autowired
    SimpleResponse sr;

    @Autowired
    InvoiceService invoiceService;


    @Value("${servicio.cre.path}")
    String crePath;
    @Value("${servicio.tigo.path}")
    String tigoPath;


    @GetMapping("/invoice/{id}")
    public ResponseEntity<?> getFactura(
            @PathVariable Long id
    ){
        try {
            log .info("User request: get invoices");
            return sr.get(200, invoiceService.getByService(id));
        } catch (NoSuchElementException exception) {
            return sr.get(404);
        } catch (IllegalCallerException exception) {
            return sr.get(400);
        } catch (Exception exception) {
            return sr.get(500);
        }
    }
    @GetMapping("/invoice")
    public ResponseEntity<?> getFacturas( ){
        try {
            log .info("User request: get all invoices");
            return sr.get(200, invoiceService.getAll());
        } catch (NoSuchElementException exception) {
            return sr.get(404);
        } catch (IllegalCallerException exception) {
            return sr.get(400);
        } catch (Exception exception) {
            return sr.get(500);
        }
    }

    @GetMapping("/invoice/sum")
    public ResponseEntity<?> getSuma( ){
        try {
            log .info("User request: get all invoices");
            return sr.get(200, invoiceService.getSum());
        } catch (NoSuchElementException exception) {
            return sr.get(404);
        } catch (IllegalCallerException exception) {
            return sr.get(400);
        } catch (Exception exception) {
            return sr.get(500);
        }
    }

}
