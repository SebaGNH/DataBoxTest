package ar.edu.ues21.pricing.services.impl;

import ar.edu.ues21.pricing.dto.LotesCsvDto;
import ar.edu.ues21.pricing.mapper.LotesCsvMapper;
import ar.edu.ues21.pricing.model.LoteCsv;
import ar.edu.ues21.pricing.repository.LotesCsvRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class LotesCsvServiceImplTest {

    @Mock
    LotesCsvRepository lotesCsvRepository;

    @Mock
    LotesCsvMapper lotesCsvMapper;

    @InjectMocks
    LotesCsvServiceImpl lotesCsvServiceImpl;

    LoteCsv loteCsv;
    LotesCsvDto loteCsvDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        loteCsv = LoteCsv.builder()
                .nombreArchivo("Archivo")
                .usuario("SUPERUSUARIO")
                .id(1L)
                .fechaCarga(LocalDate.of(2020, 1, 8))
                .build();

        loteCsvDTO = LotesCsvDto.builder()
                .nombreArchivo("Archivo")
                .usuario("SUPERUSUARIO")
                .id(1L)
                .fechaCarga(LocalDate.of(2020, 1, 8))
                .build();
    }

    @Test
    void getLotesRecent() {
        when(lotesCsvRepository.getLotesRecent()).thenReturn(List.of(loteCsv));
        when(lotesCsvMapper.mapLotesCsvDto(List.of(loteCsv))).thenReturn(List.of(loteCsvDTO));
        List<LotesCsvDto> lisLotesCsv = lotesCsvServiceImpl.getLotesRecent();
        assertNotNull(lisLotesCsv);
        assertEquals(lisLotesCsv.get(0),loteCsvDTO);
        assertTrue(lisLotesCsv.size()==1);
    }

}