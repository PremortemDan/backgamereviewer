package com.pagina.pagina.Repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pagina.pagina.Modelos.InformacionFooter;

@Repository
public interface InformacionFooterRepositorio extends JpaRepository<InformacionFooter, Long> {

}
