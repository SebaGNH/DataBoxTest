package ar.edu.ues21.pricingbff.client;

import ar.edu.ues21.pricingbff.dto.*;
import okhttp3.MultipartBody;
import org.springframework.web.bind.annotation.RequestBody;
import retrofit2.Call;
import retrofit2.http.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public interface PricingClient {

    @GET("v1/articles/current-prices")
    @Headers({ "Accept: application/json", "Content-Type: application/json" })
    Call<ArticleResponseDto> listarPreciosVigentes(@Query("idCsv") Long idCsv, @Query("idCarrera") Long idCarrera,
                                                   @Query("idCau") Long idCau, @Query("idTipoModalidad") Long idTipoModalidad,
                                                   @Query("periodoAcademico")Long periodoAcademico,
                                                   @Query("tipoArancelId")String tipoArancelId,
                                                   @Query("idTipoTicket")String idTipoTicket,
                                                   @Query("turnoCursado")String turnoCursado,
                                                   @Query("tipoAlumno")String tipoAlumno,
                                                   @Query("fechaMin")String fechaMin,
                                                   @Query("fechaMax")String fechaMax,
                                                   @Query("pageNo")Integer pageNo,
                                                   @Query("pageSize")Integer pageSize);

   @GET("v1/resources/carreras")
   @Headers({ "Accept: application/json", "Content-Type: application/json" })
   Call<List<ResourceDto>> listarCareras();

   @GET("v1/resources/modalidades")
   @Headers({ "Accept: application/json", "Content-Type: application/json" })
   Call<List<ResourceModalidadDto>> listarModalidades();

   @GET("v1/resources/cau")
   @Headers({ "Accept: application/json", "Content-Type: application/json" })
   Call<List<ResourceDto>> listarCau();

   @GET("v1/resources/ticket")
   @Headers({ "Accept: application/json", "Content-Type: application/json" })
   Call<List<ResourceDto>> listarTicket();

   @GET("v1/resources/turnos")
   @Headers({ "Accept: application/json", "Content-Type: application/json" })
   Call<List<ResourceDto>> listarTurnos();

   @GET("v1/resources/rubros")
   @Headers({ "Accept: application/json", "Content-Type: application/json" })
   Call<List<ResourceDto>> listarRubros();

   @GET("v1/resources/aranceles")
   @Headers({ "Accept: application/json", "Content-Type: application/json" })
   Call<List<ResourceDto>> listarAranceles();

   @GET("v1/resources/periodos")
   @Headers({ "Accept: application/json", "Content-Type: application/json" })
   Call<List<ResourceDto>> listarPeriodos();

   @POST("v1/articles/file/{user}")
   @Multipart
   Call<HashMap<String, ArticleCsvErrorsDto>> sendFile(@Part MultipartBody.Part file, @Path("user") final String user);

    @GET("v1/lotes/lotesRecientes")
    @Headers({ "Accept: application/json", "Content-Type: application/json" })
    Call<List<LotesResponseDto>>getLotesRecent();

    @GET("/v1/csv/current-records")
    @Headers({ "Accept: application/json", "Content-Type: application/json" })
    Call<ArticleResponseDto> visualizarRegistrosVigentes(@Query("idCsv") Long idCsv, @Query("idCarrera") Long idCarrera,
                                                   @Query("idCau") Long idCau, @Query("idTipoModalidad") Long idTipoModalidad,
                                                   @Query("periodoAcademico")Long periodoAcademico,
                                                   @Query("tipoArancelId")String tipoArancelId,
                                                   @Query("idTipoTicket")String idTipoTicket,
                                                   @Query("turnoCursado")String turnoCursado,
                                                   @Query("tipoAlumno")String tipoAlumno,
                                                   @Query("fechaMin")String fechaMin,
                                                   @Query("fechaMax")String fechaMax,
                                                   @Query("idEstado")Long idEstado,
                                                   @Query("pageNo")Integer pageNo,
                                                   @Query("pageSize")Integer pageSize);

    @GET("v1/lotes/filterLotes")
    @Headers({ "Accept: application/json", "Content-Type: application/json" })
    Call<ResponseLote> searchLotesCsv(@Query("idLote") Long idLote,
                                      @Query("nombreArchivo")String nombreArchivo,
                                      @Query("fechaMin") String fechaMin,
                                      @Query("fechaMax")String fechaMax,
                                      @Query("pageNo")Integer pageNo,
                                      @Query("pageSize")Integer pageSize);

    @GET("v1/resources/status")
    @Headers({ "Accept: application/json", "Content-Type: application/json" })
    Call<List<ResourceDto>> findAllStatus();


    @PUT("v1/csv")
    @Headers({ "Accept: application/json", "Content-Type: application/json" })
    Call<ArticleCsvDto> update(@Body ArticleCsvDto dto);

}
