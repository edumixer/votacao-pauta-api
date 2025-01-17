package com.votacaopauta.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.votacaopauta.controller.dto.PautaInputDto;
import com.votacaopauta.controller.dto.PautaResultadoDto;
import com.votacaopauta.model.Pauta;
import com.votacaopauta.model.SessaoVotacao;
import com.votacaopauta.repository.PautaRepository;
import com.votacaopauta.repository.SessaoVotacaoRepository;
import com.votacaopauta.service.PautaService;
import com.votacaopauta.service.exception.EntidadeNaoEncontradaException;


import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class PautaServiceImpl implements PautaService {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private PautaRepository pautaRepository;

	@Autowired
	private SessaoVotacaoRepository sessaoVotacaoRepository;

	@Override
	public Pauta buscarPauta(Integer id){
		Pauta pauta = pautaRepository.findById(id)
			.orElseThrow(() -> new EntidadeNaoEncontradaException("Pauta não encontrada."));
			log.info("Valor da pauta: {}", pauta);
		return pauta;
	}

	@Override
	@Transactional
	public PautaResultadoDto cadastrar(PautaInputDto pautaInputDto) {
		var pauta = modelMapper.map(pautaInputDto, Pauta.class);
		pauta = pautaRepository.save(pauta);
		log.info("Pauta cadastrada com ID: {}", pauta.getId());
		return modelMapper.map(pauta, PautaResultadoDto.class);
	}

	@Override
	public PautaResultadoDto consultarResultadoDaVotacaoNaPauta(Integer pautaId) {
		log.info("Consultando pauta com O ID: {}", pautaId);
		Pauta pauta = buscarPauta(pautaId);
		SessaoVotacao sessaoVotacao = sessaoVotacaoRepository.findByPauta(pauta)
			.orElseThrow(() -> new EntidadeNaoEncontradaException("Sessão de votação da pauta não encontrada."));
		Map<String, Long> numeroDeVotos = sessaoVotacao.obterNumeroDeVotos();
		PautaResultadoDto pautaResultadoDto = modelMapper.map(pauta, PautaResultadoDto.class);
		pautaResultadoDto.setNumeroDeVotos(numeroDeVotos);
		if(LocalDateTime.now().isAfter(sessaoVotacao.getDataHoraFechamento())) {
			calcularResultadoFinal(numeroDeVotos, pautaResultadoDto);
		}
		return pautaResultadoDto;
	}

	private void calcularResultadoFinal(Map<String, Long> numeroDeVotos, PautaResultadoDto pautaResultadoDto){
		long qtDeVotosSim = numeroDeVotos.get("Sim");
		long qtDeVotosNao = numeroDeVotos.get("Não");
		String aprovada = "A pauta " + pautaResultadoDto.getNome() + " foi aprovada!";
		String naoAprovada = "A pauta " + pautaResultadoDto.getNome() + " não foi aprovada!";
		String resultado = qtDeVotosSim > qtDeVotosNao ? aprovada : naoAprovada;
		pautaResultadoDto.setResultadoFinal(resultado);
	}

	@Override
	@Transactional(readOnly = true)
	public List<PautaResultadoDto> listarPautas() { // Change the return type here to List<PautaResultadoDto>
		// Busca todas as pautas no repositório
		List<Pauta> pautas = pautaRepository.findAll();

		// Mapeia cada Pauta para PautaResultadoDto
		return pautas.stream()
				.map(pauta -> {
					PautaResultadoDto pautaResultadoDto = modelMapper.map(pauta, PautaResultadoDto.class);

					// Verifica se existe uma sessão de votação associada à pauta
					SessaoVotacao sessaoVotacao = sessaoVotacaoRepository.findByPauta(pauta)
							.orElse(null);

					if (sessaoVotacao != null) {
						// Obtemos o número de votos
						Map<String, Long> numeroDeVotos = sessaoVotacao.obterNumeroDeVotos();

						// Verificamos se o mapa de votos não é nulo
						if (numeroDeVotos != null) {
							pautaResultadoDto.setNumeroDeVotos(numeroDeVotos);
						}

						// Calculamos o resultado final caso a sessão tenha sido fechada
						if (LocalDateTime.now().isAfter(sessaoVotacao.getDataHoraFechamento())) {
							calcularResultadoFinal(numeroDeVotos, pautaResultadoDto);
						}
					}

					return pautaResultadoDto;
				})
				.collect(Collectors.toList());
	}
}
