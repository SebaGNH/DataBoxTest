package ar.edu.ues21.pricing.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(schema = "PRICING", name = "TIPO_PRODUCTO_FACTURABLE")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArancelesResource {

   @Id
   @GeneratedValue(strategy = GenerationType.SEQUENCE)
   @Column(name = "ID", nullable = false)
   private Long id;

   @Column(name = "TIPO_ARANCEL", nullable = false)
   private String idArancel;

   @Column(name = "DESCRIPCION")
   private String descripcion;

}
