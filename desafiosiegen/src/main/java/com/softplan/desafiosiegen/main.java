package com.softplan.desafiosiegen;

import java.io.FileNotFoundException;
import java.util.List;

import com.softplan.desafiosiegen.model.Servico;

/**
 * Hello world!
 *
 */
public class main 
{
    public static void main( String[] args )
    {
        DesafioSiegen ds = new DesafioSiegen();
        String jsonPathFileName = System.getProperty("user.dir") + "\\dados-entrada-servicos-composicoes.json";

        try {
        	List<Servico>descricaoServicos = ds.calculaPorServicoRecursivo(jsonPathFileName);        
        
        	for(Servico servico : descricaoServicos) {
        		System.out.println(servico.toString());
        	}
        }catch (FileNotFoundException e) {
        	System.out.println(e);
		}
        
    }
}
