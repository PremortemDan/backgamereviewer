package com.pagina.pagina.Repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pagina.pagina.Modelos.Logo;

@Repository
public interface LogoRepositorio extends JpaRepository<Logo, Long> {

}
