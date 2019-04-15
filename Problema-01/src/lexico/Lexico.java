package lexico;

import java.io.IOException;

import automatos.Automatos;

public class Lexico {
	
	private Buffer buffer;
	private Automatos automatos;
	private characterDiscover charDiscover;
	private ConcatenadorString concatenarString;
	private PalavrasReservadas palavra;
	private TabelaTokens tabelaTokens;
	
	public Lexico(Buffer buffer, String arquivo_saida) {
		this.buffer = buffer; 
		this.palavra = new PalavrasReservadas();
		this.concatenarString = new ConcatenadorString();
		this.automatos = new Automatos(buffer, concatenarString); //inicializa os automatos
		this.charDiscover = new characterDiscover();
		this.tabelaTokens = new TabelaTokens(arquivo_saida);
	}
	
	public void saveTokensInFile() throws IOException {
		this.tabelaTokens.salvarTokens();
	}
	
	public void rodarAutomatos(){
		char c = ' ';
		String classificacao;
		char proximo_caractere;
		
		//roda enquanto nao for o fim do script/arquivo
		while(buffer.temProximaLinha()) {
			buffer.lerLinha();
			while(buffer.temProximoChar()) {
				c = buffer.lerChar();////System.out.println("Antes de entrar: "+c);
				//esse if trata o / que pode levar ao automato de comentarios ou de operador arimetico
				if (c == '/') {
					concatenarString.concatenar_String(c);
					if (buffer.temProximoChar()) {
						proximo_caractere = buffer.verProximo();
						if (proximo_caractere == '*' || proximo_caractere == '/') {
							classificacao = automatos.automatoComentarios();
							//System.out.println(buffer.getLinha() + ", " + classificacao + ": " + concatenarString.getStringConcatenada());
							tabelaTokens.guardarTokens(buffer.getLinha(), concatenarString.getStringConcatenada(), classificacao);
						}
						else {
							classificacao = automatos.automatoOperadorAritmetico();
							//System.out.println(buffer.getLinha() + ", "+classificacao + ": " + concatenarString.getStringConcatenada());
							tabelaTokens.guardarTokens(buffer.getLinha(), concatenarString.getStringConcatenada(), classificacao);
						}
						concatenarString.zerar_StringConcatenada();
					}
				} 
				
				else if(this.charDiscover.isLetra(c)) {
					concatenarString.concatenar_String(c);
					classificacao = automatos.automatoIdentificador();
					if (palavra.ehPalavraReservada(concatenarString.getStringConcatenada())) {
						//System.out.println(buffer.getLinha() + ", "+ classificacao+ ": " + concatenarString.getStringConcatenada());
						tabelaTokens.guardarTokens(buffer.getLinha(), concatenarString.getStringConcatenada(), "Palavra Reservada");
					}
					else {
						//System.out.println(buffer.getLinha() + ", "+ classificacao+ ": " + concatenarString.getStringConcatenada());
						tabelaTokens.guardarTokens(buffer.getLinha(), concatenarString.getStringConcatenada(), "Identificador");
					} 
					concatenarString.zerar_StringConcatenada();
				}

				else if(this.charDiscover.isDigito(c)) {
					concatenarString.concatenar_String(c);
					classificacao = automatos.automatoNumero();
					//System.out.println(buffer.getLinha() + ", " + classificacao + ": " + concatenarString.getStringConcatenada());
					tabelaTokens.guardarTokens(buffer.getLinha(), concatenarString.getStringConcatenada(), classificacao);
					concatenarString.zerar_StringConcatenada();
				} else if(c == '"') { //chama funcao de automato de cadeia de caracteres...
					concatenarString.concatenar_String(c);
					classificacao = this.automatos.automatoCadeiaCaractere();
					//System.out.println(buffer.getLinha() + ", "+ classificacao + ": " + concatenarString.getStringConcatenada());
					tabelaTokens.guardarTokens(buffer.getLinha(), concatenarString.getStringConcatenada(), classificacao);
					concatenarString.zerar_StringConcatenada();
				} 
				
				else if(this.charDiscover.isDelimitador(c)) {
					//System.out.println(buffer.getLinha() + ", Delimitador: " + c);
					tabelaTokens.guardarTokens(buffer.getLinha(), Character.toString(c), "Delimitador");
					concatenarString.zerar_StringConcatenada();
				} 
				
				else if(c == '!' || c == '&' || c == '|') {
					concatenarString.concatenar_String(c);
					classificacao = automatos.automatoOperadorLogico();
					//System.out.println(buffer.getLinha() + ", "+classificacao + ": " + concatenarString.getStringConcatenada());
					tabelaTokens.guardarTokens(buffer.getLinha(), concatenarString.getStringConcatenada(), classificacao);
					concatenarString.zerar_StringConcatenada();
				} 
				
				else if(c == '=' || c == '>' || c == '<') {
					concatenarString.concatenar_String(c);
					classificacao = automatos.automatoOperadorRelacional();
					//System.out.println(buffer.getLinha() + ", "+classificacao + ": " + concatenarString.getStringConcatenada());
					tabelaTokens.guardarTokens(buffer.getLinha(), concatenarString.getStringConcatenada(), classificacao);
					concatenarString.zerar_StringConcatenada();
				} 
				
				else if(c == '+' || c == '-' || c == '*') {
					concatenarString.concatenar_String(c);
					//verifica se eh numero ou operador aritmetico para numeros negativos
					if (c == '-' && buffer.temProximoChar()) {
						proximo_caractere = buffer.verProximo();
						//verifica se o proximo eh numero ou espaco
						if (charDiscover.isDigito(proximo_caractere) || charDiscover.isEspaco(proximo_caractere)) {
							classificacao = automatos.automatoNumero();
							//System.out.println(buffer.getLinha() + ", " + classificacao + ": " + concatenarString.getStringConcatenada());
							tabelaTokens.guardarTokens(buffer.getLinha(), concatenarString.getStringConcatenada(), classificacao);
						} else {
							classificacao = automatos.automatoOperadorAritmetico();
							//System.out.println(buffer.getLinha() + ", "+automatos.automatoOperadorAritmetico() + ": " + concatenarString.getStringConcatenada());
							tabelaTokens.guardarTokens(buffer.getLinha(), concatenarString.getStringConcatenada(), automatos.automatoOperadorAritmetico());
						}
					} else {
						classificacao = automatos.automatoOperadorAritmetico();
						//System.out.println(buffer.getLinha() + ", "+classificacao + ": " + concatenarString.getStringConcatenada());
						tabelaTokens.guardarTokens(buffer.getLinha(), concatenarString.getStringConcatenada(), classificacao);
					}
					concatenarString.zerar_StringConcatenada();
				}
				
				else if(this.charDiscover.isSimbolo(c)) { 
					concatenarString.concatenar_String(c);
					//System.out.println(buffer.getLinha() + ", Simbolo: " + concatenarString.getStringConcatenada());
					tabelaTokens.guardarTokens(buffer.getLinha(), concatenarString.getStringConcatenada(), "Simbolo");
					concatenarString.zerar_StringConcatenada();
				}
				
			}
		}
	}
}
