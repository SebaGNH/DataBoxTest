package ar.edu.ues21.pricing.services.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ar.edu.ues21.pricing.dto.ArticleExtendDto;
import ar.edu.ues21.pricing.mapper.ArticleExtendsTransformer;
import ar.edu.ues21.pricing.repository.ArticleExtendRepository;
import ar.edu.ues21.pricing.services.ArticleExtendServices;

@Service
public class ArticleExtendServiceImp implements ArticleExtendServices {


   public ArticleExtendRepository articleExtendRepository;

   public ArticleExtendsTransformer articleExtendsTransformer;

   @Override
   public Page<ArticleExtendDto> listarPreciosVigentes(Long csvId, Long cauId, Long idCarrera, String tipoAlumno, String idTipoTicket,
         Long idTipoModalidad, Long periodoAcademico, String tipoArancelId, String turnoCursado, Date fechaMin, Date fechaMax,
         Pageable pageRequest) {

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

      Page<ArticleExtendDto> articleExtendDtoPage = articleExtendRepository.listarPreciosVigentes(idCsv, idCau, carreraId, tipoAlumno, idTipoTicket,
            modalidad, idPeriodoAcademico, tipoArancelId, turnoCursado, fechaMin, fechaMax, pageRequest);

      return articleExtendDtoPage;

   }

   @Autowired
   @Qualifier("articleExtendRepository")
   public void setArticleExtendRepository(ArticleExtendRepository articleExtendRepository) {
      this.articleExtendRepository = articleExtendRepository;
   }

   @Autowired
   @Qualifier("articleExtendsTransformer")
   public void setArticleExtendsTransformer(ArticleExtendsTransformer articleExtendsTransformer) {
      this.articleExtendsTransformer = articleExtendsTransformer;
   }

}
