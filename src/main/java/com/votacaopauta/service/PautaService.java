package com.votacaopauta.service;

import com.votacaopauta.controller.dto.PautaInputDto;
import com.votacaopauta.controller.dto.PautaResultadoDto;
import com.votacaopauta.model.Pauta;

import java.util.List;

public interface PautaService {
	Pauta buscarPauta(Integer id);
	PautaResultadoDto cadastrar(PautaInputDto pautaInputDto);
	PautaResultadoDto consultarResultadoDaVotacaoNaPauta(Integer pautaId);
	List<PautaResultadoDto> listarPautas();
}
