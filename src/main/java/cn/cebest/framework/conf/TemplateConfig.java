package cn.cebest.framework.conf;

import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import com.jfinal.template.Directive;
import com.jfinal.template.ext.spring.JFinalViewResolver;
import com.jfinal.template.source.ClassPathSourceFactory;
import lombok.extern.slf4j.Slf4j;

/**
 *  模板引擎配置
  * @author maming  
  * @date 2018年8月28日
 */

@Slf4j
@Configuration
@ConditionalOnProperty(prefix="gc.template", value="enable", matchIfMissing = true)
public class TemplateConfig {
	
	/*
	 * 模板位置
	 */
	@Value("${gc.template.location:/templates/}")
	private String location;
	
	/**
	 * 共享类
	 */
	@Value("#{${gc.template.classnames:null}}")
	private Map<String,String> classNames;
	
	
	/**
	 * 扩展指令
	 */
	@Value("#{${gc.template.directives:null}}")
	private Map<String,String> directives;
	
	
	/**
	 * 使用@Value需要的bean
	 */
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfigure(){
		return new PropertySourcesPlaceholderConfigurer();
	}

	/**
	 * 模板引擎
	 */
	@Bean
	  public JFinalViewResolver getJFinalViewResolver() {
	    JFinalViewResolver jfr = new JFinalViewResolver();
	    // 设置热启动
	    jfr.setDevMode(true);
	    jfr.setSourceFactory(new ClassPathSourceFactory());
	    // 模板位置
	    JFinalViewResolver.engine.setBaseTemplatePath(location);
	    try {
			if(classNames != null) {
				Set<String> keys = classNames.keySet();
				for (String key : keys) {
					String className = classNames.get(key);
					Class<?> c = Class.forName(className);
					jfr.addSharedObject(key, c.newInstance());
				}
			}
		} catch (Exception e) {
			log.error("模板共享对象异常", e);
		} 
	    
		try {
			if (directives != null) {
				Set<String> keys = directives.keySet();
				for (String key : keys) {
					String className = directives.get(key);
					Class<?> c = Class.forName(className);
					if(com.jfinal.template.Directive.class.isAssignableFrom(c)) {
						jfr.addDirective(key, (Directive) c.newInstance());
					}
					
				}
			}
		} catch (Exception e) {
			log.error("添加扩展指令异常", e);
		}
	    jfr.setSessionInView(true);
	    jfr.setSuffix(".html");
	    jfr.setContentType("text/html;charset=UTF-8");
	    jfr.setOrder(0);
	    return jfr;
	  }
	
}
