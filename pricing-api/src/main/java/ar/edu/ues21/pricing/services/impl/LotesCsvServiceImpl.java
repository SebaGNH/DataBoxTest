package ar.edu.ues21.pricing.services.impl;

import static ar.edu.ues21.pricing.mapper.LotesCsvMapper.LOTES_CSV_MAPPER;


import ar.edu.ues21.pricing.dto.LotesCsvDto;
import ar.edu.ues21.pricing.model.LoteCsv;
import ar.edu.ues21.pricing.repository.LotesCsvRepository;
import ar.edu.ues21.pricing.services.LotesCsvService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LotesCsvServiceImpl implements LotesCsvService {

    private final LotesCsvRepository lotesCsvRepository;

    @Override
    public List<LotesCsvDto> getLotesRecent() {
        return LOTES_CSV_MAPPER.mapLotesCsvDto(lotesCsvRepository.getLotesRecent());
    }

    @Override
    public Page<LotesCsvDto> searchLoteCsv(Long idLote, String nombreArchivo, Date fechaMin, Date fechaMax, Pageable pageRequest) {
        if (nombreArchivo == null) {
            nombreArchivo = "";
        }
        long loteId = ( idLote== null) ? -1 : idLote;

        Page<LoteCsv> loteCsvEntity = lotesCsvRepository.searchLoteCsv(loteId,nombreArchivo,fechaMin,fechaMax,pageRequest);


        for (LoteCsv lote: loteCsvEntity) {
            lote.setCountPendientes(lotesCsvRepository.countPendientes(lote.getId()));
            lote.setCountTotal(lotesCsvRepository.countTotal(lote.getId()));
        }

        return LOTES_CSV_MAPPER.toPage(loteCsvEntity);

    }


}
