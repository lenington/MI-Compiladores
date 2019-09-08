package AnalisadorSemantico;

/*Classe responsavel por verificar a analise semantica das constantes e variaveis*/


public class VerificadorCasos {
	
	private String tipo;
	private String valor;
	private String tokenType;
	private String nomeConstante;
	private String valorVetorPrimeiro;
	private String valorVetorSegundo = " ";

	/*Metodo responsavel por verificar se a semantica das constantes e variaveis estao corretas*/
	public boolean semanticaConstante() {
		if (tipo.equals("real") && tokenType.equals("Numero"))
			return true;

		else if (tipo.equals("inteiro") && tokenType.equals("Numero") && valor.contains(".") == false)
			return true;

		else if (tipo.equals("texto") && tokenType.equals("Cadeia de Caractere"))
			return true;

		else if (tipo.equals("boleano") && (valor.equals("verdadeiro") || valor.equals("falso")))
			return true;
		else
			return false;
	}

	
	/*Verifica a semantica da parte do vetor*/
	public boolean sematicaVetor() {
		if (this.valorVetorSegundo.equals(" ")) {
			if (this.valorVetorPrimeiro.contains(".") == false) {
				return true;
			}
		} else {
			if (this.valorVetorSegundo.contains(".") == false)
				return true;
		}
			return false;
	}

	public void zerarStrings() {
		this.tipo = " ";
		this.valor = " ";
		this.tokenType = " ";
		this.nomeConstante = " ";
		this.valorVetorPrimeiro = " ";
		this.valorVetorSegundo = " ";
	}
	
	public void zerarVetores() {
		this.valorVetorPrimeiro = " ";
		this.valorVetorSegundo = " ";
	}

	public String getTipo() {
		return this.tipo;
	}

	public String getValor() {
		return this.valor;
	}

	public String getTokenType() {
		return this.tokenType;
	}

	public String getNomeConstante() {
		return this.nomeConstante;
	}

	public String getValorVetorPrimeiro() {
		return this.valorVetorPrimeiro;
	}

	public String getValorVetorSegundo() {
		return this.valorVetorSegundo;
	}

	// nome da constante real -->const1<-- = 3
	public void setNomeConstante(String nomeConstante) {
		this.nomeConstante = nomeConstante;
	}

	// real inteiro texto boleano
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	// 3.5 4 "texto" falso verdadeiro
	public void setValor(String valor) {
		this.valor = valor;
	}

	// cadeia Caractere, Numerico...
	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

	// [->5<-]
	public void setValorVetorPrimeiro(String valorVetor) {
		this.valorVetorPrimeiro = valorVetor;
	}

	// [2][->2<-]
	public void setValorVetorSegundo(String valorVetor) {
		this.valorVetorSegundo = valorVetor;
	}

}
