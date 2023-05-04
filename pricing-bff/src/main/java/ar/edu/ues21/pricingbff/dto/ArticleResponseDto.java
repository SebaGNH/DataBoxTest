package ar.edu.ues21.pricingbff.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleResponseDto {

    private Long totalElements;
    private List<Object> articles;
}
