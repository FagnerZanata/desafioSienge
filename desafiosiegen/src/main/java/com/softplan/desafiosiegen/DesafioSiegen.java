package com.softplan.desafiosiegen;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.softplan.desafiosiegen.model.Servico;
import com.softplan.desafiosiegen.model.eTipoServico;

public class DesafioSiegen {

	public Servico[] processaJson(JsonReader jsonReader) {

		Gson gson = new Gson();

		// Type listType = new TypeToken<ArrayList<Servico>>(){}.getType();

		Servico[] servicos = gson.fromJson(jsonReader, Servico[].class);

		return servicos;
	}
	
	
	

	public static BigDecimal calculaTotalItem(Servico s) {
		BigDecimal total = new BigDecimal(s.getQuantidadeComposicao().replaceAll(",", "."))
				.multiply(new BigDecimal(s.getValorUnidadeComposicao().replaceAll(",", ".")));
		
		System.out.println("retorno calculaTotalItem: " + total);
		
		return total;

	}

	public static BigDecimal calculaTotalComposicao(List<Servico> servicos, Servico servico, BigDecimal total) {

		
		for (Servico s : servicos.stream().filter(x -> x.getCodigoServico().equals(servico.getCodigoComposicao()))
				.collect(Collectors.toList())) {
				System.out.println(servico.getCodigoServico() + " : " + servico.getCodigoComposicao() + " Total: " + total);
			if (s.getTipoServico().equals(eTipoServico.INSUMO)) {
				System.out.println(servico.getCodigoServico() + " : " + servico.getCodigoComposicao() + " calculaTotalItem: " + total);
				total = total.add(calculaTotalItem(s));
						//.multiply(new BigDecimal(s.getQuantidadeComposicao().replaceAll(",", ".")));
				
			} else {
				System.out.println(servico.getCodigoServico() + " : " + s.getCodigoComposicao() + " Antes de: chamar: " + total + " e X : " + servico.getQuantidadeComposicao());
				return total.add( calculaTotalComposicao(servicos, s, total));
						//.multiply(new BigDecimal(servico.getQuantidadeComposicao().replaceAll(",", "."))));
			}
			
			
		}
		
		System.out.println(servico.getCodigoServico() + " : " + servico.getCodigoComposicao() + " Fim: Total: " + total);
		System.out.println(servico.getCodigoServico() + " : " + servico.getCodigoComposicao() + " Fim: X: " + new BigDecimal(servico.getQuantidadeComposicao().replaceAll(",", ".")));
		//BigDecimal totalComposicao = total.multiply(new BigDecimal(servico.getQuantidadeComposicao().replaceAll(",", ".")));
		//System.out.println(servico.getCodigoServico() + " : " + servico.getCodigoComposicao() + " Fim: totalComposicao: " + totalComposicao);

		return total;
		
		
		
		//return calculaComposicaoRec(servicos, codServico, total);

	}

	private static BigDecimal calculaComposicaoRec(List<Servico> servicos, Integer codComposicao, BigDecimal total) {
		for (Servico s : servicos.stream().filter(x -> x.getCodigoServico().equals(codComposicao))
				.collect(Collectors.toList())) {
			if (s.getTipoServico().equals(eTipoServico.INSUMO)) {
				total = total.add(calculaTotalItem(s));
			} else {
				total = calculaComposicaoRec(servicos, s.getCodigoComposicao(), total)
						.multiply(new BigDecimal(s.getQuantidadeComposicao().replaceAll(",", ".")));
			}			
		}
		return total;
	}

}
