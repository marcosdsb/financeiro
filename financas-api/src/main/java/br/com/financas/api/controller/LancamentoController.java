package br.com.financas.api.controller;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import br.com.financas.api.entity.LancamentoEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.financas.api.Constantes;
import br.com.financas.api.dto.LancamentoDTO;
import br.com.financas.api.event.RecursoCriadoEvent;
import br.com.financas.api.exceptionHandler.FinancasExceptionHandler.MensagemErro;
import br.com.financas.api.repository.LancamentoRepository;
import br.com.financas.api.repository.filter.LancamentoFilter;
import br.com.financas.api.service.LancamentoService;
import br.com.financas.api.service.exception.PessoaInexistenteOuInativaException;

@RestController
@RequestMapping("api/lancamento")
@Api(value = "API REST Lançamento")
@CrossOrigin(value = "*")
public class LancamentoController {
	
	@Autowired
	private LancamentoService service;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private LancamentoRepository repository;
	
	@Autowired
	private MessageSource messageSource;
	
	
	@GetMapping
	@ApiOperation(value = "Retorna Lançamento(s) pelo filtro")
	public ResponseEntity<Page<LancamentoEntity>> pesquisar(LancamentoFilter filter, Pageable pageable){
		Page<LancamentoEntity> dtos = service.filtrar(filter, pageable);
		return dtos != null ? ResponseEntity.ok(dtos) : ResponseEntity.noContent().build();

	}
	
	@GetMapping("/{id}")
	@ApiOperation(value = "Retorna um Lançamento pelo ID")
	public ResponseEntity<LancamentoDTO> obterLancamentoId(@PathVariable Long id){
		LancamentoDTO lancamentoDTO = service.obterLancamentoId(id);
		return lancamentoDTO != null ? ResponseEntity.ok(lancamentoDTO) : ResponseEntity.notFound().build();
	}
	
	@PostMapping
	@ApiOperation(value = "Grava uma novo Lançamento")
	public ResponseEntity<LancamentoDTO> salvarNovoLancamento(@Valid @RequestBody LancamentoDTO lancamentoDTO, HttpServletResponse response){
		
		LancamentoDTO dto = service.salvarNovoLancamento(lancamentoDTO);

		publisher.publishEvent(new RecursoCriadoEvent(this, response, dto.getId()) );
		
		return ResponseEntity.status(HttpStatus.CREATED).body(dto);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ApiOperation(value = "Deleta um Lançamento pelo ID")
	public void deletar(@PathVariable Long id) {
		repository.deleteById(id);
	}
	
	
	@ExceptionHandler({PessoaInexistenteOuInativaException.class})
	public ResponseEntity<Object> handlerPessoaInexistenteOuInativaException(PessoaInexistenteOuInativaException ex){
		
		String mensagemUsuario = messageSource.getMessage(Constantes.PESSOA_INIXISTENTE_INATIVA, null, LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = ex.toString();
		List<MensagemErro> erros = Arrays.asList(new  MensagemErro(mensagemUsuario, mensagemDesenvolvedor) );
		
		return ResponseEntity.badRequest().body(erros);
	}
	
	

}
