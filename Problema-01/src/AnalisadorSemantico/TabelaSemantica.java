package AnalisadorSemantico;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;

/*Classe responsavel por armazenar as tabelas semanticas
 * 
 * */

public class TabelaSemantica {

	HashMap<String, AtributosConstVar> tabelaConstVar;
	HashMap<String, AtributosMetodos> tabelaMetodos;

	public TabelaSemantica() {

		tabelaConstVar = new HashMap<String, AtributosConstVar>();
		tabelaMetodos = new HashMap<String, AtributosMetodos>();
	}

	/*
	 * Tabela semantica que guarda informacoes das constantes e variaveis Essa
	 * tabela esta estrutura da forma de HashMap com a cadeia <nome da
	 * variavel/constante> e uma classe que eh utilizada apenas para armazenar as
	 * string
	 */
	public void inserirTabela(String cadeia, String token, String cat, String tipo, String valor, String primeiroIndice,
			String segundoIndice, boolean utilizado,String metodoDaVariavel) {

		if (tabelaConstVar.containsKey(cadeia) == false)
			tabelaConstVar.put(cadeia,
					new AtributosConstVar(token, cat, tipo, valor, primeiroIndice, segundoIndice, utilizado, metodoDaVariavel));

	}

	/*
	 * Inserir informacoes na tabela semantica dos metodos metodo soma(inteiro a,
	 * real b): vazio nomeMetodo = soma tipoRetorno = vazio listaPrametros = [a, b]
	 * tipoParametro = [inteiro, real]
	 * esse metodo retorna false se ja existir uma chave
	 */
	public boolean inserirTabelaMetodos(String nomeMetodo, String tipoRetorno, LinkedList<String> listaParametros, LinkedList<String> tipoParametros) {
		if (tabelaMetodos.containsKey(nomeMetodo) == false) {
			tabelaMetodos.put(nomeMetodo, new AtributosMetodos(tipoRetorno, listaParametros, tipoParametros, false));
			return true;
		}
		return false;
	}
	
	
	/*
	 *Verifica se o nome do metodo existe 
	 * */
	public boolean metodoExiste(String nomeMetodo) {
		
		if (this.tabelaMetodos.containsKey(nomeMetodo) == false) {
			System.out.println("Essa metodo ainda nao foi declarado!");
			return false;
		}
		return true;
	}
	
	/*
	 * Verifica se os atribuitos dos metodos estao corretos
	 * metodo soma(inteiro a)
	 * soma (a). a eh inteiro tambem?
	 * */
	
	public boolean checaAtributoChamadaMetodo() {
		return true;
	}
	
	/*Verifica se a variavel ja esta declarada dentro do metodo que ela esta*/
	public boolean varConstDeclaradaMetodo(String nomeMetodo, String nomeParametro) {
		return tabelaMetodos.get(nomeMetodo).containsVar(nomeParametro);
	}
	
	
	/* Verifica se uma determinada constante ou variavel existe na tabela */
	public boolean temConstVar(String cadeia) {
		if (tabelaConstVar.containsKey(cadeia))
			return true;
		else
			return false;
	}

	/*
	 * Verifica se o valor dentro do vetor [->2<-] eh inteiro ou se eh uma constante
	 * inteira
	 */
	public boolean validaIdentificadorVetor(String cadeia) {
		// System.out.println(tabelaConstVar.get(cadeia).getTipo());
		if (tabelaConstVar.containsKey(cadeia)) {
			if (tabelaConstVar.get(cadeia).getTipo().equals("inteiro"))
				return true;
		}
		return false;
	}
	
	
	/*
	 * Apenas imprime a tabela para a verificacao visual da tabela
	 * */
	public void printTabela() {
		System.out.println(tabelaConstVar.keySet());
		Set<String> a = tabelaConstVar.keySet();
		for (String chave : a) {
			if (chave != null) {
				System.out.println(chave);
				tabelaConstVar.get(chave).imprimirString();
			}
		}
	}

}
