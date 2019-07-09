package com.softplan.desafiosiegen;

import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.Test;

import com.softplan.desafiosiegen.model.Servico;
import com.softplan.desafiosiegen.model.eTipoServico;

/**
 * Unit test for simple App.
 */
public class DesafioSiegenTest {

	@Test
	public void calculaPorServicoRecursivoTest() throws FileNotFoundException {

		DesafioSiegen ds = new DesafioSiegen();

		String jsonfilename = "C:\\Users\\zanata.fagner\\Documents\\GitHub\\desafioSienge\\docs\\dados-entrada-servicos-composicoes.json";
		
		List<Servico> out = ds.calculaPorServicoRecursivo(jsonfilename);

		assertTrue(out.size() > 0);
	}
		
	@Test
	public void calculaPorServicoRecursivo_Valores() throws FileNotFoundException {

		DesafioSiegen ds = new DesafioSiegen();

		String jsonfilename = "C:\\Users\\zanata.fagner\\Documents\\GitHub\\desafioSienge\\docs\\dados-entrada-servicos-composicoes.json";
		
		List<Servico> out = ds.calculaPorServicoRecursivo(jsonfilename);
		Map<Integer, BigDecimal> esperados = new HashMap<Integer, BigDecimal>();
		esperados.put(98561,new BigDecimal(28.73));
		esperados.put(87286,new BigDecimal(289.97));
		esperados.put(94793,new BigDecimal(128.60));
		esperados.put(88831,new BigDecimal(0.22));
		esperados.put(88830,new BigDecimal(1.25));
		
		for(Servico s : out) {
			assertTrue(esperados.get(s.getCodigoServico()).equals(s.getValorTotal()));
			
		}
		
	}
	

	@Test
	public void DesafioSiegen_CalculaPorComposicaoRec() throws FileNotFoundException {
				
		String jsonfilename = "C:\\Users\\zanata.fagner\\Documents\\GitHub\\desafioSienge\\docs\\dados-entrada-servicos-composicoes.json";

		List<Servico> servicos = Arrays.asList(DesafioSiegen.processaJson(jsonfilename));

		Map<Integer, List<Servico>> resulMapServicos = servicos.stream()
				.collect(Collectors.groupingBy(Servico::getCodigoServico));

		Map<Integer, List<Servico>> resulMapComposicoes = servicos.stream()
				.collect(Collectors.groupingBy(Servico::getCodigoComposicao));

		BigDecimal calculaServicoComposicaoTotal = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
		
		BigDecimal totalServico = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
		for (Integer codServico : resulMapServicos.keySet()) {
			calculaServicoComposicaoTotal = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
			totalServico = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);		    
		    calculaServicoComposicaoTotal = calculaComposicaoRec(codServico, totalServico, servicos);
		    Servico s = servicos.stream().filter(x->x.getCodigoServico().equals(codServico)).collect(Collectors.toList()).get(0);
			/* System.out.println(s.toString(calculaServicoComposicaoTotal)); */
		}


		assertTrue(true);

	}
	
	private BigDecimal calculaComposicaoRec(Integer codServico, BigDecimal totalServico,
			List<Servico> listaComposicoes) {				
		
		for (Servico servicoComposicao : listaComposicoes) {
			BigDecimal totalItemServico = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
			if(servicoComposicao.getCodigoServico().equals(codServico)) {				
				
				if(servicoComposicao.getTipoServico() == eTipoServico.INSUMO) {					
					totalItemServico = DesafioSiegen.calculaTotalItem(servicoComposicao);

				}else {
					BigDecimal totalComposicao = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);					
				
					return totalServico.add(calculaComposicaoRec(servicoComposicao.getCodigoComposicao(), totalComposicao, listaComposicoes));
				}				
				totalServico = totalServico.add(totalItemServico);

			}
		}
		
		return totalServico.setScale(2, RoundingMode.HALF_UP);
	}
	
	@Test
	public void DesafioSiegen_CalculoTotalServico() throws FileNotFoundException {

		String jsonfilename = "C:\\Users\\zanata.fagner\\Documents\\GitHub\\desafioSienge\\docs\\dados-entrada-servicos-composicoes.json";

		List<Servico> servicos = Arrays.asList(DesafioSiegen.processaJson(jsonfilename));

		Map<Integer, List<Servico>> resulMap = servicos.stream()
				.collect(Collectors.groupingBy(Servico::getCodigoServico));

		for (Integer cod : resulMap.keySet()) {
			List<Servico> itens = servicos.stream().filter(x -> x.getCodigoServico().equals(cod))
					.collect(Collectors.toList());

			// System.out.println(cod + " : Quantidade Itens:" + itens.size());

			BigDecimal totalServico = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
			for (Servico service : itens) {
				// System.out.println("CodigoComposicao: " + srvComposicao.getCodigoComposicao()
				// + " " + srvComposicao.getDescricaoComposicao());
				BigDecimal totalItem = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);

				if (service.getTipoServico().equals(eTipoServico.COMPOSICAO)) {

					totalItem = calculaTotalComposicaoRec(servicos, service, totalItem);
					
					totalItem = totalItem.multiply(new BigDecimal(service.getQuantidadeComposicao().replaceAll(",", ".")));
				} else {
					totalItem = DesafioSiegen.calculaTotalItem(service);
				}
				totalServico = totalServico.add(totalItem);
			}


			/*
			 * Servico s =
			 * servicos.stream().filter(x->x.getCodigoServico().equals(cod)).collect(
			 * Collectors.toList()).get(0); System.out.println(s.toString(totalServico));
			 */

			assertTrue(true);

		}
	}
	
public static BigDecimal calculaTotalComposicaoRec(List<Servico> servicos, Servico servico, BigDecimal total) {
		
		for (Servico s : servicos.stream().filter(x -> x.getCodigoServico().equals(servico.getCodigoComposicao()))
				.collect(Collectors.toList())) {
				//System.out.println(servico.getCodigoServico() + " : " + servico.getCodigoComposicao() + " Total: " + total);
			if (s.getTipoServico().equals(eTipoServico.INSUMO)) {
				//System.out.println(servico.getCodigoServico() + " : " + servico.getCodigoComposicao() + " calculaTotalItem: " + total);
				total = total.add(DesafioSiegen.calculaTotalItem(s));
						//.multiply(new BigDecimal(s.getQuantidadeComposicao().replaceAll(",", ".")));
				
			} else {
				//System.out.println(servico.getCodigoServico() + " : " + s.getCodigoComposicao() + " Antes de: chamar: " + total + " e X : " + servico.getQuantidadeComposicao());
				return total.add( calculaTotalComposicaoRec(servicos, s, total));
						//.multiply(new BigDecimal(servico.getQuantidadeComposicao().replaceAll(",", "."))));
			}
		}
		
		
		//BigDecimal totalComposicao = total.multiply(new BigDecimal(servico.getQuantidadeComposicao().replaceAll(",", ".")));
		//System.out.println(servico.getCodigoServico() + " : " + servico.getCodigoComposicao() + " Fim: totalComposicao: " + totalComposicao);

		return total;
	}


}
