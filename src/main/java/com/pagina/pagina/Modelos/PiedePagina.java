package com.pagina.pagina.Modelos;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Setter;
import lombok.Getter;

@Entity
@Setter
@Getter
@Table(name = "pie_pagina")
public class PiedePagina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Descripción
    @Column(nullable = false, length = 100)
    private String tituloDescripcion;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String textoDescripcion;

    // Copyright
    @Column(nullable = false, length = 200)
    private String copyright;

    // Nota: Las redes sociales ya están en la entidad RedesSociales
    // La información de enlaces y marcas asociadas estarán en entidades separadas
}
