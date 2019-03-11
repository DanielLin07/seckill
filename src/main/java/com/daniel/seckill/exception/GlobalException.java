package com.daniel.seckill.exception;


import com.daniel.seckill.common.CodeMsg;

/**
 * 全局异常
 *
 * @author DanielLin07
 * @date 2019/3/7 17:28
 */
public class GlobalException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	private CodeMsg codeMsg;
	
	public GlobalException(CodeMsg codeMsg) {
		super(codeMsg.toString());
		this.codeMsg = codeMsg;
	}

	public CodeMsg getCodeMsg() {
		return codeMsg;
	}

}
