package com.joe.dating.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableCaching
@EnableScheduling
public class CacheConfig {
    private static final Logger logger = LoggerFactory.getLogger(CacheConfig.class);

    public static final String PROFILE_SEARCH_CACHE = "PROFILE_SEARCH_CACHE";
    public static final String USER_BY_ID_CACHE = "USER_BY_ID_CACHE";
    public static final String LOCATION_CACHE = "LOCATION_CACHE";
    public static final String PAYMENT_CACHE = "PAYMENT_CACHE";
    public static final String PROFILE_VIEWS_BY_ID_CACHE = "PROFILE_VIEWS_CACHE";
    public static final String FLIRTS_BY_ID_CACHE = "FLIRTS_BY_ID_CACHE";
    public static final String FAVORITES_BY_ID_CACHE = "FAVORITES_BY_ID_CACHE";
    public static final String MESSAGES_BY_ID_CACHE = "MESSAGES_BY_ID_CACHE";

    private static final int MS_IN_DAY = 60000 * 1440;
    private static final int EXPIRE_DAYS = 2;

    @CacheEvict(cacheNames = {
            PROFILE_SEARCH_CACHE,
            USER_BY_ID_CACHE,
            PROFILE_VIEWS_BY_ID_CACHE,
            FLIRTS_BY_ID_CACHE,
            FAVORITES_BY_ID_CACHE,
            MESSAGES_BY_ID_CACHE

    }, allEntries = true)
    @Scheduled(fixedDelay = EXPIRE_DAYS * MS_IN_DAY)
    public void cacheEvict() {
        logger.info("Cache Evicted");
    }

    @Bean
    public CacheManager concurrentMapCacheManager() {
        ConcurrentMapCacheManager cmcm = new ConcurrentMapCacheManager();
        return cmcm;
    }
}
