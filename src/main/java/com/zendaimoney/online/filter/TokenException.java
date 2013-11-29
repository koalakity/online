package com.zendaimoney.online.filter;

/**
 * 令牌异常
 *
 * @author ultrafrog
 * @version 1.0, 2013-4-23
 * @since 1.0
 */
public class TokenException extends RuntimeException {

	private static final long serialVersionUID = 185678558502853834L;

	/**
	 * 构造函数
	 */
	public TokenException() {
		super("token is not match!");
	}
}
