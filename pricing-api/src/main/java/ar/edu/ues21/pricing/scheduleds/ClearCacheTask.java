package ar.edu.ues21.pricing.scheduleds;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ClearCacheTask {
   @Autowired
   private CacheManager cacheManager;

   @Scheduled(cron = "0 0 20 * * ?")
   public void reportCurrentTime() {
      cacheManager.getCacheNames().parallelStream().forEach(name -> cacheManager.getCache(name).clear());
   }
}