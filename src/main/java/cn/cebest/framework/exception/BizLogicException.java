package cn.cebest.framework.exception;

/**
 * 
 * @author gaopeng@300.cn
 * @version 2018年8月29日 下午1:12:59
 */
public class BizLogicException extends RuntimeException {

	private static final long serialVersionUID = 6772006553291150019L;

	public BizLogicException() {
		super();
	}

	public BizLogicException(String message) {
		super(message);
	}

	public BizLogicException(Throwable cause) {
		super(cause);
	}

	public BizLogicException(String message, Throwable cause) {
		super(message, cause);
	}
}
