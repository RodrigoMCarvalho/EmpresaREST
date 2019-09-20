package com.empresaRest.exception;

import java.io.PrintStream;
import java.io.PrintWriter;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class LimitAgeException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	public LimitAgeException(String mensagem) {
		super(mensagem);
	}

	@Override
	public void printStackTrace() {
	}

	@Override
	public void printStackTrace(PrintStream arg0) {
	}

	@Override
	public void printStackTrace(PrintWriter arg0) {
	}

	
}
