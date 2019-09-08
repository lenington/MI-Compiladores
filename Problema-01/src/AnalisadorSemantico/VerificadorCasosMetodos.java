package AnalisadorSemantico;

import java.util.LinkedList;

/*
 * Classe criada para fazer a verificacao da semantica em relacao a analise semantica
 * 
 * */

public class VerificadorCasosMetodos {
	
	private String nomeMetodo;
	private String tipoRetorno;
	private LinkedList<String> listaParametros;
	private LinkedList<String> tipoParametro;
	
	
	public boolean semanticaMetodo() {
		if (nomeMetodo.equals("principal") && listaParametros.isEmpty() == false) {
			System.out.println("Erro! o metodo principal nao pode conter parametros");
			return true;
		}
		return false;
	}
	
	/*metodo para verificar se tem parametros duplicados. Exemplo.: metodo soma(inteiro casa, real casa)*/
	public boolean duplicateParametro(String nomeParametro) {
		return this.listaParametros.contains(nomeParametro);
	}
	
	//armazena o nome do metodo: metodo *soma*(){}
	public void setNomeMetodo(String nomeMetodo) {
		this.nomeMetodo = nomeMetodo;
	}
	
	//armazena o tipo do retorno :inteiro
	public void setTipoRetorno(String tipoRetorno) {
		this.tipoRetorno = tipoRetorno;
	}
	
	//armazena os parametros (inteiro *a*, real *nome*)
	public void setNomeParametro(String nomeParametro) {
		this.listaParametros.add(nomeParametro);

	}
	
	//armazena o tipo do parametro (*inteiro* a, *real* nome)
	public void setTipoPrametro(String tipoParametroS) {
		this.tipoParametro.add(tipoParametroS);
	}
	
	//retorna o nome do metodo
	public String getNomeMetodo() {
		return this.nomeMetodo;
	}
	
	//retorna o tipo do retorno :inteiro
	public String getTipoRetorno() {
		return  this.tipoRetorno;
	}
	
	//retorna os parametros (inteiro *a*, real *nome*)
	public LinkedList<String> getNomeParametro() {
		return this.listaParametros;

	}
	
	//retorna o tipo do parametro (*inteiro* a, *real* nome)
	public LinkedList<String> getTipoPrametro() {
		return this.tipoParametro;
	}
	

	public void inicializaAtributos() {
		this.nomeMetodo = "";
		this.tipoRetorno = "";
		this.listaParametros = new LinkedList<String>();
		this.tipoParametro = new LinkedList<String>();
	}
	
}
