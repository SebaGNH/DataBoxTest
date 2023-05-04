package ar.edu.ues21.pricing.controller;

import java.util.Date;
import java.util.LinkedList;
import java.util.Map;

import ar.edu.ues21.pricing.exception.ErrorMessage;
import ar.edu.ues21.pricing.exception.ServiceException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.PathVariable;

import ar.edu.ues21.pricing.dto.ArticleCsvErrorsDto;
import ar.edu.ues21.pricing.dto.ArticleExtendDto;
import ar.edu.ues21.pricing.dto.ArticleResponseDto;
import ar.edu.ues21.pricing.services.ArticleExtendServices;
import ar.edu.ues21.pricing.services.impl.ParseCsvServiceImp;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/articles")
@RequiredArgsConstructor
public class ArticleExtendController {

   private final ArticleExtendServices articleExtendServices;

   private final  ParseCsvServiceImp parseCsvServiceImp;

   //@PreAuthorize("#oauth2.hasScope('pricingApi:read')")
   @Operation(summary = "listar precios vigentes")
   @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success"),
         @ApiResponse(responseCode = "400", description = "Bad Request"),
         @ApiResponse(responseCode = "401", description = "Full authentication is required to access this resource"), })
   @GetMapping("/current-prices")
   public ResponseEntity<ArticleResponseDto> listaDePreciosVigentes(
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
         @RequestParam(required = false, name = "fechaMax")@DateTimeFormat(pattern = "yyyy-MM-dd",iso = DateTimeFormat.ISO.DATE) Date fechaMax,
         @Parameter(description = "the number of pages that will be retrieved", example = "0") @RequestParam(defaultValue = "0") final Integer pageNo,
         @Parameter(description = "the number of elements that will be retrieved for each page", example = "10") @RequestParam(defaultValue = "20") final Integer pageSize) {

      Pageable pageRequest = PageRequest.of(pageNo, pageSize);

      Page<ArticleExtendDto> articleExtendDtoList = articleExtendServices.listarPreciosVigentes(idCsv, idCau, idCarrera, tipoAlumno, idTipoTicket,
            idTipoModalidad, periodoAcademico, tipoArancelId, turnoCursado, fechaMin, fechaMax, pageRequest);
      ArticleResponseDto response = new ArticleResponseDto(Long.valueOf(articleExtendDtoList.getTotalElements()), articleExtendDtoList.getContent());
      return ResponseEntity.ok(response);

   }

   @Operation(summary = "listar aranceles vigentes")
   @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success"),
         @ApiResponse(responseCode = "400", description = "Bad Request"),
         @ApiResponse(responseCode = "401", description = "Full authentication is required to access this resource"), })
   @PostMapping(value = "/file/{user}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
   public ResponseEntity<Map<Long, ArticleCsvErrorsDto>> parseFile(@RequestParam MultipartFile file, @PathVariable("user") String user) {
      Map<Long, ArticleCsvErrorsDto> response = null;
      try {
         response = parseCsvServiceImp.parse(file, user);
      } catch (ServiceException ex) {
         return new ResponseEntity(new ErrorMessage(ex.getCode().value(), ex.getMessage()), ex.getCode());
      }
      return ResponseEntity.ok(response);
   }
}
