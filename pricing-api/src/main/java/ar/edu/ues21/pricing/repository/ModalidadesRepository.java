package ar.edu.ues21.pricing.repository;

import java.util.List;

import ar.edu.ues21.pricing.model.ModalidadesResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ModalidadesRepository extends JpaRepository<ModalidadesResource, Long> {

   @Query("SELECT DISTINCT new ModalidadesResource (m.id, m.descripcion, m.modalidad) FROM ModalidadesResource as m")
   List<ModalidadesResource> findDistinctTopByDescripcion();
}
