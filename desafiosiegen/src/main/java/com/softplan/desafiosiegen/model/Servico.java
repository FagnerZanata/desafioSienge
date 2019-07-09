package com.softplan.desafiosiegen.model;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;

@Getter
public class Servico {

	

	public Servico() {
		
	}
	
	public Servico(Integer codigo, String descricao, eUnidade unidade, BigDecimal valorTotal) {
		this.codigoServico = codigo;
		this.descricaoServico = descricao;
		this.unidadeServico = unidade;
		this.valorTotal = valorTotal;
		
	}
	
	
	public String toString() {
		String builder = "";
		
		builder+=(codigoServico + " ");
		
		builder+=(descricaoServico + " ");
		
		builder+=(unidadeServico + " ");
		
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(2);
		df.setMinimumFractionDigits(0);
		builder+=(df.format( valorTotal));
		
		return builder;
	}

	@SerializedName("c\u00f3digo do servi\u00e7o")
	@Expose
	private Integer codigoServico;
	
	@SerializedName("descri\u00e7\u00e3o")
	@Expose
	private String descricaoServico;
	
	@SerializedName("unidade")
	@Expose
	private eUnidade unidadeServico;
	
	@SerializedName("tipo")
	@Expose
	private eTipoServico tipoServico;
	
	@SerializedName("c\u00f3digo composi\u00e7\u00e3o")
	@Expose
	private Integer codigoComposicao;
	
	@SerializedName("descri\u00e7\u00e3o composi\u00e7\u00e3o")
	@Expose
	private String descricaoComposicao;
	
	@SerializedName("unidade composi\u00e7\u00e3o")
	@Expose	
	private eUnidade unidadeComposicao;
		
	@SerializedName("quantidade composi\u00e7\u00e3o")
	@Expose
	private String quantidadeComposicao;
	
	@SerializedName("valor unit\u00e1rio")
	@Expose
	private String valorUnidadeComposicao;

	private BigDecimal valorTotal;
	
}
