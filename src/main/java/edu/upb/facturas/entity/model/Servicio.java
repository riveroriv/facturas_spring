package edu.upb.facturas.entity.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "SERVICIOS")
public class Servicio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "OWNER")
    private Long owner;

    @Column(name = "NAME")
    private String name;

    @Column(name = "CLIENT_NUMBER")
    private String clientNumber;

    @Column(name = "SERVICE")
    private String service;
}