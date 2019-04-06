package lexico;

public class Tokens {
	
	private int linha;
	private String lexama;
	private String classificacao;
	
	public Tokens(int linha, String lexama, String classificacao) {
		this.linha = linha;
		this.lexama = lexama;
		this.classificacao = classificacao;
	}

	public int getLinha() {
		return linha;
	}
	
	public String getLexama() {
		return lexama;
	}
	
	public String getClassificacao() {
		return classificacao;
	}
}
