package ar.edu.ues21.pricing.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "UE_ARTICULO_AMPLIADO", schema = "PRICING")
public class ArticleExtend implements Serializable {

   @Id
   @Column(name = "ID", unique = true, nullable = false)
   private Integer id;

   @Column(name = "ID_CARRERA")
   private Long idProgram;

   @Column(name = "DESCRIPCION_CARRERA")
   private String descriptionProgram;

   @Column(name = "ID_ESTADO")
   private Long idStatus;

   @Column(name = "ESTADO")
   private String status;

   @Column(name = "PRECIO")
   private Long price;

   @Column(name = "MODALIDAD")
   private String modality;

   @Column(name = "TIPO_MODALIDAD")
   private String typeModality;

   @Column(name = "ID_TIPO_MODALIDAD")
   private Long idTypeModality;

   @Column(name = "CB_OI")
   private String cb_oi;

   @Column(name = "TIPO_TICKET")
   private String typeTicket;

   @Column(name = "ID_TIPO_TICKET")
   private String idTypeTicket;

   @Column(name = "RUBRO")
   private String rubro;

   @Column(name = "PRODUCTO_FACTURACION")
   private String productBilling;

   @Column(name = "TURNO")
   private Long turn;

   @Column(name = "TURNO_CURSADO")
   private String turnCourse;

   @Column(name = "CAU")
   private String cau;

   @Column(name = "ID_CAU")
   private Long idCau;

   @Column(name = "PERIODO_ACADEMICO")
   private String academicPeriod;

   @Column(name = "CANTIDAD_MATERIAS")
   private Integer amountCourse;

   @Column(name = "TIPO_ALUMNO")
   private String typeStudent;

   @Column(name = "FECHA_CARGA")
   private Date loadDate;

   @Column(name = "FECHA_INICIO")
   private Date startDate;

   @Column(name = "ARTICULO")
   private String article;

   @Column(name = "ID_MON")
   private String money;

   @Column(name = "PORCENTAJE")
   private String percentage;

   @Column(name = "CSV_ID")
   private Long csvId;

   @Column(name = "TIPO_ARANCEL")
   private String typeArancel;

   @Column(name = "TIPO_ARANCEL_ID")
   private String idTypeArancel;

   @Column(name = "USUARIO")
   private String user;

}
