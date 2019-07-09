# desafiosiegen

## Desenvolvimento
1. Entendido a necessidade do problema.
2. Iniciado pelo parser do Json usando a lib Gson.
3. Realizado calculo apenas para servicos com insumos, sem composicao.
4. Refatorado codigo para atender a servicos com composicao, infelizmento não completado.
 
 
## Classes
* Classe Servico: Para mapeamento do Json para objecto java. 
* Classe enums: eUnidade e eTipoServico par facilitar desenvolvimento.
* Classe DesafioSiegen: Responsavel por processar e calcular os valores dos servicos.
* Classe de teste DesafioSiegenTest: calculaPorServicoRecursivoTest e calculaPorServicoRecursivo_Valores. Outros testes foram tentativas de fazer a recursividade funcionar.


## Ambiente
* Utilizado Java 11 e IDE Eclipse.
* Compilacao e execucao: mvn clean install

## Bibliotecas
* Lombok para facilitar get/set.
* Gson (google) para processamento do JSON.
* junit 4.12









