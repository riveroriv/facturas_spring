package edu.upb.facturas.controller;

import edu.upb.facturas.dao.entity.UserDto;
import edu.upb.facturas.dao.request.ChangePasswordRequest;
import edu.upb.facturas.entity.model.Servicio;
import edu.upb.facturas.service.AuthenticationService;
import edu.upb.facturas.service.ServicioService;
import edu.upb.facturas.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
public class ServicioController {

    @Autowired
    SimpleResponse sr;

    @Autowired
    ServicioService servicioService;


    @GetMapping("/service")
    public ResponseEntity<?> getService(){
        try {
            log .info("User request: get services");
            return sr.get(200, servicioService.findByOwner());
        } catch (NoSuchElementException exception) {
            return sr.get(404);
        } catch (Exception exception) {
            return sr.get(500);
        }
    }
    @PostMapping("/service")
    public ResponseEntity<?> changeService(
            @RequestBody Servicio servicio
            ){
        try {
            log .info("User request: creating servicio");
            return sr.get(200, servicioService.create(servicio));
        } catch (DataIntegrityViolationException exception) {
            return sr.get(409);
        } catch (Exception exception) {
            return sr.get(500);
        }
    }
    @PutMapping("/service")
    public ResponseEntity<?> updateService(
            @RequestBody Servicio servicio
    ){
        try {
            log .info("User request: updating servicio");
            return sr.get(200, servicioService.update(servicio));
        } catch (NoSuchElementException exception) {
            return sr.get(404);
        } catch (Exception exception) {
            return sr.get(500);
        }
    }
    @DeleteMapping("/service/{service}")
    public ResponseEntity<?> deleteUser(
            @PathVariable Long service
    ){
        try {
            log .info("User request: delete servicio");
            return sr.get(200, servicioService.delete(service));
        } catch (NoSuchElementException exception) {
            return sr.get(404);
        } catch (DataIntegrityViolationException exception) {
            return sr.get(403);
        } catch (Exception exception) {
            return sr.get(500);
        }
    }
}
