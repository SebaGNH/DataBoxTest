package ar.edu.ues21.pricing.services.impl;

import ar.edu.ues21.pricing.dto.ArticleCsvDto;
import ar.edu.ues21.pricing.mapper.ArticleCsvMapper;
import ar.edu.ues21.pricing.model.ArticleCsv;
import ar.edu.ues21.pricing.repository.ArticleCsvRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class ArticleCsvServiceImpTest {

    @Mock
    ArticleCsvRepository csvRepository;

    @Mock
    ArticleCsvMapper articleCsvMapper;

    @InjectMocks
    ArticleCsvServiceImp articleCsvServiceImp;

    ArticleCsvDto articleCsvDto;
    ArticleCsvDto articleCsvDtoUpdate;
    ArticleCsv articleCsv;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        articleCsv = new ArticleCsv();
        articleCsv.setId(152L);
        articleCsv.setDescriptionProgram("Description test");

        articleCsvDto = new ArticleCsvDto();
        articleCsvDto.setCsvId(152L);
        articleCsvDto.setDescriptionProgram("Description test");

        articleCsvDtoUpdate = new ArticleCsvDto();
        articleCsvDtoUpdate.setCsvId(153L);
        articleCsvDtoUpdate.setDescriptionProgram("Description test update");
    }

    @Test
    void create() {
        when(csvRepository.save(any())).thenReturn(articleCsv);

        ArticleCsvDto articleCsvDto1 = articleCsvServiceImp.create(articleCsvDto);

        assertEquals(152L,articleCsvDto1.getId());
        assertNotNull(articleCsvDto1);
        assertEquals("Description test", articleCsvDto1.getDescriptionProgram());
    }

    @Test
    void update() {
        when(csvRepository.findById(any())).thenReturn(Optional.of(articleCsv));
        when(articleCsvMapper.toDto(articleCsv)).thenReturn(articleCsvDtoUpdate);

        articleCsvServiceImp.update(articleCsvDtoUpdate);
        assertNotNull(articleCsvDtoUpdate);
    }

    @Test
    void getAllByCsv() {
        ArticleCsv articleCsv = new ArticleCsv();
        articleCsv.setCsvId(10L);

        List<ArticleCsv> articleCsvDtoList = new ArrayList<>();

        Page<ArticleCsv> page = new PageImpl<>(articleCsvDtoList);
        Pageable pageRequest = PageRequest.of(0, 10 );

        when(csvRepository.findAllByCsvIdIs(anyLong(), any())).thenReturn(page);
        Page<ArticleCsvDto> finalPage = articleCsvServiceImp.getAllByCsv(10L, pageRequest);

        assertNotNull(finalPage);
    }

    @Test
    void deleteAllByCsv() {
        ArticleCsv articleCsv = new ArticleCsv();
        articleCsv.setCsvId(10L);

        List<ArticleCsv> articleCsvDtoList = new ArrayList<>();

        Page<ArticleCsv> page = new PageImpl<>(articleCsvDtoList);
        Pageable pageRequest = PageRequest.of(0, 10 );

        when(csvRepository.findAllByCsvIdIs(anyLong(), any())).thenReturn(page);

        articleCsvServiceImp.deleteAllByCsv(10L);

        assertTrue(articleCsvDtoList.isEmpty());
    }

    @Test
    void getById() {
        when(csvRepository.findById(anyLong())).thenReturn(Optional.of(articleCsv));
        when(articleCsvMapper.toDto(articleCsv)).thenReturn(articleCsvDto);

        ArticleCsvDto articleCsvDto1 = articleCsvServiceImp.getById(152L);
        assertNotNull(articleCsvDto1);
        assertEquals(152,articleCsvDto1.getId());
    }
}