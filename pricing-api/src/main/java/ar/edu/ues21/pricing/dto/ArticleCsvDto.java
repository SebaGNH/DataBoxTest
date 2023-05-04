package ar.edu.ues21.pricing.dto;

import java.io.Serializable;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import ar.edu.ues21.pricing.enums.EstadoDeCarga;
import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@EqualsAndHashCode
public class ArticleCsvDto implements Serializable {

   private Long id;

   private Long idProgram;

   private String descriptionProgram;

   private String modality;

   private String typeModality;

   private Long idTypeModality;

   private String typeTicket;

   private String typeArancel;

   private Long idTypeArancel;

   private Long turn;

   private String cau;

   private Long idCau;

   private String academicPeriod;

   private Long amountCourse;

   private String typeStudent;

   private String idTypeTicket;

   private LocalDate startDate;

   private String turnCourse;
}
