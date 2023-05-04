package ar.edu.ues21.pricingbff.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@EqualsAndHashCode
public class ArticleCsvDto {

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

    private Long price;



}
