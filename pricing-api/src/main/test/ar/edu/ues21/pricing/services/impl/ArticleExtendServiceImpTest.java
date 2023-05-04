package ar.edu.ues21.pricing.services.impl;

import ar.edu.ues21.pricing.dto.ArticleExtendDto;
import ar.edu.ues21.pricing.mapper.ArticleExtendsTransformer;
import ar.edu.ues21.pricing.model.ArticleExtend;
import ar.edu.ues21.pricing.repository.ArticleExtendRepository;
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
import java.util.Date;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class ArticleExtendServiceImpTest {

    @Mock
    ArticleExtendRepository articleExtendRepository;

    @Mock
    ArticleExtendsTransformer articleExtendsTransformer;

    @InjectMocks
    ArticleExtendServiceImp articleExtendServiceImp;

    private Date date;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        date = new Date();
    }

    @Test
    void listarPreciosVigentes() {
        ArticleExtend articleExtend = new ArticleExtend();

        articleExtend.setArticle("Prueba");

        List<ArticleExtendDto> articleExtendDtoList = new ArrayList<>();

        articleExtendDtoList.add(articleExtendsTransformer.apply(articleExtend));

        Page<ArticleExtendDto> page = new PageImpl<>(articleExtendDtoList);

        Pageable pageRequest = PageRequest.of(0, 10 );

        when(articleExtendRepository.listarPreciosVigentes(any(),any(),any(),anyString(),anyString(), any(), any(),anyString(),anyString(), any(), any(), any())).thenReturn(page);

        Page<ArticleExtendDto> finalpage = articleExtendServiceImp.listarPreciosVigentes(6066L, 1062L, 1861L, "RI", "3", 3L, 1L, "30", "5", date, date, pageRequest);

        assertNotNull(finalpage);
        assertEquals(1, finalpage.getTotalElements());
    }
}