package cn.cebest.framework.conf;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cn.cebest.framework.cache.redis.RedisService;
import cn.cebest.framework.cache.redis.impl.RedisServiceImpl;


/**
 *  redis配置
  * @author maming  
  * @date 2018年8月28日
 */


@Configuration
@ConditionalOnClass(RedisService.class)
@ConditionalOnProperty(prefix="gc.redis", value="enable", matchIfMissing = false)
public class RedisConfig {
	
	@Bean
	@ConditionalOnMissingBean(RedisService.class)
	public RedisService redisService(){
		RedisService redisService = new RedisServiceImpl();
		return redisService;
	}
	
}
