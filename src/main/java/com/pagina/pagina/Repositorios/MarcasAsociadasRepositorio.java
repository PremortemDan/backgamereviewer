package com.pagina.pagina.Repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pagina.pagina.Modelos.MarcasAsociadas;

@Repository
public interface MarcasAsociadasRepositorio extends JpaRepository<MarcasAsociadas, Long> {

}
