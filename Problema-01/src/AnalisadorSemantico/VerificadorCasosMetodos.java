package AnalisadorSemantico;

import java.util.LinkedList;

/*
 * Classe criada para fazer a verificacao da semantica em relacao a analise semantica
 * 
 * */

public class VerificadorCasosMetodos {

	/*
	 * metodo soma (inteiro a, real b):vazio{} tipoRetorno: vazio nomeMetodo: soma
	 * listaParametro: [a,b] tipoParametro: [inteiro, real]
	 */

	private String nomeMetodo;
	private String tipoRetorno;
	private boolean temResultado = false;
	private LinkedList<String> listaParametros;
	private LinkedList<String> tipoParametro;

	/*
	 * caso seja o metodo principal, nao permite parametros no escopo do metodo
	 **/
	public boolean semanticaMetodo() {
		if (nomeMetodo.equals("principal") && listaParametros.isEmpty() == false) {
			System.out.println("Erro! o metodo principal nao pode conter parametros");
			return true;
		}
		return false;
	}

	/**
	 * Verifica a semantica do retorno valorResultado eh : resultado **"verdade"**
	 * tipoResultado eh: cadeia de Caractere retorna false se houver erro semantica
	 */
	public boolean semanticaResultado(String valorResultado, String tipoResultado) {
		
		System.out.println(valorResultado + " " + tipoResultado + " >>" + this.tipoRetorno);
		
		if (this.nomeMetodo.equals("principal") || this.tipoRetorno.equals("vazio")) {
			System.out.println("Error! Metodo principal/vazio nao precisa de retorno");
			return false;
		} else if (this.tipoRetorno.equals("texto") && tipoResultado.equals("Cadeia de Caractere") == false) {
			System.out.println("Error no retorno! Esperava uma cadeia de caractere");
			this.temResultado = true;
			return false;
		}else if (this.tipoRetorno.equals("real") && tipoResultado.equals("Numero") == false) {
			System.out.println("Error no retorno! Esperava um numero real");
			this.temResultado = true;
			return false;
		}else if (this.tipoRetorno.equals("inteiro") && (tipoResultado.equals("Numero") == false || valorResultado.contains(".") == true)){
			System.out.println("Error no retorno! Esperava um retorno inteiro");
			this.temResultado = true;
			return false;
		}else if (this.tipoRetorno.equals("booleano") && tipoResultado.equals("booleano") == false) {
			System.out.println("Error! Erro no retorno do booleano");
			this.temResultado = true;
			return false;
		}
		return true;
	}
	
	/*
	 * Verifica se o metodo tem retorno
	 */
	public boolean oRetornoAparaceu() {
		if (this.tipoRetorno.equals("vazio") == false && this.temResultado == true) {
			return true;
		}else if (this.tipoRetorno.equals("vazio") == true && this.temResultado == false) {
			return true;
		}else if (this.nomeMetodo.equals("principal") == true && this.temResultado == false) {
			if (this.tipoRetorno.equals("vazio") == true)
				return true;
			else{
				System.out.println("Erro! O metodo principal deve ser do tipo vazio");
				return false;
			}
		}
		//System.out.println("Error! Esse metodo necessita de um resultado.");
		return false;
	}
	
	public void setTemResultado(boolean temResultado) {
		this.temResultado = temResultado;
	}
	
	public boolean getTemResultado() {
		//System.out.println("AQUI >> "+nomeMetodo+" >> "+temResultado+" >> FIM");
		return this.temResultado;
	}

	/*
	 * metodo para verificar se tem parametros duplicados. Exemplo.: metodo
	 * soma(inteiro casa, real casa)
	 */
	public boolean duplicateParametro(String nomeParametro) {
		return this.listaParametros.contains(nomeParametro);
	}

	// armazena o nome do metodo: metodo *soma*(){}
	public void setNomeMetodo(String nomeMetodo) {
		this.nomeMetodo = nomeMetodo;
	}

	// armazena o tipo do retorno :inteiro
	public void setTipoRetorno(String tipoRetorno) {
		this.tipoRetorno = tipoRetorno;
	}

	// armazena os parametros (inteiro *a*, real *nome*)
	public void setNomeParametro(String nomeParametro) {
		this.listaParametros.add(nomeParametro);

	}

	// armazena o tipo do parametro (*inteiro* a, *real* nome)
	public void setTipoPrametro(String tipoParametroS) {
		this.tipoParametro.add(tipoParametroS);
	}
	
	// retorna o nome do metodo
	public String getNomeMetodo() {
		return this.nomeMetodo;
	}

	// retorna o tipo do retorno :inteiro
	public String getTipoRetorno() {
		return this.tipoRetorno;
	}

	// retorna os parametros (inteiro *a*, real *nome*)
	public LinkedList<String> getNomeParametro() {
		return this.listaParametros;

	}

	// retorna o tipo do parametro (*inteiro* a, *real* nome)
	public LinkedList<String> getTipoPrametro() {
		return this.tipoParametro;
	}

	public void inicializaAtributos() {
		this.nomeMetodo = "";
		this.tipoRetorno = "";
		this.listaParametros = new LinkedList<String>();
		this.tipoParametro = new LinkedList<String>();
		this.temResultado = false;
	}

}
