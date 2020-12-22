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
import org.springframework.web.bind.annotation.*;

import br.com.financas.api.dto.CategoriaDTO;
import br.com.financas.api.event.RecursoCriadoEvent;
import br.com.financas.api.service.CategoriaService;


@RestController
@RequestMapping("/api/categorias")
@Api(value = "API REST Categoria")
@CrossOrigin(origins = "*")
public class CategoriaController {
	
	@Autowired
	private CategoriaService categoriaService;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@GetMapping
	@ApiOperation(value = "Retorna todas as categorias")
	public  ResponseEntity<List<CategoriaDTO>> obterTodasCategorias(){
		List<CategoriaDTO> categoriaDTOs = categoriaService.obterTodasCategorias();
		return categoriaDTOs != null ? ResponseEntity.ok(categoriaDTOs) : ResponseEntity.noContent().build();	
	}
	
	
	@GetMapping("/{id}")
	@ApiOperation(value = "Retorna uma categoria por ID")
	public ResponseEntity<CategoriaDTO> bucarCategoriaPorId(@PathVariable Long id){
		
		CategoriaDTO categoriaDTO = categoriaService.bucarCategoriaPorId(id);
		
		return categoriaDTO != null ? ResponseEntity.ok(categoriaDTO) : ResponseEntity.noContent().build();
		
	}
	
	@PostMapping
	@ApiOperation(value = "Grava uma nova categoria")
	public ResponseEntity<CategoriaDTO> gravarNovaCategoria(@Valid @RequestBody CategoriaDTO categoriaDTO, HttpServletResponse response){
		
		CategoriaDTO categoriaSalvaDto = categoriaService.gravarNovaCategoria(categoriaDTO);
		
		publisher.publishEvent(new RecursoCriadoEvent(this, response, categoriaSalvaDto.getId()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(categoriaSalvaDto);
	}
	
	@PutMapping("/{id}")
	@ApiOperation(value = "Atualiza uma categoria existente pelo ID")
	public ResponseEntity<CategoriaDTO> atualizarCategoria(@PathVariable Long id, @RequestBody CategoriaDTO categoriaDTO){
		CategoriaDTO dto = categoriaService.atualizarCategoria(id, categoriaDTO);
		return ResponseEntity.ok(dto);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ApiOperation(value = "Deleta uma categoria pelo ID")
	public void deletarCategoria(@PathVariable Long id) {
		categoriaService.deletarCategoria(id);
	}
	

}
