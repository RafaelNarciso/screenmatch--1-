package br.com.estudos.screenmatch.principal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import br.com.estudos.screenmatch.model.DadosEpisodio;
import br.com.estudos.screenmatch.model.DadosSerie;
import br.com.estudos.screenmatch.model.DadosTemporda;
import br.com.estudos.screenmatch.model.Epsodio;
import br.com.estudos.screenmatch.service.ConsumoApi;
import br.com.estudos.screenmatch.service.ConverteDados;

public class Principal {
    private Scanner leitura = new Scanner(System.in);
    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();

    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=d0bc9998";

    public void exibeMenu() {
        System.out.println("Digite o nome da Série para busca: ");
        var nomeSerie = leitura.nextLine();

        var json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);

        DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
        System.out.println(dados);

        List<DadosTemporda> temporda = new ArrayList<>();

        for (int i = 1; i <= dados.totalTemporadas(); i++) {
            json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + "&season=" + i + API_KEY);
            DadosTemporda dadosTemporda = conversor.obterDados(json, DadosTemporda.class);
            temporda.add(dadosTemporda);
        }

        temporda.forEach(System.out::println);
        temporda.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));

        List<DadosEpisodio> dadosEpisodios = temporda.stream()
                .flatMap(t -> t.episodios().stream())
                .collect(Collectors.toList());
        // .toList(); quando se usa toList nao e possivel alterar e add

        // Para os top 5 episodio
        System.out.println("\n\t Top 5 episódio ");
        dadosEpisodios
                .stream()
                .filter(e -> !e.avaliacao().equalsIgnoreCase("N/A"))
                .sorted(Comparator.comparing(DadosEpisodio::avaliacao).reversed())
                .limit(5)
                .forEach(System.out::println);

        List<Epsodio> epsodios = temporda.stream()
                .flatMap(t -> t.episodios().stream()
                        .map(d -> new Epsodio(t.numero(), d)))
                .collect(Collectors.toList());

        epsodios.forEach(System.out::println);

        /*
         * List<String> nome = Arrays.asList("Rafa", "ZAZA", "Bel");
         * 
         * nome.stream()
         * .sorted()
         * .limit(2)
         * .filter(n -> n.startsWith("B"))
         * .map(n -> n.toUpperCase())
         * .forEach(System.out::println);
         */
    }
}
