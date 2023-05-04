package ar.edu.ues21.pricing.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDate;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LotesCsvDto {

    private Long id;
    private String nombreArchivo;
    private LocalDate fechaCarga;
    private String usuario;
    private Long countTotal;
    private Long countPendientes;

}
