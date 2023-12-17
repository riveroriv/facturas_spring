package edu.upb.facturas.controller;

import edu.upb.facturas.dao.response.SimpleMessageResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SimpleResponse {

    @Value("${api.response.ok}")
    private String okMessage;

    @Value("${api.response.created}")
    private String createdMessage;

    @Value("${api.response.bad-request}")
    private String badRequestMessage;

    @Value("${api.response.not-autenticated}")
    private String notAuthenticatedMessage;

    @Value("${api.response.not-authorized}")
    private String notAuthorizedMessage;

    @Value("${api.response.not-found}")
    private String notFoundMessage;

    @Value("${api.response.already-exists}")
    private String alreadyExistsMessage;

    @Value("${api.response.internal-error}")
    private String internalErrorMessage;
    private ResponseEntity<?> simple(String message, HttpStatus status){
        log.info("response: "+message);
        return ResponseEntity
                .status(status)
                .body( new SimpleMessageResponse(
                        message,
                        status.value()
                ));
    }
    private ResponseEntity<?> response(Object object, String message, HttpStatus status){
        log.info(message);
        return ResponseEntity
                .status(status)
                .body(object);
    }
    public ResponseEntity<?> get(int code){
        return simple(message(code), status(code));
    }
    public ResponseEntity<?> get(String message, int code){
        return simple(message, status(code));
    }
    public ResponseEntity<?> get(int code, Object object){
        return response(object, message(code), status(code));
    }
    public ResponseEntity<?> get(int code, Object object, String log){
        return response(object, log, status(code));
    }

    private HttpStatus status(int status) {
        return switch (status) {
            case 200 -> HttpStatus.OK;
            case 201 -> HttpStatus.CREATED;
            case 400 -> HttpStatus.BAD_REQUEST;
            case 401 -> HttpStatus.UNAUTHORIZED;
            case 403 -> HttpStatus.FORBIDDEN;
            case 404 -> HttpStatus.NOT_FOUND;
            case 409 -> HttpStatus.CONFLICT;
            case 500 -> HttpStatus.INTERNAL_SERVER_ERROR;
            default -> HttpStatus.BAD_REQUEST;
        };
    }
    private  String message(int status) {
        return switch (status) {
            case 200 -> okMessage;
            case 201 -> createdMessage;
            case 400 -> badRequestMessage;
            case 401 -> notAuthenticatedMessage;
            case 403 -> notAuthorizedMessage;
            case 404 -> notFoundMessage;
            case 409 -> alreadyExistsMessage;
            case 500 -> internalErrorMessage;
            default -> "Mensaje predeterminado";
        };
    }

}
