package com.softplan.desafiosiegen.model;

import java.math.BigDecimal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class Servico {

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
