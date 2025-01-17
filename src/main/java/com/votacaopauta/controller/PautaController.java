package com.votacaopauta.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.votacaopauta.controller.dto.PautaInputDto;
import com.votacaopauta.controller.dto.PautaResultadoDto;
import com.votacaopauta.controller.openapi.PautaControllerOpenApi;
import com.votacaopauta.service.PautaService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping(path = "/v1/pautas", produces = MediaType.APPLICATION_JSON_VALUE)
public class PautaController implements PautaControllerOpenApi {

	@Autowired
	private PautaService service;

	@PostMapping("cadastrar")
	public ResponseEntity<PautaResultadoDto> cadastrarPauta(@RequestBody @Valid PautaInputDto pautaInputDto){
		log.info("Cadastrando pauta...");
		PautaResultadoDto pautaResultadoDto = service.cadastrar(pautaInputDto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(pautaResultadoDto.getId()).toUri();
		log.info("Pauta cadastrada com sucesso!");
		return ResponseEntity.created(uri).body(pautaResultadoDto);
	}

	@GetMapping("consultar/{pautaId}")
	public ResponseEntity<PautaResultadoDto> consultarResultadoDaVotacaoNaPauta(@PathVariable Integer pautaId){
		log.info("Consultando resultado das votações na pauta com ID: {}", pautaId);
		PautaResultadoDto pautaResultadoDto = service.consultarResultadoDaVotacaoNaPauta(pautaId);
		return ResponseEntity.ok(pautaResultadoDto);
	}

	@GetMapping("listar-pautas")
	public ResponseEntity<List<PautaResultadoDto>> listarPautas() {
		log.info("Listando pautas...");
		List<PautaResultadoDto> pautasResultadoDto = service.listarPautas();
		return ResponseEntity.ok(pautasResultadoDto);
	}

}
