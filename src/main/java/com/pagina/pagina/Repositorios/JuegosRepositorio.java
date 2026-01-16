package com.pagina.pagina.Repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pagina.pagina.Modelos.Juegos;

@Repository
public interface JuegosRepositorio extends JpaRepository<Juegos, Long> {

}
