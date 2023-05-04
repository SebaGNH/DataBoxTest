package ar.edu.ues21.pricing.model;

import java.time.LocalDate;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(schema = "PRICING", name = "UE_LOTES_CARGA")
public class LoteCsv {

   @Id
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ue_lotes_carga_gen")
   @GenericGenerator(name = "ue_lotes_carga_gen", strategy = "sequence", parameters = {
         @Parameter(name = "sequence", value = "UE_LOTES_CARGA_SEQ")
   })
   @Column(name = "id", nullable = false)
   private Long id;

   @Column(name = "NOMBRE_ARCHIVO")
   private String nombreArchivo;

   @Column(name = "FECHA_CARGA")
   @Builder.Default
   private LocalDate fechaCarga = LocalDate.now();

   @Column(name = "USUARIO")
   private String usuario;

   @Transient
   private Long countPendientes;

   @Transient
   private Long countTotal;

}
