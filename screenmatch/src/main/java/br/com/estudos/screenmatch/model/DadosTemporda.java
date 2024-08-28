package br.com.estudos.screenmatch.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosTemporda(
        @JsonAlias("Season") Integer numero,
        @JsonAlias("Episodes") List<DadosEpisodio> episodios) {

}
