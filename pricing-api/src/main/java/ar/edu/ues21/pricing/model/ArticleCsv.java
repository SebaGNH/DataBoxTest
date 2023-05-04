package ar.edu.ues21.pricing.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;

import ar.edu.ues21.pricing.enums.EstadoDeCarga;
import ar.edu.ues21.pricing.mapper.DateConvert;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
@Builder
@Table(schema = "PRICING", name = "UE_ARTICULO_AMPLIADO_CARGA")
public class ArticleCsv implements Serializable {

   @Id
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "art_gen")
   @GenericGenerator(name = "art_gen", strategy = "sequence", parameters = {
         @Parameter(name = "sequence", value = "UE_ARTICULO_AMPLIADO_CARGA_SEQ") })
   @Column(name = "ID")
   @EqualsAndHashCode.Exclude
   private Long id;

   @CsvBindByName(column = "CARRERA")
   @Column(name = "ID_CARRERA")
   private Long idProgram;

   @Column(name = "DESCRIPCION_CARRERA")
   private String descriptionProgram;

   @CsvBindByName(column = "MODALIDAD")
   @Column(name = "MODALIDAD")
   private String modality;

   @Column(name = "TIPO_MODALIDAD")
   private String typeModality;

   @Column(name = "ID_TIPO_MODALIDAD")
   @CsvBindByName(column = "TIPO_MODALIDAD")
   private Long idTypeModality;

   @CsvBindByName(column = "CB_OI")
   @Column(name = "CB_OI")
   @Builder.Default
   private String cbOi = "N/A";

   @CsvBindByName(column = "TIPO_DE_TICKET")
   @Column(name = "TIPO_TICKET")
   private String typeTicket;

   @Column(name = "TIPO_ARANCEL")
   private String typeArancel;

   @CsvBindByName(column = "TIPO_ARANCEL")
   @Column(name = "TIPO_ARANCEL_ID")
   private Long idTypeArancel;

   @CsvBindByName(column = "PORCENTAJE")
   @Column(name = "PORCENTAJE")
   private String percentage;

   @CsvBindByName(column = "RUBRO")
   @Column(name = "RUBRO")
   @Builder.Default
   private String rubro = "N/A";

   @CsvBindByName(column = "PRODUCTO_DE_FACTURACION")
   @Column(name = "PRODUCTO_FACTURACION")
   @Builder.Default
   private String productBilling = "N/A";

   @CsvBindByName(column = "TURNO")
   @Column(name = "TURNO")
   private Long turn;

   @CsvBindByName(column = "TURNO_CURSADO_CARRERA")
   @Column(name = "TURNO_CURSADO")
   private Long turnCourse;

   @CsvBindByName(column = "CAU")
   @Column(name = "CAU")
   private String cau;

   @Column(name = "ID_CAU")
   private Long idCau;

   @CsvBindByName(column = "PRECIO")
   @Column(name = "PRECIO")
   @EqualsAndHashCode.Exclude
   private Long price;

   @CsvBindByName(column = "PERIODO_ACADEMICO")
   @Column(name = "PERIODO_ACADEMICO")
   private String academicPeriod;

   @CsvBindByName(column = "MONEDA")
   @Column(name = "ID_MON")
   @EqualsAndHashCode.Exclude
   private String money;

   @CsvBindByName(column = "CANTIDAD_DE_MATERIAS")
   @Column(name = "CANTIDAD_MATERIAS")
   private Long amountCourse;

   @CsvBindByName(column = "TIPO_DE_ALUMNO")
   @Column(name = "TIPO_ALUMNO")
   private String typeStudent;

   @Column(name = "ID_TIPO_TICKET")
   private String idTypeTicket;

   @CsvCustomBindByName(column = "FECHA_DE_INICIO", converter = DateConvert.class)
   @Column(name = "FECHA_INICIO")
   private LocalDate startDate;

   @Transient
   @EqualsAndHashCode.Exclude
   private Long numberLine;

   @Column(name = "CSV_ID")
   @EqualsAndHashCode.Exclude
   private Long csvId;

   @Column(name = "ARTICULO")
   private String article;

   @Column(name = "FECHA_CARGA")
   private LocalDate loadDate;

   @Column(name = "ESTADO_CARGA")
   @Enumerated(EnumType.STRING)
   @EqualsAndHashCode.Exclude
   private EstadoDeCarga estadoDeCarga;

   @Column(name = "OBSERVACION")
   @EqualsAndHashCode.Exclude
   private String observacion;

   @Column(name = "ESTADO")
   @Builder.Default
   @EqualsAndHashCode.Exclude
   private String estado = "BORRADOR";

   @Column(name = "ID_ESTADO")
   @Builder.Default
   @EqualsAndHashCode.Exclude
   private Long idEstado = 1L;

   @Column(name = "USUARIO")
   @EqualsAndHashCode.Exclude
   private String usuario;
}
