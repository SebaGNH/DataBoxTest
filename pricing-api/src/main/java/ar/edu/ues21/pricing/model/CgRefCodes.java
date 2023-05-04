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
@Table(schema = "UE21", name = "CG_REF_CODES")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CgRefCodes {
   @Id
   @GeneratedValue(strategy = GenerationType.SEQUENCE)
   @Column(name = "RV_LOW_VALUE", nullable = false)
   private String id;

   @Column(name = "RV_MEANING")
   private String descripcion;

   @Column(name = "rv_domain")
   private String rvDomain;
}
