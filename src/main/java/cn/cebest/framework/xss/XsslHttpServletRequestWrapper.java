package cn.cebest.framework.xss;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import org.springframework.web.util.HtmlUtils;
import com.jfinal.kit.StrKit;


/**
 *  防Xss注入处理
  * @author maming  
  * @date 2018年8月29日
 */
public class XsslHttpServletRequestWrapper  extends HttpServletRequestWrapper{

	HttpServletRequest xssRequest = null;  

	public XsslHttpServletRequestWrapper(HttpServletRequest request) {
		super(request);
		xssRequest = request;
	}
	
	
	@Override
	public String getParameter(String name) {
		String value = xssRequest.getParameter(name);
		if(StrKit.notBlank(value)) {
			value = cleanXSS(value);
		}
		return value;
	}

	
	
	@Override
	public Map<String, String[]> getParameterMap() {
		Map<String, String[]> paraMap = super.getParameterMap();
		Map<String, String[]> result = new HashMap<String, String[]>(paraMap.size());
        // 对于paraMap为空的直接return
        if (null == paraMap || paraMap.isEmpty()) {
            return paraMap;
        }
        for (Entry<String, String[]> entry : paraMap.entrySet()) {
            String key = entry.getKey();
            String[] values     = entry.getValue();
            if (null == values) {
                continue;
            }
            String[] newValues  = new String[values.length];
            for (int i = 0; i < values.length; i++) {
                newValues[i] = cleanXSS(values[i]);
            }
            result.put(key, newValues);
        }
        return result;
	}
	
	  @Override
	    public String[] getParameterValues(String parameter) {
	        String[] values = xssRequest.getParameterValues(parameter);
	        if (values == null)  {
	            return null;
	        }
	        int count = values.length;
	        String[] encodedValues = new String[count];
	        for (int i = 0; i < count; i++) {
	            encodedValues[i] = cleanXSS(values[i]);
	        }
	        return encodedValues;
	    }
	  
	  
	  @Override
	    public String getHeader(String name) {

	        String value = xssRequest.getHeader(name);
	        if (value == null)
	            return null;
	        return cleanXSS(value);
	    }
	  
	  /**
	     * 获取request的属性时，做xss过滤
	     */
	    @Override
	    public Object getAttribute(String name) {
	        Object value = xssRequest.getAttribute(name);
	        if (null != value && value instanceof String) {
	            value = cleanXSS((String) value);
	        }
	        return value;
	    }
	
	
	/**
	 * 将字符串转换成html标签
	 */
	private  static  String cleanXSS(String value) {
        return HtmlUtils.htmlEscape(value);
    }
}
