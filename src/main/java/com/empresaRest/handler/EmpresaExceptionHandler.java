package com.empresaRest.handler;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.empresaRest.exception.ResourceNotFoundException;
import com.empresaRest.validation.GenericErrorDetails;
import com.empresaRest.validation.ResourceNotFoundDetails;
import com.empresaRest.validation.ValidationErrorDetails;

@ControllerAdvice
public class EmpresaExceptionHandler {
	
	@ExceptionHandler(ConstraintViolationException.class)
	  public ResponseEntity<?>  errorCPFDuplicado() {
		return new ResponseEntity<>("CPF informado já está cadastrado na empresa.", 
				HttpStatus.BAD_REQUEST);
	  }
	
	@ExceptionHandler(NullPointerException.class)
	public ResponseEntity<?> errorNullPointer(NullPointerException e) {
		GenericErrorDetails grDetails = GenericErrorDetails.Builder
				.newBuilder()
				.timestamp(LocalDate.now())
				.title("Ocorreu um erro interno. Favor entrar em contato com o administrador")
				.detail(e.getMessage())
				.developerMessage(e.getClass().getName())
				.build();
		return new ResponseEntity<>(grDetails, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(ResourceNotFoundException.class)
<<<<<<< HEAD
	  public ResponseEntity<?>  handlerResouceNotFoundException(ResourceNotFoundException e) {
		ResourceNotFoundDetails rnfDetails = ResourceNotFoundDetails.Builder
				.newBuilder()
				.timestamp(LocalDate.now())
				.title("Recurso não encontrado")
				.detail(e.getMessage())
				.developerMessage(e.getClass().getName())
=======
	  public ResponseEntity<?> handlerResouceNotFoundException(ResourceNotFoundException e) {
		ResourceNotFoundDetails rnfDetails = ResourceNotFoundDetails.Builder
				.newBuilder()
				.timestamp(LocalDate.now())
				.title("Recurso não encontrado")	
				.developerMessage(e.getClass().getName())
				.status(404)
				.detail(e.getMessage())
>>>>>>> a27c05feaabc438a47836cd4c5400fd6e9258733
				.build();
		return new ResponseEntity<>(rnfDetails, HttpStatus.NOT_FOUND);
	  }
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
<<<<<<< HEAD
	  public ResponseEntity<?>  handlerResouceNotFoundException(MethodArgumentNotValidException e) {
=======
	  public ResponseEntity<?> handlerResouceNotFoundException(MethodArgumentNotValidException e) {
>>>>>>> a27c05feaabc438a47836cd4c5400fd6e9258733
		
		List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
		String fields = fieldErrors.stream().map(FieldError::getField).collect(Collectors.joining(","));
		String fieldMessages = fieldErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(","));
		
		ValidationErrorDetails veDetails = ValidationErrorDetails.Builder
				.newBuilder()
				.timestamp(LocalDate.now())
				.title("Erros de validações.")
				.status(HttpStatus.BAD_REQUEST.value())
				.detail("Erros de validações.")
				.developerMessage(e.getClass().getName())
				.field(fields)
				.fieldMessage(fieldMessages)
				.build();
		return new ResponseEntity<>(veDetails, HttpStatus.BAD_REQUEST);
	  }
}
