package ar.edu.ues21.pricing.services.impl;

import static ar.edu.ues21.pricing.mapper.ArticleCsvMapper.ART_CSV;

import ar.edu.ues21.pricing.dto.ArticleExtendLoadDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import ar.edu.ues21.pricing.dto.ArticleCsvDto;
import ar.edu.ues21.pricing.exception.ServiceException;
import ar.edu.ues21.pricing.model.ArticleCsv;
import ar.edu.ues21.pricing.repository.ArticleCsvRepository;
import ar.edu.ues21.pricing.services.ArticleCsvService;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class ArticleCsvServiceImp implements ArticleCsvService {

   private final ArticleCsvRepository csvRepository;

   @Override
   public ArticleCsvDto create(ArticleCsvDto dto) {
      ArticleCsv entity = ART_CSV.fromDto(dto);
      return ART_CSV.toDto(csvRepository.save(entity));
   }

   @Override
   public ArticleCsvDto update(ArticleCsvDto dto) {
      ArticleCsv entity = csvRepository
            .findById(dto.getId())
            .orElseThrow(() -> new ServiceException("No se encontro articulo.", HttpStatus.NOT_FOUND));
      entity = ART_CSV.merge(entity, dto);
      return ART_CSV.toDto(csvRepository.save(entity));
   }

   @Override
   public Page<ArticleCsvDto> getAllByCsv(Long loteId, Pageable page) {
      return ART_CSV.toPage(csvRepository.findAllByCsvIdIs(loteId, page));
   }

   @Override
   public long deleteAllByCsv(Long csvId) {
      return csvRepository.deleteByCsvId(csvId);
   }

   @Override
   public ArticleCsvDto getById(Long id) {
      return ART_CSV.toDto(csvRepository.findById(id).orElseThrow(() -> new ServiceException("No se encontro articulo.", HttpStatus.NOT_FOUND)));
   }

   @Override
   public Page<ArticleExtendLoadDto> visualizarRegistrosVigentes(Long csvId, Long cauId, Long idCarrera, String tipoAlumno, String idTipoTicket, Long idTipoModalidad, Long periodoAcademico, String tipoArancelId, String turnoCursado, Date fechaMin, Date fechaMax, Long idEstado, Pageable pageRequest) {

         if (idTipoTicket == null) {
            idTipoTicket = "";
         }
         if (tipoAlumno == null) {
            tipoAlumno = "";
         }
         if (tipoArancelId == null) {
            tipoArancelId = "";
         }
         if (turnoCursado == null) {
            turnoCursado = "";
         }

         long idCsv = (csvId == null) ? -1 : csvId;
         long carreraId = (idCarrera == null) ? -1 : idCarrera;
         long idCau = (cauId == null) ? -1 : cauId;
         long idPeriodoAcademico = (periodoAcademico == null) ? -1 : periodoAcademico;
         long modalidad = (idTipoModalidad == null) ? -1 : idTipoModalidad;
         long estadoId = (idEstado == null) ? -1 : idEstado;

         Page<ArticleExtendLoadDto> articleExtendLoadDtoPage = csvRepository.visualizarRegistrosVigentes(idCsv, idCau, carreraId, tipoAlumno, idTipoTicket,
                 modalidad, idPeriodoAcademico, tipoArancelId, turnoCursado, fechaMin, fechaMax, estadoId, pageRequest);

         return articleExtendLoadDtoPage;
   }

}
