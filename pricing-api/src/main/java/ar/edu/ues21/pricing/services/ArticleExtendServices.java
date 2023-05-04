package ar.edu.ues21.pricing.services;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import ar.edu.ues21.pricing.dto.ArticleExtendDto;

public interface ArticleExtendServices {

   Page<ArticleExtendDto> listarPreciosVigentes(Long csvId, Long cauId, Long idCarrera, String tipoAlumno, String idTipoTicket, Long idTipoModalidad,
         Long periodoAcademico, String tipoArancelId, String turnoCursado, Date fechaMin, Date fechaMax, Pageable pageRequest);
}
