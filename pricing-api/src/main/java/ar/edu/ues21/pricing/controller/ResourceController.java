package ar.edu.ues21.pricing.controller;

import java.util.List;

import ar.edu.ues21.pricing.dto.ModalidadResourceDto;
import ar.edu.ues21.pricing.model.Status;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.ues21.pricing.dto.Resource;
import ar.edu.ues21.pricing.services.impl.ResourceServiceImp;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/resources")
@RequiredArgsConstructor
public class ResourceController {

   private final ResourceServiceImp resourceService;

   //@PreAuthorize("#oauth2.hasScope('pricingApi:read')")
   @Operation(summary = "listar carreras vigentes")
   @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success"),
         @ApiResponse(responseCode = "400", description = "Bad Request"),
         @ApiResponse(responseCode = "401", description = "Full authentication is required to access this resource"), })
   @GetMapping("/carreras")
   public List<Resource> listarCarreras() {
      return resourceService.listarCarreras();
   }

   //@PreAuthorize("#oauth2.hasScope('pricingApi:read')")
   @Operation(summary = "listar modalidales vigentes")
   @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success"),
         @ApiResponse(responseCode = "400", description = "Bad Request"),
         @ApiResponse(responseCode = "401", description = "Full authentication is required to access this resource"), })
   @GetMapping("/modalidades")
   public List<ModalidadResourceDto> listarModalidades() {
      return resourceService.listarModalidades();
   }

   //@PreAuthorize("#oauth2.hasScope('pricingApi:read')")
   @Operation(summary = "listar cau vigentes")
   @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success"),
         @ApiResponse(responseCode = "400", description = "Bad Request"),
         @ApiResponse(responseCode = "401", description = "Full authentication is required to access this resource"), })
   @GetMapping("/cau")
   public List<Resource> listarCau() {
      return resourceService.listarCau();
   }

   //@PreAuthorize("#oauth2.hasScope('pricingApi:read')")
   @Operation(summary = "listar ticket vigentes")
   @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success"),
         @ApiResponse(responseCode = "400", description = "Bad Request"),
         @ApiResponse(responseCode = "401", description = "Full authentication is required to access this resource"), })
   @GetMapping("/ticket")
   public List<Resource> listarTicket() {
      return resourceService.listarTicket();
   }

   //@PreAuthorize("#oauth2.hasScope('pricingApi:read')")
   @Operation(summary = "listar turnos vigentes")
   @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success"),
         @ApiResponse(responseCode = "400", description = "Bad Request"),
         @ApiResponse(responseCode = "401", description = "Full authentication is required to access this resource"), })
   @GetMapping("/turnos")
   public List<Resource> listarTurnos() {
      return resourceService.listarTurnosCursados();
   }

   //@PreAuthorize("#oauth2.hasScope('pricingApi:read')")
   @Operation(summary = "listar rubros vigentes")
   @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success"),
         @ApiResponse(responseCode = "400", description = "Bad Request"),
         @ApiResponse(responseCode = "401", description = "Full authentication is required to access this resource"), })
   @GetMapping("/rubros")
   public List<Resource> listarRubros() {
      return resourceService.listarRubros();
   }

   //@PreAuthorize("#oauth2.hasScope('pricingApi:read')")
   @Operation(summary = "listar periodos vigentes")
   @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success"),
         @ApiResponse(responseCode = "400", description = "Bad Request"),
         @ApiResponse(responseCode = "401", description = "Full authentication is required to access this resource"), })
   @GetMapping("/periodos")
   public List<Resource> listarPeriodos() {
      return resourceService.listarPeriodos();
   }

   //@PreAuthorize("#oauth2.hasScope('pricingApi:read')")
   @Operation(summary = "listar aranceles vigentes")
   @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success"),
         @ApiResponse(responseCode = "400", description = "Bad Request"),
         @ApiResponse(responseCode = "401", description = "Full authentication is required to access this resource"), })
   @GetMapping("/aranceles")
   public List<Resource> listarAranceles() {
      return resourceService.listarAranceles();
   }

   @Operation(summary = "listar estados")
   @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success"),
           @ApiResponse(responseCode = "400", description = "Bad Request"),
           @ApiResponse(responseCode = "401", description = "Full authentication is required to access this resource"), })
   @GetMapping("/status")
   public List<Resource> findAllStatus() {
      return this.resourceService.findAllStatus();
   }

}