package br.com.financas.api.event.listener;

import java.net.URI;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.financas.api.event.RecursoCriadoEvent;

@Component
public class RecursoCriadoListener implements ApplicationListener<RecursoCriadoEvent>{
	
	@Override
	public void onApplicationEvent(RecursoCriadoEvent event) {
		
		HttpServletResponse response = event.getResponse();
		Long codigo = event.getCodigo();
		
		// adiciona no headerLocation
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
				.buildAndExpand(codigo).toUri();
		response.setHeader("Location",uri.toASCIIString());
	}

}
