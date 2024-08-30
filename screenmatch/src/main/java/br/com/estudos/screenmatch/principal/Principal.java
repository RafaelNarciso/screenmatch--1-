package br.com.estudos.screenmatch.principal;

import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
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
                // System.out.println("\n\t Top 5 episódio ");
                // dadosEpisodios
                // .stream()
                // .filter(e -> !e.avaliacao().equalsIgnoreCase("N/A"))
                // .peek(e -> System.out.println("\nprimeiro filtro (N/A) -> " + e))
                // .sorted(Comparator.comparing(DadosEpisodio::avaliacao).reversed())
                // .peek(e -> System.out.println("\nOrdenação ==>" + e))
                // .limit(10)
                // .map(e -> e.titulo().toUpperCase())
                // .forEach(System.out::println);

                List<Epsodio> epsodios = temporda.stream()
                                .flatMap(t -> t.episodios().stream()
                                                .map(d -> new Epsodio(t.numero(), d)))
                                .collect(Collectors.toList());

                // epsodios.forEach(System.out::println);
                // System.out.println("Informe oum trecho do titulo do episódio :");
                // var trechoTitulo = leitura.nextLine();

                // Optional<Epsodio> episodioBuscado = epsodios.stream()
                // .filter(e ->
                // e.getTitulo().toUpperCase().contains(trechoTitulo.toUpperCase()))
                // .findFirst();

                // if (episodioBuscado.isPresent()) {
                // System.out.println("Episódio encontrado ");
                // System.out.println("Temporada : " + episodioBuscado.get().getTemporada());
                // } else {
                // System.out.println("Episódio não encontrado");
                // }
                // System.out.println("\n A parti de que ano você deseja ver episódios ? ");
                // var ano = leitura.nextInt();
                // leitura.nextLine();

                // LocalDate dataBusca = LocalDate.of(ano, 1, 1);
                // DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd / MM / yyyy");
                // epsodios.stream()
                // .filter(e -> e.getDataLancamento() != null &&
                // e.getDataLancamento().isAfter(dataBusca))
                // .forEach(e -> System.out.println("\nTemporada : " + e.getTemporada() +
                // "\nEpisódio : " + e.getTitulo() +
                // "\nData Lançamento: " + e.getDataLancamento().format(formatador)));

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

                Map<Integer, Double> avalicaoPorTemporada = epsodios.stream()
                                .filter(e -> e.getAvaliacao() > 0.0)
                                .collect(Collectors.groupingBy(Epsodio::getTemporada,
                                                Collectors.averagingDouble(Epsodio::getAvaliacao)));
                System.out.println(avalicaoPorTemporada);

                DoubleSummaryStatistics est = epsodios.stream()
                                .filter(e -> e.getAvaliacao() > 0.0)
                                .collect(Collectors.summarizingDouble(Epsodio::getAvaliacao));

                System.out.println("Media : " + est.getAverage());
                System.out.println("Melhor episodio : " + est.getMax());
                System.out.println("Classificação baixa: " + est.getMin());
                System.out.println("contagem : " + est.getCount());

        }
}
