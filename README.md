# spring-boot-starter-framework使用说明





spring-boot-starter-framework是一个核心框架包，springboot整合了很多依赖包，像spring-boot-starter-web、<br/>
spring-boot-starter-data-redis等等。<br/>
spring-boot-starter-framework在基础上又做了一次整合，里面包含上面的所有start依赖。<br/>
这样当我们开发项目的时候就只需要在pom.xml文件中添加一个spring-boot-starter-framework依赖即可，配置如下:<br/>

```
    <dependency>
        <groupId>cn.cebest</groupId>
        <artifactId>spring-boot-starter-framework</artifactId>
        <version>0.0.1-SNAPSHOT</version>
    </dependency>
    
```

## 该框架包含哪些功能

1、工具类<br/>
2、自定义注解<br/>
3、参数验证<br/>
4、防xss注入<br/>
5、log日志<br/>
6、核心配置及实现(redis、mail、json、mybatis、quartz、shiro、solr、enjoy模板引擎、webmvc等等)<br/>

下面我们来简单说明一下如何使用

### 工具类的使用

工具类在cn.cebest.framework.util包下，我们可以直接通过 类名.方法名去调用

### 自定义注解

我们自定义了@Mobile注解 和 @SysLog注解<br/>

@Mobile注解应用在数据模型的变量上，用于验证手机号格式，用法如下：

    @Mobile
    private String phone;
    
@SysLog应用在Controller类中的方法上，用于记录请求日志(包括请求参数、url、ip地址、返回结果等等),用法如下:

    @SysLog
	@GetMapping("index")
	public String index(){
		// TODO
	}

### 参数验证

参数验证和上面@Mobile注解的用法一样，只是@Mobile是自定义注解(验证是自己实现的)，内置的注解如下:

    
    @Null 限制只能为null
    @NotNull 限制必须不为null
    @AssertFalse 限制必须为false
    @AssertTrue 限制必须为true
    @DecimalMax(value) 限制必须为一个不大于指定值的数字
    @DecimalMin(value) 限制必须为一个不小于指定值的数字
    @Digits(integer,fraction) 限制必须为一个小数，且整数部分的位数不能超过integer，小数部分的位数不能超过fraction
    @Future 限制必须是一个将来的日期
    @Max(value) 限制必须为一个不大于指定值的数字
    @Min(value) 限制必须为一个不小于指定值的数字
    @Past 限制必须是一个过去的日期
    @Pattern(value) 限制必须符合指定的正则表达式
    @Size(max,min) 限制字符长度必须在min到max之间
    @Past 验证注解的元素值（日期类型）比当前时间早
    @NotEmpty 验证注解的元素值不为null且不为空（字符串长度不为0、集合大小不为0）
    @NotBlank 验证注解的元素值不为空（不为null、去除首位空格后长度为0），不同于@NotEmpty，@NotBlank只应用于字符串且在比较时会去除字符串的空格
    @Email 验证注解的元素值是Email，也可以通过正则表达式和flag指定自定义的email格式
    
### 防xss注入

防xss注入通过过滤器Filter实现，我们不需要关注，每次请求所有的参数会被自动过滤


### log日志

见上面@SysLog注解的用法


### 核心配置及实现

` 我们把核心配置放在最后面，是因为它比较主要，也是spring-boot-starter-framework核心框架包的重中之重 ` <br/>
    
下面我们拿其中的几个讲解一下如何使用：
    

1)webmvc配置：

```
    
@Slf4j
@Configuration
@ConditionalOnProperty(prefix="gc.webmvc", value="enable", matchIfMissing = true)
public class WebMvcConfig extends WebMvcConfigurerAdapter{
	
	/**
	 * 存放访问的url及要跳转的视图
	 */
	@Value("#{${gc.webmvc.url2views:null}}")
	private Map<String,String> url2views;
	
	/**
	 * 文件上传最大限制
	 */
	@Value("${gc.webmvc.file.maxUploadSize:10 * 1024 * 1024}")
	private String maxUploadSize;
	
	/**
	 * 静态资源
	 */
	@Value("#{${gc.webmvc.static.url2locs:null}}")
	private Map<String,String> url2locs;
	
	/**
	 * 拦截器
	 */
	@Value("#{${gc.webmvc.interceptor.pattern2class:null}}")
	private Map<String,String> pattern2class;

	
	/**
	 * 使用@Value需要的bean
	 */
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfigure(){
		return new PropertySourcesPlaceholderConfigurer();
	}
	
	
	/**
	 * 无逻辑视图跳转
	 */
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		if (url2views != null) {
			Set<String> keys = url2views.keySet();
			for (String key : keys) {
				String value = url2views.get(key);
				registry.addViewController("/" + key).setViewName("/" + value);
			}
		}
	}
	
	
	/**
	 * 文件上传相关配置
	 * @return
	 */
	@Bean
	public MultipartResolver multipartResolver() {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		// 默认允许最大上传10M
		 ScriptEngineManager manager = new ScriptEngineManager();
		 ScriptEngine se = manager.getEngineByName("js");
		 try {
			Double  maxSize = (Double) se.eval(maxUploadSize);
			multipartResolver.setMaxUploadSize(maxSize.longValue());
		} catch (ScriptException e) {
			log.error("执行表达式异常", e);
		}
		return multipartResolver;
	}
	
	
	/**
	 * 设置访问静态资源
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
		if (url2locs != null) {
			Set<String> keys = url2locs.keySet();
			for (String key : keys) {
				String location = url2locs.get(key);
				registry.addResourceHandler(key).addResourceLocations(location);
			}
		}
	}
	
	
	/**
	 * 配置拦截器
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		if (pattern2class != null) {
			Set<String> keys = pattern2class.keySet();
			for (String pattern : keys) {
				String className = pattern2class.get(pattern);
				try {
					Class<?> c = Class.forName(className);
					if(org.springframework.web.servlet.HandlerInterceptor.class.isAssignableFrom(c)){
						registry.addInterceptor((HandlerInterceptor) c.newInstance()).addPathPatterns(pattern);
					}
				} catch (Exception e) {
					log.error("添加拦截器异常", e);
				} 
			}
		}
	}
	
	
	/**
	 * 重写HttpMessageConverter
	 */
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.add(stringHttpMessageConverter());
	}
	
	
	/**
	 * 配置消息转换器
	 */
	@Bean
	StringHttpMessageConverter stringHttpMessageConverter(){
		List<MediaType> supportedMediaTypes = new ArrayList<>();
		supportedMediaTypes.add(new MediaType("text", "plain", Charset.forName("UTF-8")));
		supportedMediaTypes.add(new MediaType("text", "html", Charset.forName("UTF-8")));
		supportedMediaTypes.add(new MediaType("application", "json", Charset.forName("UTF-8")));
		StringHttpMessageConverter stringHttpMessageConverter = new StringHttpMessageConverter(Charset.forName("UTF-8"));
		stringHttpMessageConverter.setSupportedMediaTypes(supportedMediaTypes);
		return stringHttpMessageConverter;
	}
	
	
}

```

我们在这里为每个配置设置了一个开关，如果我们不需要相关配置的时候，我们可以在日后开发的项目配置中设置改配置的开关就可以了<br/>

例如我们这里可以通过设置 ` gc.webmvc.enable=false ` 来关闭此配置<br/>

可以通过 ` gc.webmvc.url2views={login:"login",index:"index",main:"main"} ` 配置访问路径与视图的对应关系 `注意：这里只应用于无业务逻辑处理的action请求`<br/>

通过 ` gc.webmvc.file.maxUploadSize:20 * 1024 * 1024 `设置文件上传文件最大限制<br/>

通过 `gc.webmvc.interceptor.pattern2class={"/**":"cn.cebest.interceptor.AuthorityIntercept"}` 设置拦截器，map对象的key为拦截路径，value为拦截器全路径<br/>



其他的配置和webmvc的配置是类似的，所以就不一一列举了<br/>


这样我们在以后的开发过程中不需要添加这些配置了，而只是在配置文件中添加相应的配置就可以了


    

