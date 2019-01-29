package cn.cebest.framework.conf;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 *  json序列化配置
  * @author maming  
  * @date 2018年8月28日
 */
@Configuration
@ConditionalOnProperty(prefix="gc.jackson", value="enable", matchIfMissing = true)
public class JacksonConfig {

	 @Bean
	    public ObjectMapper objectMapper() {
		 	ObjectMapper objectMapper = new ObjectMapper();
		 	// 序列化为NULL时字段不返回
		 	objectMapper.setSerializationInclusion(Include.NON_NULL);
		 	// 对于空的对象转json的时候不抛出错误
		 	objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
	        return objectMapper;
	    }
}
