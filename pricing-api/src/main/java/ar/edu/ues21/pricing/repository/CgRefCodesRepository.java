package ar.edu.ues21.pricing.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ar.edu.ues21.pricing.model.CgRefCodes;

@Repository
public interface CgRefCodesRepository extends JpaRepository<CgRefCodes, Long> {

   @Query("Select distinct new CgRefCodes (ticket.id, ticket.descripcion, ticket.rvDomain) from CgRefCodes as ticket where ticket.rvDomain like "
         + "'TIPO_TICKET'")
   List<CgRefCodes> findDistinctTicket();

   @Query("Select distinct new CgRefCodes (turnos.id, turnos.descripcion, turnos.rvDomain) from CgRefCodes as turnos where turnos.rvDomain like "
         + "'TURNOS_CURSADO_CARRERA'")
   List<CgRefCodes> findDistinctTurnosCursados();

   @Query("Select distinct new CgRefCodes (rubros.id, rubros.descripcion, rubros.rvDomain) from CgRefCodes as rubros where rubros.rvDomain like "
         + "'RUBROS_EPAGOS'")
   List<CgRefCodes> findDistinctRubros();

}
