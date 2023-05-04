package ar.edu.ues21.pricing.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(schema = "UE21", name = "UE_MODALIDADES")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ModalidadesResource {
   @Id
   @GeneratedValue(strategy = GenerationType.SEQUENCE)
   @Column(name = "id", nullable = false)
   private Long id;

   @Column(name = "descripcion")
   private String descripcion;

   @Column(name = "modalidad")
   private String modalidad;
}
