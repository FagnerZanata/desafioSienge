# desafiosiegen

## Desenvolvimento

1. Primeiramente entendido o sistema original e esclarecido a nova necessidade.
2. Criado teste unitario para o sistema original com cenario de singular e plural.
3. Refatorado o codigo mantendo o mesmo funcioando do sistema original, testes unitarios garatiram as alteracoes.
4. Definida uma nova classe para usar na entrada do sistema, notaFiscalSR.
5. Aplicada a mudanca de entrada de dados atráves de TDD.
6. Entrada do sistema continua como Lista, mas observando qual o tipo de dado de entrada. Escolhida essa abordagem para manter o funcionando original conforme pedido no exercicio.
7. Aplicado refatoracao conforme a necessidades do desenvolvimento, como por exemplo as funcoes "isUltimaNota" e "isPenultimaNota" afim de manter o metodo "retornaInformacoesNotas" mais limpo.
 
 
## Classes
* Classe Servico: Para mapeamento do Json para objecto java. 
* Classe enums: eUnidade e eTipoServico par facilitar desenvolvimento.
* Classe DesafioSiegen: Responsavel por processar e calcular os valores dos servicos.



## Ambiente
* Utilizado Java 11 e IDE Eclipse.
* Compilacao e execucao: mvn clean install

## Bibliotecas
* Lombok para facilitar get/set.
* Gson (google) para processamento do JSON.
* junit 4.12









