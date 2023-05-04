package ar.edu.ues21.pricingbff.controller;

import ar.edu.ues21.pricingbff.client.PricingClient;
import ar.edu.ues21.pricingbff.config.RetrofitExecutor;
import ar.edu.ues21.pricingbff.dto.ResourceDto;
import ar.edu.ues21.pricingbff.dto.ResourceModalidadDto;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ResponseHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/resources")
public class ResourceController {

   private final PricingClient resourceService;

   private final RetrofitExecutor retrofitExecutor;

   @Autowired
   public ResourceController(PricingClient resourceService, RetrofitExecutor retrofitExecutor) {
      this.resourceService = resourceService;
      this.retrofitExecutor = retrofitExecutor;
   }

   @ApiOperation(value = "listar carreras vigentes")
   @ApiResponses(value = { @ApiResponse(code = 200, message = "Success", responseHeaders = {
         @ResponseHeader(name = "cache-control max-age=5", description = "max-age=5", response = String.class),
         @ResponseHeader(name = "etag", response = String.class) }), @ApiResponse(code = 400, message = "Bad Request"),
         @ApiResponse(code = 401, message = "Full authentication is required to access this resource"), })
   @GetMapping("/carreras")
   public List<ResourceDto> listarCarreras() {
      return retrofitExecutor.execute(resourceService.listarCareras());
   }

   @ApiOperation(value = "listar modalidades vigentes")
   @ApiResponses(value = { @ApiResponse(code = 200, message = "Success", responseHeaders = {
         @ResponseHeader(name = "cache-control max-age=5", description = "max-age=5", response = String.class),
         @ResponseHeader(name = "etag", response = String.class) }), @ApiResponse(code = 400, message = "Bad Request"),
         @ApiResponse(code = 401, message = "Full authentication is required to access this resource"), })
   @GetMapping("/modalidades")
   public List<ResourceModalidadDto> listarModalidades() {
      return retrofitExecutor.execute(resourceService.listarModalidades());
   }

   @ApiOperation(value = "listar cau vigentes")
   @ApiResponses(value = { @ApiResponse(code = 200, message = "Success", responseHeaders = {
         @ResponseHeader(name = "cache-control max-age=5", description = "max-age=5", response = String.class),
         @ResponseHeader(name = "etag", response = String.class) }), @ApiResponse(code = 400, message = "Bad Request"),
         @ApiResponse(code = 401, message = "Full authentication is required to access this resource"), })
   @GetMapping("/cau")
   public List<ResourceDto> listarCau() {
      return retrofitExecutor.execute(resourceService.listarCau());
   }

   @ApiOperation(value = "listar ticket vigentes")
   @ApiResponses(value = { @ApiResponse(code = 200, message = "Success", responseHeaders = {
         @ResponseHeader(name = "cache-control max-age=5", description = "max-age=5", response = String.class),
         @ResponseHeader(name = "etag", response = String.class) }), @ApiResponse(code = 400, message = "Bad Request"),
         @ApiResponse(code = 401, message = "Full authentication is required to access this resource"), })
   @GetMapping("/ticket")
   public List<ResourceDto> listarTicket() {
      return retrofitExecutor.execute(resourceService.listarTicket());
   }

   @ApiOperation(value = "listar turnos vigentes")
   @ApiResponses(value = { @ApiResponse(code = 200, message = "Success", responseHeaders = {
         @ResponseHeader(name = "cache-control max-age=5", description = "max-age=5", response = String.class),
         @ResponseHeader(name = "etag", response = String.class) }), @ApiResponse(code = 400, message = "Bad Request"),
         @ApiResponse(code = 401, message = "Full authentication is required to access this resource"), })
   @GetMapping("/turnos")
   public List<ResourceDto> listarTurnos() {
      return retrofitExecutor.execute(resourceService.listarTurnos());
   }

   @ApiOperation(value = "listar rubros vigentes")
   @ApiResponses(value = { @ApiResponse(code = 200, message = "Success", responseHeaders = {
         @ResponseHeader(name = "cache-control max-age=5", description = "max-age=5", response = String.class),
         @ResponseHeader(name = "etag", response = String.class) }), @ApiResponse(code = 400, message = "Bad Request"),
         @ApiResponse(code = 401, message = "Full authentication is required to access this resource"), })
   @GetMapping("/rubros")
   public List<ResourceDto> listarRubros() {
      return retrofitExecutor.execute(resourceService.listarRubros());
   }

   @ApiOperation(value = "listar aranceles vigentes")
   @ApiResponses(value = { @ApiResponse(code = 200, message = "Success", responseHeaders = {
         @ResponseHeader(name = "cache-control max-age=5", description = "max-age=5", response = String.class),
         @ResponseHeader(name = "etag", response = String.class) }), @ApiResponse(code = 400, message = "Bad Request"),
         @ApiResponse(code = 401, message = "Full authentication is required to access this resource"), })
   @GetMapping("/aranceles")
   public List<ResourceDto> listarAranceles() {
      return retrofitExecutor.execute(resourceService.listarAranceles());
   }

   @ApiOperation(value = "listar periodos vigentes")
   @ApiResponses(value = { @ApiResponse(code = 200, message = "Success", responseHeaders = {
         @ResponseHeader(name = "cache-control max-age=5", description = "max-age=5", response = String.class),
         @ResponseHeader(name = "etag", response = String.class) }), @ApiResponse(code = 400, message = "Bad Request"),
         @ApiResponse(code = 401, message = "Full authentication is required to access this resource"), })
   @GetMapping("/periodos")
   public List<ResourceDto> listarPeriodos() {
      return retrofitExecutor.execute(resourceService.listarPeriodos());
   }

   @ApiOperation(value = "listar status")
   @ApiResponses(value = { @ApiResponse(code = 200, message = "Success", responseHeaders = {
           @ResponseHeader(name = "cache-control max-age=5", description = "max-age=5", response = String.class),
           @ResponseHeader(name = "etag", response = String.class) }), @ApiResponse(code = 400, message = "Bad Request"),
           @ApiResponse(code = 401, message = "Full authentication is required to access this resource"), })
   @GetMapping("/status")
   public List<ResourceDto> getStatuses() {
      return retrofitExecutor.execute(resourceService.findAllStatus());
   }

}
