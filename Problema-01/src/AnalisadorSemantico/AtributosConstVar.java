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
	private String metodoDaVariavel;
	private boolean utilizado;
	
	/*
	 * token Numero cadeia caractere e etc...
	 * cat variavel ou constante
	 * tipo inteiro real texto
	 * valor. conteudo da variavel
	 * primeiroIndice - isso eh relacionado ao vetor [1][2]
	 * segundoIndice - [1][*2*]
	 * utilizado - se a variavel ja foi utilizada durante o codigo
	 * metodoDaVariavel - nome do metodo que a variavel veio.
	 * */
	
	public AtributosConstVar(String token, String cat, String tipo, String valor,String primeiroIndice, String segundoIndice, boolean utilizado, String metodoDaVariavel) {
		this.token = token;
		this.cat = cat;
		this.tipo = tipo;
		this.valor = valor;
		this.primeiroIndice = primeiroIndice;
		this.segundoIndice = segundoIndice;
		this.metodoDaVariavel = metodoDaVariavel;
		this.utilizado = utilizado;
	}
	
	/*
	 * tipo - real inteiro texto
	 * **/
	public String getTipo() {
		return this.tipo;
	}
	
	/*
	 * metodoDaVariavel
	 * */
	public String getmeetodoDaVariavel() {
		return this.metodoDaVariavel;
	}
	
	public String getCategoria() {
		return this.cat;
	}
	
	public void imprimirString() {
		System.out.println(token + " " + cat + " " + tipo + " " + valor + " " + primeiroIndice + " " + " " + segundoIndice + " " + this.metodoDaVariavel+ "\n");
	}

}
