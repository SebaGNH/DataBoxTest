package ar.edu.ues21.pricingbff.controller;


import ar.edu.ues21.pricingbff.client.PricingClient;
import ar.edu.ues21.pricingbff.config.RetrofitExecutor;
import ar.edu.ues21.pricingbff.dto.ArticleResponseDto;
import ar.edu.ues21.pricingbff.dto.LotesResponseDto;
import ar.edu.ues21.pricingbff.dto.ResponseLote;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("v1/lotes")
public class LotesCsvController {

    private final RetrofitExecutor retrofitExecutor;
    private final PricingClient pricingClient;

    @Autowired
    public LotesCsvController(RetrofitExecutor retrofitExecutor, PricingClient pricingClient) {
        this.retrofitExecutor = retrofitExecutor;
        this.pricingClient = pricingClient;
    }

    @ApiOperation(value = "listar archivos recientes")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success", responseHeaders = {
            @ResponseHeader(name = "cache-control max-age=5", description = "max-age=5", response = String.class),
            @ResponseHeader(name = "etag", response = String.class) }),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Full authentication is required to access this resource"), })
    @GetMapping("/lotesRecientes")
    public ResponseEntity<List<LotesResponseDto>> getLotes() {
        List<LotesResponseDto> response = retrofitExecutor.execute(
                pricingClient.getLotesRecent());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @ApiOperation(value = "buscar lotes")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success", responseHeaders = {
            @ResponseHeader(name = "cache-control max-age=5", description = "max-age=5", response = String.class),
            @ResponseHeader(name = "etag", response = String.class) }),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Full authentication is required to access this resource"), })
    @GetMapping("/filterLotes")
    public ResponseEntity<ResponseLote> searchLotesCsv(
            @RequestParam(required = false, name = "idLote") Long idLote,
            @RequestParam(required = false, name = "nombreArchivo") String nombreArchivo,
            @RequestParam(required = false, name = "fechaMin")@DateTimeFormat(pattern = "yyyy-MM-dd",iso = DateTimeFormat.ISO.DATE) Date fechaMin,
            @RequestParam(required = false, name = "fechaMax")@DateTimeFormat(pattern = "yyyy-MM-dd",iso = DateTimeFormat.ISO.DATE) Date fechaMax,
            @ApiParam(value = "the number of pages that will be retrieved", example = "0") @RequestParam(defaultValue = "0") final Integer pageNo,
            @ApiParam(value = "the number of elements that will be retrieved for each page", example = "10") @RequestParam(defaultValue = "20") final Integer pageSize){


        ResponseLote lotesCsv = retrofitExecutor.execute(pricingClient.searchLotesCsv(idLote,nombreArchivo,fechaMin != null ? fechaMin.toInstant().toString() : null, fechaMax != null ? fechaMax.toInstant().toString() : null,pageNo,pageSize));

        return ResponseEntity.status(HttpStatus.OK).body(lotesCsv);


    }



}
