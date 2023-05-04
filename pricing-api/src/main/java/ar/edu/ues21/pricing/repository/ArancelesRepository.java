package ar.edu.ues21.pricing.repository;

import ar.edu.ues21.pricing.model.ArancelesResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArancelesRepository extends JpaRepository<ArancelesResource, Long> {

}
