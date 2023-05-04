package ar.edu.ues21.pricing.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ar.edu.ues21.pricing.model.CauResource;

@Repository
public interface CauRepository extends JpaRepository<CauResource, Long> {

   @Query("SELECT DISTINCT new CauResource (c.id, c.descripcion) FROM CauResource as c")
   List<CauResource> findDistinctTopByDescripcion();
}
