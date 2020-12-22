package br.com.financas.api.exceptionHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.financas.api.Constantes;

@ControllerAdvice
public class FinancasExceptionHandler extends ResponseEntityExceptionHandler {
	
	@Autowired
	private MessageSource messageSource;
	
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		// // NOTA: Captura messagens que não conseguiu ler
		String mensagemUsuario = messageSource.getMessage(Constantes.ARGUMENTO_INVALIDO, null, LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = ex.getCause() != null ?  ex.getCause().toString() : ex.toString();
		List<MensagemErro> erros = Arrays.asList(new  MensagemErro(mensagemUsuario, mensagemDesenvolvedor) );
		return handleExceptionInternal(ex, erros , headers, HttpStatus.BAD_REQUEST, request);
	}
	
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		List<MensagemErro> erros = criarListaErros( ex.getBindingResult() );
		
		return handleExceptionInternal(ex, erros , headers, HttpStatus.BAD_REQUEST, request);
	}
	
	@ExceptionHandler({EmptyResultDataAccessException.class})
	public ResponseEntity<Object> handlehandleEmptyResultDataAccessException(EmptyResultDataAccessException ex, WebRequest request) {
		
		String mensagemUsuario = messageSource.getMessage(Constantes.RECURSO_NAO_ENCONTRADO, null, LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = ex.toString();
		List<MensagemErro> erros = Arrays.asList(new  MensagemErro(mensagemUsuario, mensagemDesenvolvedor) );
		return handleExceptionInternal(ex, erros , new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}
	
	@ExceptionHandler({TransactionSystemException.class})
	public ResponseEntity<Object> handleConstraintViolation(Exception ex, WebRequest request){
		String mensagemUsuario = messageSource.getMessage(Constantes.RECURSO_NAO_ENCONTRADO, null, LocaleContextHolder.getLocale());
		Throwable cause = ((TransactionSystemException) ex).getRootCause();
		String mensagemDesenvolvedor = cause.getMessage();
		List<MensagemErro> erros = Arrays.asList(new  MensagemErro(mensagemUsuario, mensagemDesenvolvedor) );
		return handleExceptionInternal(ex, erros , new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}
	
	@ExceptionHandler({DataIntegrityViolationException.class})
	public ResponseEntity<Object> handleDataIntegrityViolationException(Exception ex, WebRequest request){
		String mensagemUsuario = messageSource.getMessage(Constantes.RECURSO_OPERACAO_NAO_PERMITIDA, null, LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = ExceptionUtils.getRootCauseMessage(ex);
		List<MensagemErro> erros = Arrays.asList(new  MensagemErro(mensagemUsuario, mensagemDesenvolvedor) );
		return handleExceptionInternal(ex, erros , new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}
	
	
	private List<MensagemErro> criarListaErros(BindingResult bindingResult){
		List<MensagemErro> erros = new ArrayList<>();
		
		for(FieldError fieldError : bindingResult.getFieldErrors()) {
			
			String mensagemUsuario = messageSource.getMessage(fieldError ,LocaleContextHolder.getLocale());
			String mensagemDesenvolvedor = fieldError.toString();
			erros.add( new MensagemErro(mensagemUsuario, mensagemDesenvolvedor)  );
		}
		
		return erros;
	}
	
	public static class MensagemErro {
		
		private String mensagemUsuario;
		private String mensagemDesenvolvedor;
		
		public MensagemErro(String mensagemUsuario, String mensagemDesenvolvedor) {
			this.mensagemUsuario = mensagemUsuario;
			this.mensagemDesenvolvedor = mensagemDesenvolvedor;
		}

		public String getMensagemUsuario() {
			return mensagemUsuario;
		}
		
		public String getMensagemDesenvolvedor() {
			return mensagemDesenvolvedor;
		}
		
	}


}
