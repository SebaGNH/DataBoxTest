package ar.edu.ues21.pricing.constant;

import org.springframework.http.CacheControl;

import java.util.concurrent.TimeUnit;

public class Constant {

    public static final CacheControl cacheControlWithEtag = CacheControl
            .maxAge(5, TimeUnit.SECONDS)
            .cachePrivate()
            .mustRevalidate();
}
