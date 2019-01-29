package cn.cebest.framework.lucene.solr;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;

/**
 *  solr相关服务
  * @author maming  
  * @date 2018年9月3日
 */
public interface SolrService {
	
	/**
	 * 创建索引
	 * @param doc
	 */
	public UpdateResponse createIndex(SolrInputDocument doc);
	
	/**
	 * solr查询
	 * @param solrQuery
	 * @return
	 */
	public QueryResponse query(SolrQuery solrQuery);
	
	/**
	 * solr分页查询
	 * @param solrQuery 查询条件
	 * @param start     起始行
	 * @param rows      显示条数
	 * @param isHLight  是否高亮
	 * @param hLightPram高亮字段
	 * @return
	 */
	public QueryResponse pageQuery(SolrQuery solrQuery, Integer start, Integer rows, boolean isHLight, String hLightPram);

}
