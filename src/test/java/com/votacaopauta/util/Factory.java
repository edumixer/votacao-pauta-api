package com.votacaopauta.util;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.votacaopauta.controller.dto.PautaInputDto;
import com.votacaopauta.controller.dto.PautaResultadoDto;
import com.votacaopauta.controller.dto.VotoInputDto;
import com.votacaopauta.model.Pauta;
import com.votacaopauta.model.SessaoVotacao;
import com.votacaopauta.model.Voto;

public class Factory {

	public static SessaoVotacao createSessaoVotacao(){
		Pauta pauta = createPauta();
		Set<Voto> votos = new HashSet<>();
		return new SessaoVotacao(1000, LocalDateTime.now(), LocalDateTime.now().plusSeconds(120), pauta, votos);
	}

	public static Voto createVoto(){
		SessaoVotacao sessaoVotacao = createSessaoVotacao();
		return new Voto("294.987.150-09", "Sim", sessaoVotacao);
	}

	public static Pauta createPauta(){
		return new Pauta(1000, "Pauta teste");
	}

	public static PautaResultadoDto createPautaResultadoDto(){
		Map<String, Long> numeroDeVotos = new HashMap<>();
		numeroDeVotos.put("Sim", 3L);
		numeroDeVotos.put("Não", 2L);
		return new PautaResultadoDto(1000, "Pauta teste", numeroDeVotos, "Pauta aprovada");
	}

	public static PautaInputDto createPautaInputDto(){
		return new PautaInputDto("Pauta teste");
	}

	public static VotoInputDto createVotoInputDto() {
		return new VotoInputDto("273.818.360-33", "Sim");
	}

}
