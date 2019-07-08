package com.softplan.desafiosiegen;

import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.junit.Test;

import com.google.gson.stream.JsonReader;
import com.softplan.desafiosiegen.model.Servico;
import com.softplan.desafiosiegen.model.eTipoServico;

/**
 * Unit test for simple App.
 */
public class DesafioSiegenTest {

	@Test
	public void DesafioSiegenTest_ProcessaJsonTest() throws FileNotFoundException {

		DesafioSiegen ds = new DesafioSiegen();

		String jsonfilename = "C:\\Users\\zanata.fagner\\Documents\\GitHub\\desafioSienge\\docs\\dados-entrada-servicos-composicoes.json";

		JsonReader reader = new JsonReader(new FileReader(jsonfilename));

		Servico[] servicos = ds.processaJson(reader);

		assertTrue(servicos.length > 0);

	}

	
	
	@Test
	public void DesafioSiegenTest_CalculoTotalServico() throws FileNotFoundException {

		DesafioSiegen ds = new DesafioSiegen();

		String jsonfilename = "C:\\Users\\zanata.fagner\\Documents\\GitHub\\desafioSienge\\docs\\dados-entrada-servicos-composicoes.json";

		JsonReader reader = new JsonReader(new FileReader(jsonfilename));

		List<Servico> servicos = Arrays.asList(ds.processaJson(reader));

		Map<Integer, List<Servico>> resulMap = servicos.stream()
				.collect(Collectors.groupingBy(Servico::getCodigoServico));

		for (Integer cod : resulMap.keySet()) {
			List<Servico> itens = servicos.stream().filter(x -> x.getCodigoServico().equals(cod)).collect(Collectors.toList());
			
			//System.out.println(cod + " : Quantidade Itens:" + itens.size());

			BigDecimal totalServico = BigDecimal.ZERO;
			for (Servico service : itens) {
				//System.out.println("CodigoComposicao: " + srvComposicao.getCodigoComposicao() + " " + srvComposicao.getDescricaoComposicao());
				BigDecimal totalItem = BigDecimal.ZERO;

				if (service.getTipoServico().equals(eTipoServico.COMPOSICAO)) {
					
					System.out.println("Calculo composicao: " + service.getCodigoComposicao());
					totalItem = DesafioSiegen.calculaTotalComposicao(servicos,service, totalItem);
					System.out.println(service.getCodigoServico() + " : " + service.getCodigoComposicao() + " : TotalComposicao: " + totalItem);
					System.out.println(service.getCodigoServico() + " : " + service.getCodigoComposicao() + " : TotalItem " + totalItem + " X " + service.getQuantidadeComposicao());
					totalItem = totalItem.multiply(new BigDecimal(service.getQuantidadeComposicao().replaceAll(",", ".")));
					System.out.println(service.getCodigoServico() + " : " + service.getCodigoComposicao() + " : totalMultiplicado : " + totalItem);
				} else {
					totalItem = DesafioSiegen.calculaTotalItem(service);
					System.out.println(service.getCodigoServico() + " : " + service.getCodigoComposicao() + " : item: " + totalItem);
				}
				

				totalServico = totalServico.add(totalItem);
				

			}
			
			System.out.println(cod + " : total: " + totalServico);
			System.out.println("\n\n");

			assertTrue(true);

		}
	}

	static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
		Map<Object, Boolean> seen = new ConcurrentHashMap<>();
		return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
	}

}
