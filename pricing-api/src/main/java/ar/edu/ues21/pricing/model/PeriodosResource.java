package ar.edu.ues21.pricing.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PeriodosResource {

   @Id
   @GeneratedValue(strategy = GenerationType.SEQUENCE)
   private Long id;

   private String descripcion;

}
