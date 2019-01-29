package cn.cebest.framework.lucene.solr.impl;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import cn.cebest.framework.lucene.solr.SolrService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SolrServiceImpl implements SolrService {

	 @Autowired
	  private SolrClient client;

	/**
	 * 创建索引
	 */
	@Override
	public UpdateResponse createIndex(SolrInputDocument doc) {
		UpdateResponse response = null;
		try {
			response = client.add(doc);
			client.commit();
		} catch (Exception e) {
			log.error("创建solr索引异常", e);
		}
		return response;
	}
	
	
	/**
	 * solr查询
	 * @param solrQuery
	 * @return
	 */
	@Override
	public QueryResponse query(SolrQuery solrQuery) {
		return pageQuery(solrQuery, null, null, false, null);
	}

	
	/**
	 * solr查询
	 * @param solrQuery 查询条件
	 * @param start     起始行
	 * @param rows      显示条数
	 * @param isHLight  是否高亮
	 * @param hLightPram高亮字段
	 * @return
	 */
	@Override
	public QueryResponse pageQuery(SolrQuery solrQuery, Integer start, Integer rows, boolean isHLight, String hLightPram) {
		QueryResponse response = null;
		try {
			
			// 设置分页
			if(start != null && rows != null){
				solrQuery.setStart(start);
				solrQuery.setRows(rows);
			}
			// 设置高亮
			if(isHLight){
				solrQuery.setHighlight(true).setHighlightSnippets(1);
				solrQuery.setParam("hl.fl", "title");
				solrQuery.setHighlightSimplePre("<font color='red'>");//标记，高亮关键字前缀  
				solrQuery.setHighlightSimplePost("</font>");//后缀
			}
			response = client.query(solrQuery);
		} catch (Exception e) {
			log.error("solr查询异常", e);
		}
		return response;
	}


}
