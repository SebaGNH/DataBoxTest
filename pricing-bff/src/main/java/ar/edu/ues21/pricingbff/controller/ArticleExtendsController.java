package ar.edu.ues21.pricingbff.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ar.edu.ues21.pricingbff.exception.ProxyException;

import ar.edu.ues21.pricingbff.exception.ErrorMessage;
import ar.edu.ues21.pricingbff.exception.ServiceException;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ResponseHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ar.edu.ues21.pricingbff.dto.ArticleCsvErrorsDto;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import ar.edu.ues21.pricingbff.client.PricingClient;
import ar.edu.ues21.pricingbff.config.RetrofitExecutor;
import ar.edu.ues21.pricingbff.dto.ArticleResponseDto;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/v1/articles")
@Api(value = "Article extends")
public class ArticleExtendsController {

   private static final Logger LOGGER = LoggerFactory.getLogger(ArticleExtendsController.class);

   @Autowired
   private RetrofitExecutor retrofitExecutor;
   @Autowired
   private PricingClient pricingClient;
   private static final String DIR_UPLOADS = "/tmp/uploads";
   private final Path root = Paths.get(DIR_UPLOADS);

   @ApiOperation(value = "listar precios vigentes")
   @ApiResponses(value = { @ApiResponse(code = 200, message = "Success", responseHeaders = {
         @ResponseHeader(name = "cache-control max-age=5", description = "max-age=5", response = String.class),
         @ResponseHeader(name = "etag", response = String.class) }), @ApiResponse(code = 400, message = "Bad Request"),
         @ApiResponse(code = 401, message = "Full authentication is required to access this resource"), })
   @GetMapping("/current-prices")
   public ResponseEntity<ArticleResponseDto> listaDePreciosVigentes(@RequestParam(required = false, name = "idCsv") Long idCsv,
                                                                    @RequestParam(required = false, name = "idCarrera") Long idCarrera,
                                                                    @RequestParam(required = false, name = "idCau") Long idCau,
                                                                    @RequestParam(required = false, name = "idTipoModalidad") Long idTipoModalidad,
                                                                    @RequestParam(required = false, name = "periodoAcademico") Long periodoAcademico,
                                                                    @RequestParam(required = false, name = "tipoArancelId") String tipoArancelId,
                                                                    @RequestParam(required = false, name = "idTipoTicket") String idTipoTicket,
                                                                    @RequestParam(required = false, name = "turnoCursado") String turnoCursado,
                                                                    @RequestParam(required = false, name = "tipoAlumno") String tipoAlumno,
                                                                    @RequestParam(required = false, name = "fechaMin") @DateTimeFormat(pattern = "yyyy-MM-dd",iso = DateTimeFormat.ISO.DATE)Date fechaMin,
                                                                    @RequestParam(required = false, name = "fechaMax") @DateTimeFormat(pattern = "yyyy-MM-dd",iso = DateTimeFormat.ISO.DATE) Date fechaMax,
         @ApiParam(value = "the number of pages that will be retrieved", example = "0") @RequestParam(defaultValue = "0") final Integer pageNo,
         @ApiParam(value = "the number of elements that will be retrieved for each page", example = "10") @RequestParam(defaultValue = "20") final Integer pageSize) {

      ArticleResponseDto response = retrofitExecutor.execute(
            pricingClient.listarPreciosVigentes(idCsv, idCarrera, idCau, idTipoModalidad, periodoAcademico, tipoArancelId, idTipoTicket, turnoCursado,
                  tipoAlumno, fechaMin != null ? fechaMin.toInstant().toString() : null, fechaMax != null ? fechaMax.toInstant().toString() : null, pageNo, pageSize));
      return ResponseEntity.status(HttpStatus.OK).body(response);
   }

   @ApiOperation(value = "listar carreras vigentes")
   @ApiResponses(value = { @ApiResponse(code = 200, message = "Success", responseHeaders = {
         @ResponseHeader(name = "cache-control max-age=5", description = "max-age=5", response = String.class),
         @ResponseHeader(name = "etag", response = String.class) }), @ApiResponse(code = 400, message = "Bad Request"),
         @ApiResponse(code = 401, message = "Full authentication is required to access this resource"), })
   @PostMapping(value = "/file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
   public ResponseEntity<HashMap<String, ArticleCsvErrorsDto>> sendFile(@RequestParam(name = "file") MultipartFile file,@ApiIgnore final Principal principal) throws IOException {
      if (!Files.exists(root)) {
         Files.createDirectory(root);
      }
      Files.copy(file.getInputStream(), this.root.resolve(file.getOriginalFilename()));
      LOGGER.info("Copy file in :"+ this.root.resolve(file.getOriginalFilename()).toString());
      File fileUpload= new File(DIR_UPLOADS + "/" + file.getOriginalFilename());
      MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", fileUpload.getName(), RequestBody.create(okhttp3.MediaType.parse("text/csv"), fileUpload));
      String user = principal != null ? principal.getName() : "";
      try {
         var response = retrofitExecutor.execute(pricingClient.sendFile(filePart, user));
         fileUpload.delete();
         return ResponseEntity.ok(response);
      } catch (ProxyException ex) {
         fileUpload.delete();
         ObjectMapper objectMapper = new ObjectMapper();
         ErrorMessage error = objectMapper.readValue(ex.getBody(), ErrorMessage.class);
         return new ResponseEntity(error, ex.getCode());
      }
   }
}
