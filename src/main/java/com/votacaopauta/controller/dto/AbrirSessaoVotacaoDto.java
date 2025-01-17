package com.votacaopauta.controller.dto;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class AbrirSessaoVotacaoDto {

    @NotNull(message = "O pautaId é obrigatório")
    private Integer pautaId;

    private Integer tempoParaFechamento;
}