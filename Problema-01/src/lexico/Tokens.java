package lexico;

public class Tokens {
	
	private int linha;
	private String token;
	private String descricao;
	
	public Tokens(int linha, String token, String descricao) {
		this.linha = linha;
		this.token = token;
		this.descricao = descricao;
	}

	public int getLinha() {
		return linha;
	}
	
	public String getToken() {
		return token;
	}
	
	public String getDescricao() {
		return descricao;
	}
}
