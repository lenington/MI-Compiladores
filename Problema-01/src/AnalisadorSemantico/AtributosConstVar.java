package AnalisadorSemantico;


/*
 * Classe que armazena as informacoes para a tabela das constantes e variaveis
 * */
public class AtributosConstVar {
	
	private String cat;
	private String token;
	private String tipo;
	private String valor;
	private String primeiroIndice;
	private String segundoIndice;
	private boolean utilizado;
	
	
	
	public AtributosConstVar(String token, String cat, String tipo, String valor,String primeiroIndice, String segundoIndice, boolean utilizado) {
		this.token = token;
		this.cat = cat;
		this.tipo = tipo;
		this.valor = valor;
		this.primeiroIndice = primeiroIndice;
		this.segundoIndice = segundoIndice;
		this.utilizado = utilizado;
	}
	
	public String getTipo() {
		return this.tipo;
	}
	
	public void imprimirString() {
		System.out.println(token + " " + cat + " " + tipo + " " + valor + " " + primeiroIndice + " " + " " + segundoIndice + " \n");
	}

}
