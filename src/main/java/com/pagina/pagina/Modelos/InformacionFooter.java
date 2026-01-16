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
@Table(name = "informacion_footer")
public class InformacionFooter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String texto;

    @Column(nullable = false, length = 200)
    private String url;

    @Column
    private Integer orden; // Para mantener el orden de los enlaces
}
