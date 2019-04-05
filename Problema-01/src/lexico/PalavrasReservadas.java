package lexico;

import java.util.LinkedList;

public class PalavrasReservadas {
	LinkedList<String> palavras_reservadas;
	
	public PalavrasReservadas() {
		palavras_reservadas = new LinkedList<String>();
			palavras_reservadas.add("programa");
			palavras_reservadas.add("constantes");
			palavras_reservadas.add("variaveis");
			palavras_reservadas.add("metodo");
			palavras_reservadas.add("resultado");
			palavras_reservadas.add("principal");
			palavras_reservadas.add("se");
			palavras_reservadas.add("entao");
			palavras_reservadas.add("senao");
			palavras_reservadas.add("enquanto");
			palavras_reservadas.add("leia");
	    	palavras_reservadas.add("escreva");
			palavras_reservadas.add("vazio");
			palavras_reservadas.add("inteiro");
			palavras_reservadas.add("real");
			palavras_reservadas.add("boleano");
			palavras_reservadas.add("texto");
			palavras_reservadas.add("verdadeiro");
			palavras_reservadas.add("falso");
	}
	
	public boolean ehPalavraReservada(String palavra) {
		if (palavras_reservadas.contains(palavra))
			return true;
		return false;
	}

}
