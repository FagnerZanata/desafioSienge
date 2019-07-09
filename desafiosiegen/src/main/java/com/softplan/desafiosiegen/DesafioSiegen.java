package com.softplan.desafiosiegen;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.softplan.desafiosiegen.model.Servico;
import com.softplan.desafiosiegen.model.eTipoServico;

public class DesafioSiegen {

	public List<Servico> calculaPorServicoRecursivo(String jsonPathFileName) throws FileNotFoundException {
		List<Servico> servicos = Arrays.asList(processaJson(jsonPathFileName));
		Map<Integer, List<Servico>> resulMapServicos = servicos.stream()
				.collect(Collectors.groupingBy(Servico::getCodigoServico));

		BigDecimal total = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
		
		List<Servico> response = new ArrayList<Servico>();

		BigDecimal totalServico = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
		for (Integer codServico : resulMapServicos.keySet()) {
			total = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
			
			totalServico = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
			
			total = calculaServicoRec(codServico, totalServico, servicos);

			Servico s = servicos.stream().filter(x -> x.getCodigoServico().equals(codServico))
					.collect(Collectors.toList()).get(0);
			
			Servico serv = new Servico(s.getCodigoServico(),s.getDescricaoServico(),s.getUnidadeServico(),total);
			response.add(serv);
		}

		return response;
	}

	private BigDecimal calculaServicoRec(Integer codServico, BigDecimal totalServico, List<Servico> servicos) {
		for (Servico servico : servicos) {
			BigDecimal totalItemServico = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);

			if (servico.getCodigoServico().equals(codServico)) {
				if (servico.getTipoServico() == eTipoServico.INSUMO) {
					totalItemServico = DesafioSiegen.calculaTotalItem(servico);
				} else {
					BigDecimal totalComposicao = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
					return totalServico = ((calculaServicoRec(servico.getCodigoComposicao(), totalComposicao, servicos))
							.multiply(new BigDecimal(servico.getQuantidadeComposicao().replace(",", "."))))
									.add(totalServico);
				}
				totalServico = totalServico.add(totalItemServico);
			}
		}

		return totalServico.setScale(2, RoundingMode.HALF_UP);
	}

	public static Servico[] processaJson(String jsonPathFileName) throws FileNotFoundException {
		Gson gson = new Gson();
		JsonReader reader = new JsonReader(new FileReader(jsonPathFileName));

		Servico[] servicos = gson.fromJson(reader, Servico[].class);
		return servicos;
	}

	public static BigDecimal calculaTotalItem(Servico s) {
		BigDecimal total = new BigDecimal(s.getQuantidadeComposicao().replaceAll(",", "."))
				.multiply(new BigDecimal(s.getValorUnidadeComposicao().replaceAll(",", ".")));

		return total.setScale(2, RoundingMode.HALF_EVEN);

	}

}
