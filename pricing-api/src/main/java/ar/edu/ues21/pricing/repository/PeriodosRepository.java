package ar.edu.ues21.pricing.repository;

import ar.edu.ues21.pricing.model.PeriodosResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PeriodosRepository extends JpaRepository<PeriodosResource, Long> {

   @Query( value = " SELECT uaa.turno AS id, NVL(utc.observaciones, 'S/D') as descripcion\n"
         + "FROM ue21.ue_turnos_cursado utc  RIGHT JOIN ( SELECT DISTINCT turno FROM PRICING.UE_ARTICULO_AMPLIADO ) uaa\n"
         + "                                           ON utc.id = uaa.turno ORDER BY uaa.turno DESC ", nativeQuery = true)
   List<PeriodosResource> findPeriodos();
}
