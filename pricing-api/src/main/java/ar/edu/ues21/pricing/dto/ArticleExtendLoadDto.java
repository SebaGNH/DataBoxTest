package ar.edu.ues21.pricing.dto;

import java.util.Date;

public interface ArticleExtendLoadDto {

    Integer getIdArticle();
    String getModality();
    String getTipoModalidad();
    Long getIdTipoModalidad();
    String getTypeTicket();
    String getIdTipoTicket();
    Long getIdCarrera();
    String getDescripcionCarrera();
    Integer getPrecio();
    String getCBOI();
    String getRubro();
    Long getTurno();
    String getTurnoCursado();
    String getTurnoCursadoDesc();
    String getCau();
    Long getIdCau();
    String getPeriodoAcademico();
    Date getFechaInicio();
    Date getFechaCarga();
    String getArticulo();
    Long getCsvId();
    String getTipoArancelId();
    String getTipoArancel();
    String getPorcentaje();
    String getUsuario();
    String getMoneda();
    Integer getCantidadMaterias();
    String getTipoALumno();
    String getEstado();
    Long getIdEstado();
    String getObesrvaciones();
    String getEstadoCarga();
}
