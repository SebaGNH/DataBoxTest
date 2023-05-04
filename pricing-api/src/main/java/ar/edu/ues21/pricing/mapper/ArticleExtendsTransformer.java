package ar.edu.ues21.pricing.mapper;

import ar.edu.ues21.pricing.dto.ArticleExtendDto;
import ar.edu.ues21.pricing.model.ArticleExtend;
import org.springframework.stereotype.Component;

import java.util.function.Function;


@Component
public class ArticleExtendsTransformer implements Function<ArticleExtend, ArticleExtendDto> {


    @Override
    public ArticleExtendDto apply(ArticleExtend articleExtend) {
        return null;
    }
}
