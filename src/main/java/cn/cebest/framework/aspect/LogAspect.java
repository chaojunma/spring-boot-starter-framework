package cn.cebest.framework.aspect;

import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import lombok.extern.slf4j.Slf4j;

/**
 *  日志切面
  * @author maming  
  * @date 2018年8月29日
 */

@Slf4j
@Aspect
@Component
public class LogAspect {

	
	/**
	 * 切点
	 */
	@Pointcut("@annotation(cn.cebest.framework.annotation.SysLog)")
	public void webLog() {
	}

	/**
	 * 前置方法
	 * @param joinPoint
	 * @throws Throwable
	 */
	@Before("webLog()")
	public void doBefore(JoinPoint joinPoint) throws Throwable {
		// 接收到请求，记录请求内容
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();
		// 记录下请求内容
		log.info("URL : " + request.getRequestURL().toString());
		log.info("HTTP_METHOD : " + request.getMethod());
		log.info("IP : " + request.getRemoteAddr());
		Enumeration<String> enu = request.getParameterNames();
		while (enu.hasMoreElements()) {
			String name = (String) enu.nextElement();
			log.info(String.format("name:{%s},value:{%s}", name, request.getParameter(name)));
		}
	}

	
	/**
	 *  返回结果后置方法
	 * @param result
	 * @throws Throwable
	 */
	@AfterReturning(returning = "result", pointcut = "webLog()")
	public void doAfterReturning(Object result) throws Throwable {
		// 处理完请求，返回内容
		log.info("RESPONSE : " + result);
	}

}
