package AnalisadorSemantico;

import java.util.LinkedList;

import AnalisarSintatico.TokenReader;

public class AnalisadorSemantico {

	private TokenReader s;
	private LinkedList<String> tipo;
	private TabelaSemantica tabSem;
	private VerificadorCasos vc;
	private String token;
	private String tipoVerificarCaso;
	private String nomeConstante;
	private String valorConstante;
	private String tktp;
	private boolean hasErroSemantico;

	public AnalisadorSemantico(TokenReader s) {
		hasErroSemantico = false;
		this.s = s;
		tipo = new LinkedList<String>();
		tabSem = new TabelaSemantica();
		vc = new VerificadorCasos();
		tipo.add("real");
		tipo.add("inteiro");
		tipo.add("boleano");
		tipo.add("texto");
		tipo.add("vazio");
	}

	public void programa() {

		token = s.nextToken();
		if (token.equals("programa")) {
			token = s.nextToken();
			if (token.equals("{")) {

				token = s.nextToken();
				blocoConstantes();
				// escopoPrograma();

				if (token.equals("}"))
					System.out.println("SUCESSO");
			}
		}
	}

	public void blocoConstantes() {

		if (token.equals("constantes")) {
			token = s.nextToken();
			if (token.equals("{")) {
				// chamar metodo<estrutura de constantes>
				token = s.nextToken();
				estruturaConstantes();

				if (token.equals("}")) {
					token = s.nextToken();
					return;
				}
			}

		} else
			return; // senao vier uma "constantes" retorna
	}

	public void estruturaConstantes() {

		if (tipo.contains(token)) {
			// chamar metodo de constantes; <Constantes>
			tipoVerificarCaso = token;
			token = s.nextToken();
			constanteS();
			if (token.trim().equals(";")) {
				token = s.nextToken();
				estruturaConstantes();
			}
		} else if (token.equals("}")) {
			return;
		}
	}

	public void constanteS() {
		token = s.tokenType();

		if (token.equals("Identificador")) {
			nomeConstante = token;
			token = s.nextToken().trim();
			if (token.equals("=")) {
				// chamar <constante><multiconst>
				token = s.nextToken();
	
				valorConstante = token;
				tktp = s.tokenType();
				constante();
				
				if (!vc.semanticaConstante(tipoVerificarCaso, valorConstante, tktp))
					System.out.println("Tem erro semantico");
				else
					tabSem.inserirTabela(nomeConstante, tktp, "constante", tipoVerificarCaso);
				
				multiconst();
			}
		}
	}

	public void constante() {
		token = s.tokenType();

		if (token.equals("Identificador")) {
			token = s.nextToken();
			return;
		} else if (token.equals("Numero")) {
			token = s.nextToken();
			return;
		} else if (token.matches("Cadeia de Caractere")) {
			token = s.nextToken();
			return;
		}
	}

	public void multiconst() {

		if (token.equals(",")) {
			token = s.nextToken();
			constanteS();
		} else
			return;
	}

}
