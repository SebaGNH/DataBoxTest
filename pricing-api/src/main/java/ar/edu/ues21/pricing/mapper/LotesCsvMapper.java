package ar.edu.ues21.pricing.mapper;


import ar.edu.ues21.pricing.dto.LotesCsvDto;
import ar.edu.ues21.pricing.model.LoteCsv;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import java.util.List;


@Mapper
public interface LotesCsvMapper {

    LotesCsvMapper LOTES_CSV_MAPPER = Mappers.getMapper(LotesCsvMapper.class);

    List<LotesCsvDto> mapLotesCsvDto(List<LoteCsv> loteCsvEntity);

    LotesCsvDto toDto(LoteCsv loteCsvEntity);


    default Page<LotesCsvDto> toPage(Page<LoteCsv> page) {
        return page.map(this::toDto);
    }
}
