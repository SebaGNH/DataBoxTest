package ar.edu.ues21.pricing.repository;

import ar.edu.ues21.pricing.dto.ArticleExtendDto;
import ar.edu.ues21.pricing.model.ArticleExtend;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;


@Repository
public interface ArticleExtendRepository extends JpaRepository<ArticleExtend, Long> {

    @Query(value = "SELECT \n" +
            "            UAA.id as idArticle, \n" +
            "            UAA.cb_oi as CBOI,\n" +
            "            UAA.id_carrera as idCarrera,\n" +
            "            UAA.descripcion_carrera as descripcionCarrera,\n" +
            "            ROUND(UAA.precio, 2) as precio,\n" +
            "            UAA.id_mon as moneda,\n" +
            "            UAA.articulo as articulo,\n" +
            "            UAA.modalidad as modality,\n" +
            "            UAA.tipo_modalidad as tipoModalidad,\n" +
            "            UAA.tipo_ticket as typeTicket,\n" +
            "            UAA.tipo_arancel_id as tipoArancelId,\n" +
            "            UAA.tipo_arancel as tipoArancel,\n" +
            "            UAA.porcentaje as porcentaje,\n" +
            "            UAA.turno as turno,\n" +
            "            UAA.TURNO_CURSADO AS turnoCursado,\n" +
            "            (SELECT crc.rv_meaning FROM ue21.CG_REF_CODES crc \n" +
            "                WHERE crc.rv_domain like 'TURNOS_CURSADO_CARRERA' AND crc.RV_LOW_VALUE = uaa.TURNO_CURSADO) AS \"turnoCursadoDesc\",\n" +
            "            UAA.producto_facturacion as productoFacturacion, \n" +
            "            UAA.rubro as rubro, \n" +
            "            UAA.cau as cau,\n" +
            "            UAA.periodo_academico as periodoAcademico,\n" +
            "            UAA.cantidad_materias as cantidadMaterias,\n" +
            "            UAA.tipo_alumno as tipoAlumno,\n" +
            "            ue.descripcion as estado,\n" +
            "            UAA.fecha_inicio as fechaInicio,\n" +
            "            UAA.fecha_carga as fechaCarga,\n" +
            "            UAA.usuario as usuario,\n" +
            "            UAA.csv_id as csvId,\n" +
            "            UAA.id_cau as idCau,\n" +
            "            UAA.id_tipo_ticket as idTipoTicket,\n" +
            "            UAA.id_tipo_modalidad as idTipoModalidad\n" +
            "    FROM pricing.UE_ARTICULO_AMPLIADO UAA INNER JOIN pricing.ue_estados ue ON UAA.id_estado = ue.id\n" +
            "    where (ue.id = 4) and\n" +
            "(:csvId =-1 or UAA.csv_id = :csvId) and \n" +
            "(:cauId =-1 or UAA.id_cau = :cauId) and \n" +
            "(:idCarrera =-1 or UAA.id_carrera = :idCarrera)\n" +
            "and (:tipoAlumno is null or UAA.tipo_alumno = :tipoAlumno)\n" +
            "and (:idTipoTicket is null or UAA.id_tipo_ticket = :idTipoTicket)\n" +
            "and (:idTipoModalidad =-1 or UAA.id_tipo_modalidad = :idTipoModalidad)\n"+
            "and (:periodoAcademico = -1 or UAA.TURNO = :periodoAcademico)\n"+
            "and (:tipoArancelId is null or UAA.tipo_arancel_id = :tipoArancelId)\n"+
            "and (:turnoCursado is null or UAA.turno_cursado= :turnoCursado)\n"+
            "and(:fechaMax is null or TRUNC(UAA.fecha_inicio) <= :fechaMax)\n"+
            "and(:fechaMin is null or TRUNC(UAA.fecha_inicio) >= :fechaMin)",
            countQuery = "SELECT COUNT(UAA.ID) " +
                    "FROM pricing.UE_ARTICULO_AMPLIADO UAA INNER JOIN pricing.ue_estados ue ON UAA.id_estado = ue.id " +
                    "    where (ue.id = 4)\n" +
                    "and(:csvId =-1 or UAA.csv_id = :csvId)\n" +
                    "and(:cauId =-1 or UAA.id_cau = :cauId)\n" +
                    "and(:idCarrera =-1 or UAA.id_carrera = :idCarrera) \n"+
                    "and (:tipoAlumno is null or UAA.tipo_alumno = :tipoAlumno) \n" +
                    "and (:idTipoTicket is null or UAA.id_tipo_ticket = :idTipoTicket)\n" +
                    "and (:idTipoModalidad =-1 or UAA.id_tipo_modalidad = :idTipoModalidad)\n" +
                    "and (:periodoAcademico = -1 or UAA.TURNO = :periodoAcademico)\n"+
                    "and (:tipoArancelId is null or UAA.tipo_arancel_id = :tipoArancelId)\n"+
                    "and (:turnoCursado is null or UAA.turno_cursado= :turnoCursado)\n"+
                    "and(:fechaMax is null or TRUNC(UAA.fecha_inicio) <= :fechaMax)\n"+
                    "and(:fechaMin is null or TRUNC(UAA.fecha_inicio) >= :fechaMin)",nativeQuery = true)
    Page<ArticleExtendDto>listarPreciosVigentes(@Param("csvId") Long csvId,
                                                @Param("cauId") Long cauId,
                                                @Param("idCarrera") Long idCarrera,
                                                @Param("tipoAlumno") String tipoAlumno,
                                                @Param("idTipoTicket") String idTipoTicket,
                                                @Param("idTipoModalidad") Long idTipoModalidad,
                                                @Param("periodoAcademico") Long periodoAcademico,
                                                @Param("tipoArancelId")String tipoArancelId,
                                                @Param("turnoCursado")String turnoCursado,
                                                @Param("fechaMin") Date fechaMin,
                                                @Param("fechaMax") Date fechaMax,
                                                Pageable pageRequest);


}
