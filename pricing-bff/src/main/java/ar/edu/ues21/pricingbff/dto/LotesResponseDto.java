package ar.edu.ues21.pricingbff.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LotesResponseDto {

    private Long id;
    private String nombreArchivo;
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate fechaCarga;
    private String usuario;

}
