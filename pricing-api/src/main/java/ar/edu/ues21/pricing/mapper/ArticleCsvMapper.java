package ar.edu.ues21.pricing.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import ar.edu.ues21.pricing.dto.ArticleCsvDto;
import ar.edu.ues21.pricing.dto.ArticleExtendDto;
import ar.edu.ues21.pricing.model.ArticleCsv;

@Mapper
public interface ArticleCsvMapper {

   ArticleCsvMapper ART_CSV = Mappers.getMapper(ArticleCsvMapper.class);

   ArticleCsv fromDto(ArticleCsvDto dto);

   ArticleCsvDto toDto(ArticleCsv entity);

   @Mappings({
         @Mapping(target = "id", ignore = true),
         @Mapping(target = "csvId", ignore = true)
   })
   ArticleCsv merge(@MappingTarget ArticleCsv entity, ArticleCsvDto dto);

   default Page<ArticleCsvDto> toPage(Page<ArticleCsv> page) {
      return page.map(this::toDto);
   }

}
