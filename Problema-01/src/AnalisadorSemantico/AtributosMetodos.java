package AnalisadorSemantico;

import java.util.LinkedList;

/*
 * Essa classe so armazena e retorna as informacoes sobre o metodo
 * tipoRetorno: inteiro, real, texto...
 * listaParametro: (a,b,c)
 * tipoParametro(inteiro, real, texto)
 * **/

public class AtributosMetodos {
	
	private String nomeMetodo;
	private String tipoRetorno;
	private boolean utilizado;
	private LinkedList<String> listaParametros;
	private LinkedList<String> tipoParametros;
	
	
	public AtributosMetodos(String nomeMetodo, String tipoRetorno, LinkedList<String> listaParametros, LinkedList<String> tipoParametros, boolean utilizado) {
		this.nomeMetodo = nomeMetodo;
		this.tipoRetorno = tipoRetorno;
		this.listaParametros = listaParametros;
		this.tipoParametros = tipoParametros;
		this.utilizado = utilizado;
	}
	
	public boolean getUtilizado() {
		return this.utilizado;
	}
	
	public String getTipoRetorno() {
		return this.tipoRetorno;
	}
	
	public LinkedList<String>  getListaParametros(){
		return this.listaParametros;
	}
	
	public LinkedList<String> getTipoParametros(){
		return this.tipoParametros;
	}
	
	
	public String getNomeMetodo() {
		return this.nomeMetodo;
	}
	
	public String tipoParametro(int i) {
		return tipoParametros.get(i);
	}
	
	/*Retorna true se o nome do parametro estiver contido na lista*/
	public boolean containsVar(String nomeParametro) {
		return listaParametros.contains(nomeParametro);
	}
	
	

}
