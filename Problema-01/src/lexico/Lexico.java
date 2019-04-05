package lexico;

import automatos.AutomatosTeste;

public class Lexico {
	
	private Buffer buffer;
	private AutomatosTeste automatos;
	private characterDiscover charDiscover;
	private ConcatenadorString concatenarString;
	private PalavrasReservadas palavra;
	
	
	public Lexico(Buffer buffer) {
		this.buffer = buffer; 
		palavra = new PalavrasReservadas();
		concatenarString = new ConcatenadorString();
		this.automatos = new AutomatosTeste(buffer, concatenarString); //inicializa os automatos
		this.charDiscover = new characterDiscover();
	}
	
	public void rodarAutomatos() {
		char c = ' ';
		String token = "";
		char proximo_caractere;
		
		//roda enquanto nao for o fim do script/arquivo
		while(buffer.temProximaLinha()) {
			buffer.lerLinha();
			while(buffer.temProximoChar()) {
			c = buffer.lerChar();
			
			//esse if trata o / que pode levar ao automato de comentarios ou de operador arimetico
			if (c == '/') {
				concatenarString.concatenar_String(c);
				if (buffer.possoVerProximo()) {
					proximo_caractere = buffer.verProximo();
					if (proximo_caractere == '*' || proximo_caractere == '/') {
						System.out.println(automatos.automatoComentarios() + ": " + concatenarString.getStringConcatenada());
					}
					else {
						System.out.println("Operador arimetico: /"); //tratar o / como divisao		
					}
					concatenarString.zerar_StringConcatenada();
				}
			}
			
			else if(this.charDiscover.isLetra(c)) {
				concatenarString.concatenar_String(c);
				automatos.automatoIdentificador();
				if (palavra.ehPalavraReservada(concatenarString.getStringConcatenada()))
					System.out.println("eh palavra reservada: " + concatenarString.getStringConcatenada());
				else
					System.out.println("Identificador: " + concatenarString.getStringConcatenada());
				concatenarString.zerar_StringConcatenada();
				
			} 
			else if(this.charDiscover.isDigito(c) ) {
					
			} 
			else if(c == '"') { //chama funcao de automato de cadeia de caracteres...
				concatenarString.concatenar_String(c);
				System.out.println(this.automatos.automatoCadeiaCaractere() + ": " + concatenarString.getStringConcatenada());
				concatenarString.zerar_StringConcatenada();
				
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
