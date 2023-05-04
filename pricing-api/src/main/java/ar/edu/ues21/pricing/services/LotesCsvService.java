package ar.edu.ues21.pricing.services;


import ar.edu.ues21.pricing.dto.LotesCsvDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface LotesCsvService {


    List<LotesCsvDto> getLotesRecent();

    Page<LotesCsvDto> searchLoteCsv(Long idLote, String nombreArchivo, Date fechaMin, Date fechaMax, Pageable page);
}
