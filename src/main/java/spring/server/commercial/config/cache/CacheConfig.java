package spring.server.commercial.config.cache;

import java.time.Duration;
import java.util.Set;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

@Configuration
@EnableCaching
public class CacheConfig {
	@Bean
	public RedisConnectionFactory redisConnection() {
		return new LettuceConnectionFactory();
	}

	@Bean
	public RedisCacheConfiguration cacheConfiguration() {
		RedisCacheConfiguration configuration = RedisCacheConfiguration.defaultCacheConfig();
		// Cấu hình thời gian tồn tại của cache
		configuration = configuration.entryTtl(Duration.ofMinutes(10)); // Thời gian tồn tại 10 phút
		// Cấu hình sử dụng PrefixedCacheKeys
		configuration = configuration.computePrefixWith(cacheName -> "prefix_" + cacheName + "_");
		return configuration;
	}

	@Bean
	public RedisCacheManager cacheManager() {
		return RedisCacheManager.builder(this.redisConnection()).initialCacheNames(Set.of("aclCache"))
				.cacheDefaults(this.cacheConfiguration()).build();
	}
}
