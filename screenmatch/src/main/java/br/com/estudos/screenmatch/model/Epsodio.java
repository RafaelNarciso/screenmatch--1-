package br.com.estudos.screenmatch.model;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Epsodio {
    private Integer temporada;
    private String titulo;
    private Integer numeroEpisodio;
    private Double avaliacao;
    private LocalDate dataLancamento;

    public Epsodio(Integer numeroTemporada, DadosEpisodio dadosEpisodio) {
        this.temporada = numeroTemporada;
        this.titulo = dadosEpisodio.titulo();
        this.numeroEpisodio = dadosEpisodio.numero();

        try {
            this.avaliacao = Double.valueOf(dadosEpisodio.avaliacao());

        } catch (NumberFormatException ex) {

            this.avaliacao = 0.0;
        }

        try {
            this.dataLancamento = LocalDate.parse(dadosEpisodio.dataLancamento());
        } catch (DateTimeParseException ex) {
            this.dataLancamento = null;
        }
    }

    @Override
    public String toString() {
        return "\ntemporada = " + temporada +
                "\n titulo = " + titulo +
                "\n numeroEpisodio = " + numeroEpisodio +
                "\n avaliacao = " + avaliacao +
                "\n dataLancamento = " + dataLancamento;
    }

    /**
     * @return Integer return the temporada
     */
    public Integer getTemporada() {
        return temporada;
    }

    /**
     * @param temporada the temporada to set
     */
    public void setTemporada(Integer temporada) {
        this.temporada = temporada;
    }

    /**
     * @return String return the titulo
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * @param titulo the titulo to set
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     * @return Integer return the numeroEpisodio
     */
    public Integer getNumeroEpisodio() {
        return numeroEpisodio;
    }

    /**
     * @param numeroEpisodio the numeroEpisodio to set
     */
    public void setNumeroEpisodio(Integer numeroEpisodio) {
        this.numeroEpisodio = numeroEpisodio;
    }

    /**
     * @return Double return the avaliacao
     */
    public Double getAvaliacao() {
        return avaliacao;
    }

    /**
     * @param avaliacao the avaliacao to set
     */
    public void setAvaliacao(Double avaliacao) {
        this.avaliacao = avaliacao;
    }

    /**
     * @return LocalDate return the dataLancamento
     */
    public LocalDate getDataLancamento() {
        return dataLancamento;
    }

    /**
     * @param dataLancamento the dataLancamento to set
     */
    public void setDataLancamento(LocalDate dataLancamento) {
        this.dataLancamento = dataLancamento;
    }

}
