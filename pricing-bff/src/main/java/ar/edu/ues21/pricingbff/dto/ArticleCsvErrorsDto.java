package ar.edu.ues21.pricingbff.dto;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@Builder
@EqualsAndHashCode
public class ArticleCsvErrorsDto implements Serializable {

   private Long numeroLinea;
   private String modalidad;
   private Long modalidadTipo;
   private String CBOI;
   private String tipoTicket;
   private String tipoArancel;
   private String porcentaje;
   private Long precio;

   private List<String> errores;

}
