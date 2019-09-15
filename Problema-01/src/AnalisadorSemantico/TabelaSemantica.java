package AnalisadorSemantico;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;

/*Classe responsavel por armazenar as tabelas semanticas
 * 
 * */

public class TabelaSemantica {

	private int countParametro;

	HashMap<String, AtributosConstVar> tabelaConstVar;
	HashMap<String, AtributosMetodos> tabelaMetodos;

	public TabelaSemantica() {
		this.countParametro = -1;
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
			String segundoIndice, boolean utilizado, String metodoDaVariavel) {

		if (tabelaConstVar.containsKey(cadeia) == false)
			tabelaConstVar.put(cadeia, new AtributosConstVar(token, cat, tipo, valor, primeiroIndice, segundoIndice,
					utilizado, metodoDaVariavel));
		else {
			String metodo = tabelaConstVar.get(cadeia).getmeetodoDaVariavel();
			if (metodo.equals(metodoDaVariavel))
				System.out.println("Variavel ja declarada para esse escopo");
			else {
				tabelaConstVar.remove(cadeia);
				tabelaConstVar.put(cadeia, new AtributosConstVar(token, cat, tipo, valor, primeiroIndice, segundoIndice,
						utilizado, metodoDaVariavel)); 
				
			}
		}
	}

	/*
	 * Inserir informacoes na tabela semantica dos metodos metodo soma(inteiro a,
	 * real b): vazio nomeMetodo = soma tipoRetorno = vazio listaPrametros = [a, b]
	 * tipoParametro = [inteiro, real] esse metodo retorna false se ja existir uma
	 * chave
	 */
	public boolean inserirTabelaMetodos(String nomeMetodo, String tipoRetorno, LinkedList<String> listaParametros,
			LinkedList<String> tipoParametros) {
		if (tabelaMetodos.containsKey(nomeMetodo) == false) {
			tabelaMetodos.put(nomeMetodo, new AtributosMetodos(tipoRetorno, listaParametros, tipoParametros, false));
			return true;
		}
		return false;
	}
	
	/*
	 * Verifica se eh possivel incrementar uma determinada variavel
	 * */
	public boolean podeIncrementar(String nomeVariavel, String nomeMetodo) {

		if (tabelaConstVar.containsKey(nomeVariavel)) {
			String tipo = tabelaConstVar.get(nomeVariavel).getTipo();
			String cat = tabelaConstVar.get(nomeVariavel).getCategoria();
			String nomeMet = tabelaConstVar.get(nomeVariavel).getmeetodoDaVariavel();
			
			if ((tipo.equals("real") || tipo.equals("inteiro")) && cat.equals("variavel") && nomeMet.equals(nomeMetodo))
				return true;
			else 
				return false;
		}
		else
			System.out.println("nao encontrou a chave");
		return false;
	}
	
	
	/*
	 * Verifica se o nome do metodo existe
	 */
	public boolean metodoExiste(String nomeMetodo) {

		if (this.tabelaMetodos.containsKey(nomeMetodo) == false) {
			System.out.println("Essa metodo ainda nao foi declarado!");
			return false;
		}
		return true;
	}

	/*
	 * Verifica se os atribuitos dos metodos estao corretos metodo soma(inteiro a)
	 * soma (a). a eh inteiro tambem?
	 */

	public boolean checaAtributoChamadaMetodo(String nomeAtributo, String tipoAtributo, String nomeMetodo) {

		this.countParametro++;

		if (this.tabelaConstVar.containsKey(nomeAtributo)) {
			String s = this.tabelaConstVar.get(nomeAtributo).getmeetodoDaVariavel();
			String cat = this.tabelaConstVar.get(nomeAtributo).getCategoria();

			if (s.equals(nomeMetodo) || cat.equals("constante")) {
				AtributosConstVar acv = this.tabelaConstVar.get(nomeAtributo);
				AtributosMetodos am = this.tabelaMetodos.get(nomeMetodo);

				try {
					if (acv.getTipo().equals(am.tipoParametro(this.countParametro))) {
						return true;
					} else {
						// error
						System.out.println("Error! Incompativeis parametros");
					}
				} catch (Exception e) {
					System.out.println("error! Parametros em excesso");
				}

			} else {
				// error
				System.out.println("Error! Variavel nao faz parte do metodo eu eh uma constante.");
				return false;
			}
		} else if (tipoAtributo.equals("Numero") || tipoAtributo.equals("verdadeiro") || tipoAtributo.equals("falso")
				|| tipoAtributo.equals("Cadeia de Caractere")) {
			AtributosMetodos am = this.tabelaMetodos.get(nomeMetodo);
			tipoAtributo = this.converteVar(tipoAtributo, nomeAtributo);
			if (tipoAtributo.equals(am.tipoParametro(this.countParametro))) {
				return true;
			} else {
				// error
				System.out.println("Error! Tipos incompativeis. Entrada direta>> " + tipoAtributo);
				return false;
			}

		}

		return true;
	}

	public void zerarCountParametro() {
		this.countParametro = -1;
	}

	/* Verifica se a variavel ja esta declarada dentro do metodo que ela esta */
	public boolean varConstDeclaradaMetodo(String nomeMetodo, String nomeParametro) {
		System.out.println(nomeMetodo+" e "+nomeParametro);
		return tabelaMetodos.get(nomeMetodo).containsVar(nomeParametro);
	}

	/* Verifica se uma determinada constante ou variavel existe na tabela */
	public boolean temConstVar(String cadeia) {
		if (tabelaConstVar.containsKey(cadeia)) {
			String s = tabelaConstVar.get(cadeia).getCategoria();
			if (s.equals("constante"))
				return true;
			return false;
		} else
			return false;
	}
	
	/* Verifica se uma determinada variavel existe na tabela */
	public boolean temVar(String cadeia) {
		if (tabelaConstVar.containsKey(cadeia)) {
			String s = tabelaConstVar.get(cadeia).getCategoria();
			if (s.equals("variavel"))
				return true;
			return false;
		} else
			return false;
	}
	
	public boolean validaTipoVariavel(String cadeia, String tipo) {
		if(tabelaConstVar.containsKey(cadeia)) {
			if(tabelaConstVar.get(cadeia).getTipo().equals(tipo)) {
				return true;
			} else return false;
		} else return false;
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
	 */
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

	private String converteVar(String tipoAtributo, String valorAtributo) {
		if (tipoAtributo.equals("Cadeia de Caractere"))
			return "texto";
		else if (tipoAtributo.equals("verdadeiro") || tipoAtributo.equals("falso"))
			return "booleano";
		else if (tipoAtributo.equals("Numero")) {
			if (valorAtributo.contains("."))
				return "real";
			else
				return "inteiro";
		}
		return "error";
	}

}
