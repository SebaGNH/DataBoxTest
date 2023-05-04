package ar.edu.ues21.pricing.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ar.edu.ues21.pricing.model.CarrerasResource;

@Repository
public interface CarreraRepository extends JpaRepository<CarrerasResource, Long> {

   @Query(value = " SELECT ca.id, ca.DESCRIPCION \n" +
           "                    FROM ue21.ue_Carreras ca join \n" +
           "                        ( SELECT DISTINCT id_carrera FROM PRICING.UE_ARTICULO_AMPLIADO ) aac on aac.ID_CARRERA = ca.id \n" +
           "                    ORDER BY ca.descripcion ",nativeQuery = true)
   List<CarrerasResource> findDistinctTopByDescripcion();
}
