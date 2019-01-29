package cn.cebest.framework.conf;

import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import cn.cebest.framework.shiro.ShiroService;
import cn.cebest.framework.shiro.impl.ShiroServiceImpl;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedHashMap;
import java.util.Map;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.AuthorizingRealm;

/**
 *  shiro相关配置
  * @author maming  
  * @date 2018年8月31日
 */
@Slf4j
@Configuration
@ConditionalOnClass(ShiroService.class)
@ConditionalOnProperty(prefix="gc.shiro", value="enable", matchIfMissing = false)
public class ShiroConfig {
	
	/**
	 * 自定义shiroRealm
	 */
	@Autowired
	AuthorizingRealm shiroRealm;
	
	/**
	 * redis服务地址
	 */
	@Value("${spring.redis.host:127.0.0.1}")
    private String host;
	
	/**
	 * redis服务端口
	 */
    @Value("${spring.redis.port:6379}")
    private int port;
    
    /**
     * redis服务超时时间
     */
    @Value("${spring.redis.timeout:5000}")
    private int timeout;
    
    /**
     * shiro登录地址
     */
    @Value("${gc.shiro.login.url:/login}")
    private String loginUrl;
    
    /**
     * shiro登录成功跳转地址
     */
    @Value("${gc.shiro.success.url:/index}")
    private String successUrl;
    
    /**
     * shiro未授权地址
     */
    @Value("${gc.shiro.unauthorized.url:/403}")
    private String unauthorizedUrl;
    
    /**
     * cookie加密秘钥
     */
    @Value("${gc.shiro.cipher.key:3AvVhmFLUs0KTA3Kprsdag==}")
    private String cipherKey;
    
    /**
     * 是否从数据库加载权限，默认不加载数据库
     */
    @Value("${gc.shiro.load.db:false}")
    private boolean loadDb;
	
    
    @Value("#{${gc.shiro.url2auths:null}}")
	private Map<String,String> url2auths;
    
    @Value("${gc.shiro.cache.enable:false}")
    private boolean cache;
    
	@Autowired
	private ShiroService shiroService;
	
	/*@Bean
	@ConditionalOnMissingBean(ShiroService.class)
	public ShiroService shiroService(){
		return new ShiroServiceImpl();
	}*/
	
	
	
	
	/**
     * 密码校验规则HashedCredentialsMatcher
     * 这个类是为了对密码进行编码的 ,
     * 防止密码在数据库里明码保存 , 当然在登陆认证的时候 ,
     * 这个类也负责对form里输入的密码进行编码
     * 处理认证匹配处理器：如果自定义需要实现继承HashedCredentialsMatcher
     */
    @Bean("hashedCredentialsMatcher")
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        //指定加密方式为MD5
        credentialsMatcher.setHashAlgorithmName("MD5");
        //加密次数
        credentialsMatcher.setHashIterations(1);
        credentialsMatcher.setStoredCredentialsHexEncoded(true);
        return credentialsMatcher;
    }
	
	
    /**
     * shiro权限配置
     */
	@Bean
    public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {
		log.info("ShiroConfiguration.shirFilter开始执行...");
		
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		shiroFilterFactoryBean.setSecurityManager(securityManager);
		// 登录地址，如果不配默认访问login.jsp
        shiroFilterFactoryBean.setLoginUrl(loginUrl);
        // 登录成功后要跳转的链接
        shiroFilterFactoryBean.setSuccessUrl(successUrl);
        //未授权界面;
        shiroFilterFactoryBean.setUnauthorizedUrl("");
		
        
        if(loadDb) { // 从数据库加载配置
        	//加载权限配置
            Map<String, String> filterChainDefinitionMap = shiroService.loadFilterChainDefinitions();
            
            shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        } else {// 读取配置文件中的配置
        	if(url2auths != null) {
        		Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
        		filterChainDefinitionMap.putAll(url2auths);
        		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        	}
        }
        
        return shiroFilterFactoryBean;
	}
	
	 /**
     * cacheManager 缓存 redis实现
     * 使用的是shiro-redis开源插件
     */
	
	@Bean
	@ConditionalOnProperty(prefix="gc.shiro.cache", value="enable", matchIfMissing = false)
    public RedisCacheManager cacheManager() {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());
        return redisCacheManager;
    }
	
	 /**
     * 配置shiro redisManager
     * 使用的是shiro-redis开源插件
     */
	@Bean
	@ConditionalOnProperty(prefix="gc.shiro.cache", value="enable", matchIfMissing = false)
    public RedisManager redisManager() {
        RedisManager redisManager = new RedisManager();
        redisManager.setHost(host);
        redisManager.setPort(port);
        redisManager.setExpire(1800);// 配置缓存过期时间
        redisManager.setTimeout(timeout);
        // redisManager.setPassword(password);
        return redisManager;
    }

    /**
     * Session Manager
     * 使用的是shiro-redis开源插件
     */
    @Bean
    @ConditionalOnProperty(prefix="gc.shiro.cache", value="enable", matchIfMissing = false)
    public DefaultWebSessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionDAO(redisSessionDAO());
        return sessionManager;
    }

    /**
     * RedisSessionDAO shiro sessionDao层的实现 通过redis
     * 使用的是shiro-redis开源插件
     */
    @Bean
    @ConditionalOnProperty(prefix="gc.shiro.cache", value="enable", matchIfMissing = false)
    public RedisSessionDAO redisSessionDAO() {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(redisManager());
        return redisSessionDAO;
    }

	
	
	/**
     * cookie对象
     */
    public SimpleCookie rememberMeCookie(){
       //这个参数是cookie的名称，对应前端的checkbox的name = rememberMe
       SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
       //<!-- 记住我cookie生效时间30天 ,单位秒;-->
       simpleCookie.setMaxAge(2592000);
       return simpleCookie;
    }
    
    /**
     * cookie管理对象;记住我功能
     */
    public CookieRememberMeManager rememberMeManager(){
       CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
       cookieRememberMeManager.setCookie(rememberMeCookie());
       //rememberMe cookie加密的密钥 建议每个项目都不一样 默认AES算法 密钥长度(128 256 512 位)
       cookieRememberMeManager.setCipherKey(Base64.decode(cipherKey));
       return cookieRememberMeManager;
    }
	
	
	/**
	 * 设置自定义权限认证
	 */
	@Bean
	public SecurityManager securityManager() {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		// 设置加密方式
		shiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
		securityManager.setRealm(shiroRealm);
		if(cache) {
			// 自定义缓存实现 使用redis
			securityManager.setCacheManager(cacheManager());
		}
		
		// 注入记住我管理器;
		securityManager.setRememberMeManager(rememberMeManager());
		return securityManager;

	}
	
   /**
    * 开启注解
    * @return
    */
   @Bean    
   public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {    
       AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();    
       advisor.setSecurityManager(securityManager());    
       return advisor;    
   }    
}
