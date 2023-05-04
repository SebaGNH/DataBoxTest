package ar.edu.ues21.pricing.controller;

import ar.edu.ues21.pricing.dto.ArticleExtendLoadDto;
import ar.edu.ues21.pricing.dto.ArticleLoadResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ar.edu.ues21.pricing.dto.ArticleCsvDto;
import ar.edu.ues21.pricing.services.impl.ArticleCsvServiceImp;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@RestController
@RequestMapping("/v1/csv")
@RequiredArgsConstructor
public class ArticleCsvController {

   private final ArticleCsvServiceImp service;

   @PostMapping
   public ArticleCsvDto create(@RequestBody ArticleCsvDto dto) {
      return service.create(dto);
   }

   @PutMapping
   public ArticleCsvDto update(@RequestBody ArticleCsvDto dto) {
      return service.update(dto);
   }

   @GetMapping("/{csvId}")
   public Page<ArticleCsvDto> getAllByCsv(@PathVariable Long csvId, Pageable page) {
      return service.getAllByCsv(csvId, page);
   }

   @DeleteMapping("/{csvId}")
   public long deleteAllByCsv(@PathVariable Long csvId) {
      return service.deleteAllByCsv(csvId);
   }

   @GetMapping("/article/{id}")
   public ArticleCsvDto getById(@PathVariable Long id) {
      return service.getById(id);
   }

   @Operation(summary = "listar registros vigentes")
   @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success"),
           @ApiResponse(responseCode = "400", description = "Bad Request"),
           @ApiResponse(responseCode = "401", description = "Full authentication is required to access this resource"), })
   @GetMapping("/current-records")
   public ResponseEntity<ArticleLoadResponse> visualizarRegistrosVigentes(
           @RequestParam(required = false, name = "idCsv") Long idCsv,
           @RequestParam(required = false, name = "idCarrera") Long idCarrera,
           @RequestParam(required = false, name = "idCau") Long idCau,
           @RequestParam(required = false, name = "idTipoModalidad") Long idTipoModalidad,
           @RequestParam(required = false, name = "periodoAcademico") Long periodoAcademico,
           @RequestParam(required = false, name = "tipoArancelId") String tipoArancelId,
           @RequestParam(required = false, name = "idTipoTicket") String idTipoTicket,
           @RequestParam(required = false, name = "turnoCursado") String turnoCursado,
           @RequestParam(required = false, name = "tipoAlumno") String tipoAlumno,
           @RequestParam(required = false, name = "fechaMin") @DateTimeFormat(pattern = "yyyy-MM-dd",iso = DateTimeFormat.ISO.DATE) Date fechaMin,
           @RequestParam(required = false, name = "fechaMax") @DateTimeFormat(pattern = "yyyy-MM-dd",iso = DateTimeFormat.ISO.DATE) Date fechaMax,
           @RequestParam(required = false, name = "idEstado") Long idEstado,
           @Parameter(description = "the number of pages that will be retrieved", example = "0") @RequestParam(defaultValue = "0") final Integer pageNo,
           @Parameter(description = "the number of elements that will be retrieved for each page", example = "10") @RequestParam(defaultValue = "20") final Integer pageSize) {

      Pageable pageRequest = PageRequest.of(pageNo, pageSize);

      Page<ArticleExtendLoadDto> articleExtendLoadDtoList = service.visualizarRegistrosVigentes(idCsv, idCau, idCarrera, tipoAlumno, idTipoTicket,
              idTipoModalidad, periodoAcademico, tipoArancelId, turnoCursado, fechaMin, fechaMax, idEstado, pageRequest);
      ArticleLoadResponse response = new ArticleLoadResponse(articleExtendLoadDtoList.getTotalElements(), articleExtendLoadDtoList.getContent());
      return ResponseEntity.ok(response);

   }

}
