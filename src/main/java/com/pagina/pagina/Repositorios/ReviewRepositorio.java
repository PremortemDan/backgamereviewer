package com.pagina.pagina.Repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pagina.pagina.Modelos.Reviews;

@Repository
public interface ReviewRepositorio extends JpaRepository<Reviews, Long> {

}
