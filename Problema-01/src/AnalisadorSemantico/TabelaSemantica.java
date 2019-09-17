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
	LinkedList<AtributosMetodos> tabelaMetodos;

	public TabelaSemantica() {
		this.countParametro = -1;
		tabelaConstVar = new HashMap<String, AtributosConstVar>();
		tabelaMetodos = new LinkedList<AtributosMetodos>();
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

		int length = tabelaMetodos.size();

		AtributosMetodos novaEntrada = new AtributosMetodos(nomeMetodo, tipoRetorno, listaParametros, tipoParametros,
				false);

		for (int i = 0; i < length; i++) {
			AtributosMetodos entradaGuardada = tabelaMetodos.get(i);

			if (entradaGuardada.getNomeMetodo().equals(nomeMetodo)) {
				if (sobreEscrita(novaEntrada, entradaGuardada)) {
					return false; // nao eh possivel add sobrescrita de metodos
				} else {
					tabelaMetodos.add(novaEntrada);
					return true;
				}
			}
		}
		tabelaMetodos.add(novaEntrada);
		return true;

	}

	/*
	 * Verifica a sobrescrita de metodos se tipo retorna true entao tem sobreescrita
	 */
	private boolean sobreEscrita(AtributosMetodos novaEntrada, AtributosMetodos entradaGuardada) {

		if (novaEntrada.getNomeMetodo().equals(entradaGuardada.getNomeMetodo())) {
			if (novaEntrada.getTipoRetorno().equals(entradaGuardada.getTipoRetorno())) {
				LinkedList<String> eTipo = novaEntrada.getTipoParametros();
				LinkedList<String> gTipo = entradaGuardada.getTipoParametros();

				int lengtheTipo = eTipo.size();
				int lengthgTipo = gTipo.size();
				
				if (lengtheTipo != lengthgTipo )
					return false;
				
				for (int i = 0; i < lengtheTipo; i++) {
					if (eTipo.get(i).equals(gTipo.get(i)) == false)
						return false; // tipos diferentes
				}
			}
		}

		return true;
	}

	/*
	 * Verifica se eh possivel incrementar uma determinada variavel
	 */
	public boolean podeIncrementar(String nomeVariavel, String nomeMetodo) {

		if (tabelaConstVar.containsKey(nomeVariavel)) {
			String tipo = tabelaConstVar.get(nomeVariavel).getTipo();
			String cat = tabelaConstVar.get(nomeVariavel).getCategoria();
			String nomeMet = tabelaConstVar.get(nomeVariavel).getmeetodoDaVariavel();

			if ((tipo.equals("real") || tipo.equals("inteiro")) && cat.equals("variavel") && nomeMet.equals(nomeMetodo))
				return true;
			else
				return false;
		} else
			System.out.println("nao encontrou a chave");
		return false;
	}

	/*
	 * Verifica se o nome do metodo existe
	 */
	public boolean metodoExiste(String nomeMetodo) {
		int length = tabelaMetodos.size();

		for (int i = 0; i < length; i++) {
			if (this.tabelaMetodos.get(i).getNomeMetodo().equals(nomeMetodo))
				return true;
		}
		return true;
	}

	/*
	 * Verifica se os atribuitos dos metodos estao corretos metodo soma(inteiro a)
	 * soma (a). a eh inteiro tambem? Tambem verifica a a sobrecarga de metodos
	 */
	public boolean checaAtributoChamadaMetodo(String nomeAtributo, String tipoAtributo, String nomeMetodo) {

		System.out.println(
				"nomeAtributo: " + nomeAtributo + " tipoAtributo: " + tipoAtributo + " nomeMetodo: " + nomeMetodo);

		boolean checado = false;
		String tipoMetodo;
		String tipoChamada;

		this.countParametro++;

		LinkedList<AtributosMetodos> listMetodos = new LinkedList<AtributosMetodos>();

		int length = tabelaMetodos.size();
		System.out.println("Quantidade de metodos soma: " + length);
		// guarda o(s) metodo(s) requisitado em uma lista para poder verificar a
		// sobrecarga
		for (int i = 0; i < length; i++) {
			AtributosMetodos metodo = tabelaMetodos.get(i);
			if (metodo.getNomeMetodo().equals(nomeMetodo))
				listMetodos.add(metodo);
		}

		length = listMetodos.size();

		for (int i = 0; i < length; i++) {
			AtributosMetodos metodo = listMetodos.get(i);
			try {
			tipoMetodo = metodo.getTipoParametros().get(this.countParametro);
			if (tipoAtributo.equals("Identificador")) {
				if (this.tabelaConstVar.containsKey(nomeAtributo)) {
					tipoChamada = this.tabelaConstVar.get(nomeAtributo).getTipo();
					if (tipoChamada.equals(tipoMetodo)) {
						checado = true;
						return checado;
					}
				} else {
					System.out.println(nomeAtributo + " Nao encontrada na tabela");
				}

			} else {
				tipoChamada = converteVar(tipoAtributo, nomeAtributo);
				if (tipoChamada.equals(tipoMetodo)) {
					checado = true;
					return checado;
				}
			}
			}catch(Exception e) {
				
			}
		}
		System.out.println("Erro na chamada de metodos");
		return checado;
	}

	public void zerarCountParametro() {
		this.countParametro = -1;
	}

	/* Verifica se a variavel ja esta declarada dentro do metodo que ela esta */
	public boolean varConstDeclaradaMetodo(String nomeMetodo, String nomeParametro) {

		int length = tabelaMetodos.size();

		for (int i = 0; i < length; i++) {
			if (tabelaMetodos.get(i).getNomeMetodo().equals(nomeMetodo)) {
				return tabelaMetodos.get(i).containsVar(nomeParametro);
			}
		}
		return false;
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
		if (tabelaConstVar.containsKey(cadeia)) {
			if (tabelaConstVar.get(cadeia).getTipo().equals(tipo)) {
				return true;
			} else
				return false;
		} else
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
