package ar.edu.ues21.pricing.repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ar.edu.ues21.pricing.model.LoteCsv;

@Repository
public interface LotesCsvRepository extends JpaRepository<LoteCsv, Long> {

    @Query(value = "select * from (select * from PRICING.ue_lotes_carga order by id desc) where rownum <=10 ", nativeQuery = true)
    List<LoteCsv> getLotesRecent();


    @Query(value = "select * \n" +
            "FROM PRICING.ue_lotes_carga lc \n" +
            "where (:idLote =-1 or lc.id = :idLote) \n" +
            "and(upper(lc.nombre_archivo) LIKE upper(concat('%', concat(:nombreArchivo, '%'))))\n" +
            "and(:fechaMax is null or TRUNC(lc.fecha_carga) <= :fechaMax)\n" +
            "and(:fechaMin is null or TRUNC(lc.fecha_carga)>= :fechaMin)\n" +
            "order by lc.id desc",
            countQuery = "select count(id) \n" +
                    "FROM PRICING.ue_lotes_carga lc \n" +
                    "where (:idLote =-1 or lc.id = :idLote) \n" +
                    "and (upper(lc.nombre_archivo) LIKE upper(concat('%', concat(:nombreArchivo, '%'))))\n" +
                    "and(:fechaMax is null or TRUNC(lc.fecha_carga) <= :fechaMax)\n" +
                    "and(:fechaMin is null or TRUNC(lc.fecha_carga) >= :fechaMin)\n" +
                    "order by lc.id desc",nativeQuery = true)
    Page<LoteCsv>searchLoteCsv(@Param("idLote")Long idLote,
                                          @Param("nombreArchivo")String nombreArchivo,
                                          @Param("fechaMin") Date fechaMin,
                                          @Param("fechaMax") Date fechaMax,
                                          Pageable pageRequest);


    @Query(value = "SELECT count(ID) as countTotal FROM\n" +
            "(SELECT ID FROM PRICING.ue_articulo_ampliado where csv_id = :idCsv\n" +
            "UNION ALL\n" +
            "SELECT ID FROM PRICING.ue_articulo_ampliado_carga where csv_id = :idCsv)",nativeQuery = true)
    Long countTotal(@Param("idCsv") Long idCsv);

    @Query(value = "SELECT count(ID) as countPendiente FROM PRICING.ue_articulo_ampliado_carga am \n" +
            "where am.id_estado = 1 and am.csv_id = :idCsv",nativeQuery = true)
    Long countPendientes(@Param("idCsv") Long idCsv);

    boolean existsByNombreArchivo(String nombreArchivo);

}
