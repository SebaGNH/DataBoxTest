package ar.edu.ues21.pricing.controller;

import ar.edu.ues21.pricing.dto.*;
import ar.edu.ues21.pricing.services.LotesCsvService;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
@RequestMapping("/v1/lotes")
@RequiredArgsConstructor
public class LotesCsvController {


    private final LotesCsvService lotesCsvService;

    @Operation(summary = "listar archivos recientes")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "401", description = "Full authentication is required to access this resource"), })
    @GetMapping("/lotesRecientes")
    public List<LotesCsvDto> getLotes() {
        return lotesCsvService.getLotesRecent();
    }


    @Operation(summary = "buscar lotes")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "401", description = "Full authentication is required to access this resource"), })
    @GetMapping("/filterLotes")
    public ResponseEntity<LoteResponseDto> searchLotesCsv(
            @RequestParam(required = false, name = "idLote") Long idLote,
            @RequestParam(required = false, name = "nombreArchivo") String nombreArchivo,
            @RequestParam(required = false, name = "fechaMax") @DateTimeFormat(pattern = "yyyy-MM-dd",iso = DateTimeFormat.ISO.DATE) Date fechaMax,
            @RequestParam(required = false, name = "fechaMin") @DateTimeFormat(pattern = "yyyy-MM-dd",iso = DateTimeFormat.ISO.DATE) Date fechaMin,
            @Parameter(description = "the number of pages that will be retrieved", example = "0") @RequestParam(defaultValue = "0") final Integer pageNo,
            @Parameter(description = "the number of elements that will be retrieved for each page", example = "10") @RequestParam(defaultValue = "20") final Integer pageSize){


        Pageable pageRequest = PageRequest.of(pageNo, pageSize);

        Page<LotesCsvDto> loteCsvDtoList = lotesCsvService.searchLoteCsv(idLote,nombreArchivo,fechaMin,fechaMax,pageRequest);

        LoteResponseDto loteResponseDto = new LoteResponseDto(Long.valueOf(loteCsvDtoList.getTotalElements()),loteCsvDtoList.getContent());

        return ResponseEntity.ok(loteResponseDto);


    }


}
