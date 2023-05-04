package ar.edu.ues21.pricingbff.controller;

import ar.edu.ues21.pricingbff.client.PricingClient;
import ar.edu.ues21.pricingbff.config.RetrofitExecutor;
import ar.edu.ues21.pricingbff.dto.ArticleCsvDto;
import ar.edu.ues21.pricingbff.dto.ArticleResponseDto;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.security.Principal;
import java.util.Date;

@RestController
@RequestMapping("/v1/csv")
@Api(value = "Article extends")
public class ArticleExtendLoadController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ArticleExtendsController.class);

    @Autowired
    private RetrofitExecutor retrofitExecutor;
    @Autowired
    private PricingClient pricingClient;

    @ApiOperation(value = "visualizar registros vigentes")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success", responseHeaders = {
            @ResponseHeader(name = "cache-control max-age=5", description = "max-age=5", response = String.class),
            @ResponseHeader(name = "etag", response = String.class) }), @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Full authentication is required to access this resource"), })
    @GetMapping("/current-records")
    public ResponseEntity<ArticleResponseDto> visualizarRegistrosVigentes(@RequestParam(required = false, name = "idCsv") Long idCsv,
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
                                                                          @ApiParam(value = "the number of pages that will be retrieved", example = "0") @RequestParam(defaultValue = "0") final Integer pageNo,
                                                                          @ApiParam(value = "the number of elements that will be retrieved for each page", example = "10") @RequestParam(defaultValue = "20") final Integer pageSize) {

        ArticleResponseDto response = retrofitExecutor.execute(
                pricingClient.visualizarRegistrosVigentes(idCsv, idCarrera, idCau, idTipoModalidad, periodoAcademico, tipoArancelId, idTipoTicket, turnoCursado,
                        tipoAlumno,fechaMin != null ? fechaMin.toInstant().toString() : null, fechaMax != null ? fechaMax.toInstant().toString() : null, idEstado, pageNo, pageSize));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @ApiOperation(value = "Upgrade a articleExtendsLoad.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", responseHeaders = {
            @ResponseHeader(name = "cache-control max-age=5", description = "max-age=5", response = String.class),
            @ResponseHeader(name = "etag", response = String.class) }),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Full authentication is required to access this resource"), })
    @PutMapping("")
    public ResponseEntity<ArticleCsvDto> update(@RequestBody ArticleCsvDto articleCsvDto){
        return ResponseEntity.ok(retrofitExecutor.execute(pricingClient.update(articleCsvDto)));
    }
}
