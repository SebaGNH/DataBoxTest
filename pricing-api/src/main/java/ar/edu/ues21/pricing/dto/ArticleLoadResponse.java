package ar.edu.ues21.pricing.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleLoadResponse {

    private Long totalElements;
    private List<ArticleExtendLoadDto> articles;
}
