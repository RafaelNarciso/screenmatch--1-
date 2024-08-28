package br.com.estudos.screenmatch;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.estudos.screenmatch.model.DadosEpisodio;
import br.com.estudos.screenmatch.model.DadosSerie;
import br.com.estudos.screenmatch.model.DadosTemporda;
import br.com.estudos.screenmatch.service.ConsumoApi;
import br.com.estudos.screenmatch.service.ConverteDados;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		var consumoApi = new ConsumoApi();
		var json = consumoApi.obterDados("https://www.omdbapi.com/?t=gilmore+girls&apikey=d0bc9998");
		System.out.println(json);
		ConverteDados conversor = new ConverteDados();
		DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
		System.out.println(dados);
		System.out.println("\n\n");
		json = consumoApi.obterDados("https://www.omdbapi.com/?t=gilmore+girls&season=1&episode=2&apikey=d0bc9998");
		DadosEpisodio dadosEpisodio = conversor.obterDados(json, DadosEpisodio.class);

		System.out.println(dadosEpisodio);

		System.out.println("\n\n");

		List<DadosTemporda> tempordas = new ArrayList<>();

		for (int i = 1; i <= dados.totalTemporadas(); i++) {
			json = consumoApi
					.obterDados("https://www.omdbapi.com/?t=gilmore+girls&season=" + i + "&apikey=d0bc9998");
			DadosTemporda dadosTemporda = conversor.obterDados(json, DadosTemporda.class);
			tempordas.add(dadosTemporda);
		}
		tempordas.forEach(System.out::println);

	}

}
