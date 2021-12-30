package com.empresaRest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.PrintStream;
import java.io.PrintWriter;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CpfException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public CpfException(String mensagem) {
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
