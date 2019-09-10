package AnalisadorSemantico;

import java.util.LinkedList;

/*
 * Essa classe so armazena e retorna as informacoes sobre o metodo
 * tipoRetorno: inteiro, real, texto...
 * listaParametro: (a,b,c)
 * tipoParametro(inteiro, real, texto)
 * **/

public class AtributosMetodos {
	
	private String tipoRetorno;
	private boolean utilizado;
	private LinkedList<String> listaParametros;
	private LinkedList<String> tipoParametros;
	
	
	public AtributosMetodos(String tipoRetorno, LinkedList<String> listaParametros, LinkedList<String> tipoParametros, boolean utilizado) {
		this.tipoRetorno = tipoRetorno;
		this.listaParametros = listaParametros;
		this.tipoParametros = tipoParametros;
		this.utilizado = utilizado;
	}
	
	public String tipoParametro(int i) {
		return tipoParametros.get(i);
	}
	
	/*Retorna true se o nome do parametro estiver contido na lista*/
	public boolean containsVar(String nomeParametro) {
		return listaParametros.contains(nomeParametro);
	}
	
	

}
