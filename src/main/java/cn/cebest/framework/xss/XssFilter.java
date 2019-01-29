package cn.cebest.framework.xss;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.stereotype.Component;


/**
 *  Xss过滤器
  * @author maming  
  * @date 2018年8月29日
 */

@Component
@ServletComponentScan
@WebFilter(urlPatterns = "/*",filterName = "xssFilter")
public class XssFilter implements Filter{

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response= (HttpServletResponse) servletResponse;
		new XsslHttpServletRequestWrapper((HttpServletRequest) request);
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		
	}

}
