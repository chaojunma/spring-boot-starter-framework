package cn.cebest.framework.shiro;

import java.util.Map;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;


/**
 *  shiro相关服务
  * @author maming  
  * @date 2018年8月31日
 */

public interface ShiroService {

	/**
	 * 初始化权限
	 * @return
	 */
	public Map<String, String> loadFilterChainDefinitions();
	
	
	/**
	 * 重置shiro权限配置
	 * @param shiroFilterFactoryBean
	 */
	public void reloadFilterChains(ShiroFilterFactoryBean shiroFilterFactoryBean);
}
