package br.com.financas.api.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.financas.api.dto.PessoaDTO;
import br.com.financas.api.event.RecursoCriadoEvent;
import br.com.financas.api.repository.PessoaRepository;
import br.com.financas.api.service.PessoaService;


@RestController
@RequestMapping("/api/pessoa")
@Api(value = "API REST Pessoa")
@CrossOrigin(origins = "*") // Libera qualquer dominio para acessar a api
public class PessoaController {
	
	@Autowired
	private PessoaService service;
	
	@Autowired
	private PessoaRepository repository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	
	@GetMapping("/{id}")
	@ApiOperation(value = "Retorna uma Pessoa pelo ID")
	public ResponseEntity<PessoaDTO> buscarPessoaPorId(@PathVariable  Long id) {
		PessoaDTO entity = service.buscarPessoaPorId(id);
		return entity != null ? ResponseEntity.ok(entity) : ResponseEntity.noContent().build();
	}
	
	@GetMapping
	@ApiOperation(value = "Retorna todas as Pessoas cadastradas")
	public  ResponseEntity<List<PessoaDTO>> obterPessoas(){
		List<PessoaDTO> pessoaDTOs = service.obterPessoas();
		return pessoaDTOs != null ? ResponseEntity.ok(pessoaDTOs) : ResponseEntity.noContent().build();	
	}
	
	
	@PostMapping
	@ApiOperation(value = "Salva uma nova pessoa")
	public ResponseEntity<PessoaDTO> salvarNovaPessoa(@Valid @RequestBody PessoaDTO pessoaDTO, HttpServletResponse response){
		
		PessoaDTO pessoaSalvaDto = service.salvarNovaPessoa(pessoaDTO);
		
		// publica evento e adiciona o código da pessoa no headerLocation
		publisher.publishEvent(new RecursoCriadoEvent(this, response, pessoaSalvaDto.getId()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(pessoaSalvaDto);
	}
	
	@DeleteMapping("/{id}")
	@ApiOperation(value = "Deleta uma Pessoa pelo ID")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletarPessoa(@PathVariable Long id) {
		repository.deleteById(id);
	}
	
	@PutMapping("/{id}")
	@ApiOperation(value = "Atualiza dados da Pessoa pelo ID")
	public ResponseEntity<PessoaDTO> atualizarPessoa(@Valid @PathVariable Long id, @RequestBody PessoaDTO pessoa){
		PessoaDTO pessoaDTO = service.atualizarPessoa(id, pessoa);
		return ResponseEntity.ok(pessoaDTO);
	}
	
	@PutMapping("/status/{id}")
	@ApiOperation(value = "Atualiza o Status da Pessos, busca pelo código")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizarStatus(@PathVariable Long id, @RequestBody Boolean status) {
		service.atualizarStatus(id, status);
	}

}
