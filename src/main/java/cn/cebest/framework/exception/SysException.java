package cn.cebest.framework.exception;

/**
 * 
 * @author gaopeng@300.cn
 * @version 2018年8月29日 下午1:13:04
 */
public class SysException extends RuntimeException {

	private static final long serialVersionUID = -5309574770175613338L;

	public SysException() {
		super();
	}

	public SysException(String message) {
		super(message);
	}

	public SysException(Throwable cause) {
		super(cause);
	}

	public SysException(String message, Throwable cause) {
		super(message, cause);
	}
}
