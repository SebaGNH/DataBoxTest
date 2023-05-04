package ar.edu.ues21.pricing.scheduleds;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import ar.edu.ues21.pricing.repository.ArticleCsvRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ClearOldCsv {

   private final ArticleCsvRepository csvRepository;

   @Value("${spring.purge.months:2}")
   private Long months;

   @Scheduled(cron = "0 0 0 1 * ?")
   public void purgeOldData() {
         csvRepository.deleteByLoadDateBefore(LocalDate.now().minusMonths(months));
   }

}
