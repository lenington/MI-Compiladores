package lexico;

import java.util.ArrayList;
import java.util.List;

import automatos.AutomatosTeste;

public class Lexico {
	
	private Buffer buffer;
	private AutomatosTeste automatos;
	
	private List<String> listaTokens; //lista de todos os tokens do script
	private List<String> listaErros; //lista de todos os erros léxicos do script

	
	public Lexico(Buffer buffer) {
		this.buffer = buffer; 
		
		this.listaTokens = new ArrayList<String>();
		this.listaErros = new ArrayList<String>();
		
		this.automatos = new AutomatosTeste(buffer); //inicializa os automatos
	}
	
	public void rodarAutomatos() {
		char c = ' ';
		String token = "";
		
		//roda enquanto não for o fim do script/arquivo
		while(!this.buffer.isFimScript()) {
			c = this.buffer.lookAhead(); 
			
			if ((Character) c == null) {
				break; //finalizado!
			}
			
			if(this.automatos.isLetra(c)) {
				//chama função de automato identificador...
				this.buffer.charSeguinte();
				continue;
			} else if(this.automatos.isDigito(c) ) {
				//chama função de automato numero...
				this.buffer.charSeguinte();
				continue;
			} else if(c == '"') {
				//chama função de automato de cadeia de caracteres...
				this.buffer.charSeguinte();
				continue;
			} else {
				//chama as outras funções de automatos...
				
				//testando...
				token = this.automatos.automatoOperadorAritmetico();
				System.out.println(token);
				
				if(!this.buffer.isFimScript()) {
					//this.buffer.charSeguinte();
				}
			} 
		}
	}
}
