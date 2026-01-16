
package com.pagina.pagina.Repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.pagina.pagina.Modelos.RedesSociales;

@Repository
public interface RedesSocialesRepositorio extends JpaRepository<RedesSociales, Long> {

}
