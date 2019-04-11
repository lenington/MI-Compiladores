package lexico;

import automatos.AutomatosTeste;

public class Lexico {
	
	private Buffer buffer;
	private AutomatosTeste automatos;
	private characterDiscover charDiscover;
	private ConcatenadorString concatenarString;
	private PalavrasReservadas palavra;
	private TabelaTokens tabelaTokens;
	
	public Lexico(Buffer buffer) {
		this.buffer = buffer; 
		this.palavra = new PalavrasReservadas();
		this.concatenarString = new ConcatenadorString();
		this.automatos = new AutomatosTeste(buffer, concatenarString); //inicializa os automatos
		this.charDiscover = new characterDiscover();
		this.tabelaTokens = new TabelaTokens("teste.saida");
		
	}
	
	public void rodarAutomatos() {
		char c = ' ';
		String classificacao = "";
		char proximo_caractere;
		
		//roda enquanto nao for o fim do script/arquivo
		while(buffer.temProximaLinha()) {
			buffer.lerLinha();
			while(buffer.temProximoChar()) {
				c = buffer.lerChar();
				//System.out.println(c);
				//esse if trata o / que pode levar ao automato de comentarios ou de operador arimetico
				if (c == '/') {
					concatenarString.concatenar_String(c);
					if (buffer.temProximoChar()) {
						proximo_caractere = buffer.verProximo();
						if (proximo_caractere == '*' || proximo_caractere == '/') {
							classificacao = automatos.automatoComentarios();
							System.out.println(buffer.getLinha() + " , " + classificacao + ": " + concatenarString.getStringConcatenada());
							tabelaTokens.guardarTokens(buffer.getLinha(), concatenarString.getStringConcatenada(), classificacao);
						}
						else {
							System.out.println("Operador arimetico: /"); //tratar o / como divisao		
						}
						concatenarString.zerar_StringConcatenada();
					}
				} else if(this.charDiscover.isLetra(c)) {
					concatenarString.concatenar_String(c);
					String a = automatos.automatoIdentificador();
					if (palavra.ehPalavraReservada(concatenarString.getStringConcatenada())) {
						System.out.println(buffer.getLinha() + ", eh palavra reservada: " + concatenarString.getStringConcatenada());
						tabelaTokens.guardarTokens(buffer.getLinha(), concatenarString.getStringConcatenada(), "palavra reservada");
					}
					else {
						System.out.println(buffer.getLinha() + ", Identificador: " + concatenarString.getStringConcatenada());
						tabelaTokens.guardarTokens(buffer.getLinha(), concatenarString.getStringConcatenada(), "identificador");
					} 
					concatenarString.zerar_StringConcatenada();
					
				}  else if(this.charDiscover.isDigito(c) ) {
						
				} else if(c == '"') { //chama funcao de automato de cadeia de caracteres...
					concatenarString.concatenar_String(c);
					System.out.println(buffer.getLinha() + ","+this.automatos.automatoCadeiaCaractere() + ": " + concatenarString.getStringConcatenada());
					concatenarString.zerar_StringConcatenada();
					
				} else if(this.charDiscover.isDelimitador(c)) {
					System.out.println(buffer.getLinha() + ", DELIMITADOR: " + c);
				} else {
					//chama as outras funcoes de automatos...
					
					//testando...
					//token = this.automatos.automatoOperadorAritmetico();
					//if(!token.equals("TOKEN INDEFINIDO")) System.out.println(token);
					
					//token = this.automatos.automatoOperadorLogico();
					//if(!token.equals("TOKEN INDEFINIDO")) System.out.println(token);
					
					//token = this.automatos.automatoOperadorRelacional();
					//if(!token.equals("TOKEN INDEFINIDO")) System.out.println(token);
					
					//token = this.automatos.automatoDelimitador();
					//if(!token.equals("TOKEN INDEFINIDO")) System.out.println(token);
					
				} 
			}
		}
	}
}
