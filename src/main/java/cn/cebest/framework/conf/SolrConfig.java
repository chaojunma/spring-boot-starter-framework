package cn.cebest.framework.conf;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import cn.cebest.framework.lucene.solr.SolrService;
import cn.cebest.framework.lucene.solr.impl.SolrServiceImpl;

/**
 *  solr配置
  * @author maming  
  * @date 2018年9月3日
 */
@Configuration
@ConditionalOnProperty(prefix="gc.lucene.solr", value="enable", matchIfMissing = true)
public class SolrConfig {

	
	@Bean
	@ConditionalOnMissingBean(SolrService.class)
	public SolrService solrService(){
		return new SolrServiceImpl();
	}
}
