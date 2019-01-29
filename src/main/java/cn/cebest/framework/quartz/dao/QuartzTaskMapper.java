package cn.cebest.framework.quartz.dao;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.ss.formula.functions.T;
import cn.cebest.framework.quartz.QuartzTask;


/**
 *  定时任务DAO
  * @author maming  
  * @date 2018年8月28日
 */
@Mapper
public interface QuartzTaskMapper {
	
	/**
     * 根据ID，查询定时任务
     */
	public QuartzTask queryObject(@Param("jobId")Long jobId);

	/**
     * 分页查询定时任务列表
     */
	public List<T> pageList(Map<String, Object> param);
	
	/**
     * 编辑定时任务
     */
    public int updateById(QuartzTask quartzTask);
    
    /**
     * 批量更新定时任务状态
     */
    public int updateBatchTasksStatus(@Param("ids")List<Long> ids, @Param("status")Integer status) ;
    
    /**
     * 保存定时任务
     */
    public int insert(QuartzTask quartzTask);
    
}
