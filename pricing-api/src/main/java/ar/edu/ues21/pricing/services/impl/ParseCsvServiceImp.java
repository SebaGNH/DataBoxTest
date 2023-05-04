package ar.edu.ues21.pricing.services.impl;

import static java.util.Objects.isNull;

import java.io.InputStreamReader;
import java.io.Reader;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import ar.edu.ues21.pricing.dto.ModalidadResourceDto;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.opencsv.bean.CsvToBeanBuilder;

import ar.edu.ues21.pricing.dto.ArticleCsvErrorsDto;
import ar.edu.ues21.pricing.dto.Resource;
import ar.edu.ues21.pricing.enums.EstadoDeCarga;
import ar.edu.ues21.pricing.exception.ServiceException;
import ar.edu.ues21.pricing.model.ArticleCsv;
import ar.edu.ues21.pricing.model.LoteCsv;
import ar.edu.ues21.pricing.repository.ArticleCsvRepository;
import ar.edu.ues21.pricing.repository.LotesCsvRepository;
import ar.edu.ues21.pricing.services.ParseCsvService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

@RequiredArgsConstructor
@Service
@Log
public class ParseCsvServiceImp implements ParseCsvService {

   private final ResourceServiceImp resourceService;

   private final LotesCsvRepository lotesCsvRepository;

   private final ArticleCsvRepository articleCsvRepository;

   private List<Long> carrerasIds;

   private List<String> modalidadesId;

   private List<String> cauDescriptions;

   private List<String> ticketDescriptions;

   private List<Long> turnosCursadosId;

   private List<String> rubrosId;

   private List<Long> arancelesId;

   public Map<Long, ArticleCsvErrorsDto> parse(MultipartFile file, String user) throws ServiceException {
      getData();
      Map<Long, ArticleCsvErrorsDto> result = new HashMap<Long, ArticleCsvErrorsDto>();
      List<ArticleCsv> csvList;
      if (lotesCsvRepository.existsByNombreArchivo(file.getOriginalFilename())) {
         throw new ServiceException("Nombre de archivo duplicado.", HttpStatus.BAD_REQUEST);
      }
      csvList = getArticleCsvs(file);
      processCsvAndSave(file, user, result, csvList);
      return result;
   }

   private void processCsvAndSave(MultipartFile file, String user, Map<Long, ArticleCsvErrorsDto> result, List<ArticleCsv> csvList) {
      try {
         processCsv(result, csvList);
         if (result.isEmpty()) {
            saveIfCorrect(file, csvList, user);
         }
      } catch (Exception e) {
         throw new ServiceException("Ocurrio un error al guardar el archivo.", HttpStatus.PRECONDITION_FAILED);
      }
   }

   private static List<ArticleCsv> getArticleCsvs(MultipartFile file) {
      List<ArticleCsv> csvList;
      try {
         Reader reader = new InputStreamReader(file.getInputStream());
         csvList = new CsvToBeanBuilder<ArticleCsv>(reader)
               .withIgnoreLeadingWhiteSpace(true)
               .withType(ArticleCsv.class)
               .withSeparator(';')
               .withOrderedResults(true)
               .withIgnoreEmptyLine(true)
               .build()
               .parse();
      } catch (Exception e) {
         throw new ServiceException("Ocurrio un error al parsear el archivo. Verifique el formato.", HttpStatus.PRECONDITION_FAILED);
      }
      return csvList;
   }

   private void processCsv(Map<Long, ArticleCsvErrorsDto> result, List<ArticleCsv> csvList) {
      Long count = 1L;
      HashSet<ArticleCsv> hashSet = new HashSet<>();
      for (ArticleCsv articleCsv : csvList) {
         articleCsv.setNumberLine(count);
         preValidate(result, articleCsv);
         analizeDuplicated(result, count, hashSet, articleCsv);
         count++;
      }
   }

   private static void analizeDuplicated(Map<Long, ArticleCsvErrorsDto> result, Long count, HashSet<ArticleCsv> hashSet, ArticleCsv articleCsv) {
      if (hashSet.contains(articleCsv)) {
         articleCsv.setEstadoDeCarga(EstadoDeCarga.DUPLICADO);
         if (isNull(result.get(count))) {
            LinkedList<String> errors = new LinkedList<>();
            errors.add(String.format("Elemento duplicado."));
            result.put(count, toError(articleCsv, errors, count));
         } else {
            ArticleCsvErrorsDto errors = result.get(count);
            errors.getErrores().add(String.format("Elemento duplicado."));
         }
      } else {
         articleCsv.setEstadoDeCarga(EstadoDeCarga.PREVALIDADO);
         hashSet.add(articleCsv);
      }
   }

   @Transactional(rollbackOn = Exception.class)
   public void saveArticle(List<ArticleCsv> list, LoteCsv lote) {
      list.forEach(a -> {
         a.setCsvId(lote.getId());
         a.setLoadDate(LocalDate.now());
         a.setIdTypeTicket(resourceService.getTicketByDesc(a.getTypeTicket()).getId());
         a.setIdCau(Long.valueOf(resourceService.getCauByDesc(a.getCau()).getId()));
         a.setTypeArancel(resourceService.getArancelById(String.valueOf(a.getIdTypeArancel())).getDescripcion());
         a.setDescriptionProgram(resourceService.getProgramById(a.getIdProgram()).getDescripcion());
         a.setTypeModality(resourceService.getModalityById(a.getIdTypeModality()).getDescripcion());
         a.setArticle(String.format("%s%s", a.getRubro(), a.getProductBilling()));
         a.setUsuario(lote.getUsuario());
      });
      articleCsvRepository.saveAll(list);
   }

   private void preValidate(Map<Long, ArticleCsvErrorsDto> result, ArticleCsv bean) {
      LinkedList<String> errors = new LinkedList<>();
      if (!carrerasIds.contains(bean.getIdProgram())) {
         errors.add(String.format("Linea  no tiene una carrera valido. Valor = %s", bean.getIdProgram()));
      }
      if (!modalidadesId.contains(bean.getIdTypeModality().toString())) {
         errors.add(String.format("No tiene un tipo modalidad valido. Valor = %s", bean.getIdTypeModality()));
      }
      if (!ticketDescriptions.contains(bean.getTypeTicket())) {
         errors.add(String.format("No tiene un ticket valido. Valor = %s", bean.getTypeTicket()));
      }
      if (!turnosCursadosId.contains(bean.getTurnCourse())) {
         errors.add(String.format("No tiene un cantidad de curso valido. Valor = %s", bean.getTurnCourse()));
      }
      if (!rubrosId.contains(bean.getRubro())) {
         errors.add(String.format("No tiene un rubro valido. Valor = %s", bean.getRubro()));
      }
      if (!arancelesId.contains(bean.getIdTypeArancel())) {
         errors.add(String.format("No tiene un aranceles valido. Valor = %s", bean.getIdTypeArancel()));
      }
      if (!cauDescriptions.contains(bean.getCau())) {
         errors.add(String.format("No tiene un cau valido. Valor = %s", bean.getCau()));
      }
      if (isNull(bean.getMoney())) {
         errors.add(String.format("No tiene un Moneda "));
      }
      if (!errors.isEmpty()) {
         result.put(bean.getNumberLine(), toError(bean, errors, bean.getNumberLine()));
      }
   }

   @Async
   @Transactional(rollbackOn = Exception.class)
   public void saveIfCorrect(MultipartFile file, List<ArticleCsv> beans, String user) {
      LoteCsv lote = lotesCsvRepository.save(LoteCsv.builder().nombreArchivo(file.getOriginalFilename()).usuario(user).build());
      saveArticle(beans, lote);
   }

   private void getData() {
      this.carrerasIds = resourceService.listarCarreras().stream().map(c -> Long.parseLong(c.getId())).collect(Collectors.toList());
      this.modalidadesId = resourceService.listarModalidades().stream().map(ModalidadResourceDto::getId).collect(Collectors.toList());
      this.ticketDescriptions = resourceService.listarTicket().stream().map(Resource::getDescripcion).collect(Collectors.toList());
      this.turnosCursadosId = resourceService.listarTurnosCursados().stream().map(c -> Long.parseLong(c.getId())).collect(Collectors.toList());
      this.rubrosId = resourceService.listarRubros().stream().map(Resource::getId).collect(Collectors.toList());
      this.arancelesId = resourceService.listarAranceles().stream().map(c -> Long.parseLong(c.getId())).collect(Collectors.toList());
      this.cauDescriptions = resourceService.listarCau().stream().map(Resource::getDescripcion).collect(Collectors.toList());
   }

   public static ArticleCsvErrorsDto toError(ArticleCsv csv, List<String> errores, Long number) {
      return ArticleCsvErrorsDto
            .builder()
            .errores(errores)
            .CBOI(csv.getCbOi())
            .modalidad(csv.getModality())
            .modalidadTipo(csv.getIdTypeModality())
            .tipoArancel(csv.getTypeArancel())
            .numeroLinea(number)
            .tipoTicket(csv.getTypeTicket())
            .porcentaje(csv.getPercentage())
            .precio(csv.getPrice())
            .build();
   }

}
