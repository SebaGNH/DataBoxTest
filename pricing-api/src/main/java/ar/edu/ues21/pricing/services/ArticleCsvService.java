package ar.edu.ues21.pricing.services;

import ar.edu.ues21.pricing.dto.ArticleExtendLoadDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import ar.edu.ues21.pricing.dto.ArticleCsvDto;

import java.util.Date;

public interface ArticleCsvService {

   ArticleCsvDto create(ArticleCsvDto dto);

   ArticleCsvDto update(ArticleCsvDto dto);

   Page<ArticleCsvDto> getAllByCsv(Long loteId, Pageable page);

   long deleteAllByCsv(Long csvId);

   ArticleCsvDto getById(Long id);

   Page<ArticleExtendLoadDto> visualizarRegistrosVigentes(Long csvId, Long cauId, Long idCarrera, String tipoAlumno, String idTipoTicket, Long idTipoModalidad,
                                                          Long periodoAcademico, String tipoArancelId, String turnoCursado, Date fechaMin, Date fechaMax, Long idEstado , Pageable pageRequest);
}
