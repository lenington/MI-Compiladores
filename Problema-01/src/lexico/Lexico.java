package lexico;

import java.util.ArrayList;
import java.util.List;

import automatos.AutomatosTeste;

public class Lexico {
	
	private Buffer buffer;
	private AutomatosTeste automatos;
	private characterDiscover charDiscover;
	
	private List<String> listaTokens; //lista de todos os tokens do script
	private List<String> listaErros; //lista de todos os erros l�xicos do script

	
	public Lexico(Buffer buffer) {
		this.buffer = buffer; 
		
		this.listaTokens = new ArrayList<String>();
		this.listaErros = new ArrayList<String>();
		
		this.automatos = new AutomatosTeste(buffer); //inicializa os automatos
		this.charDiscover = new characterDiscover();
	}
	
	public void rodarAutomatos() {
		char c = ' ';
		String token = "";
		
		//roda enquanto nao for o fim do script/arquivo
		while(!this.buffer.isFimScript()) {
			c = this.buffer.charAtual(); 
			
			if ((Character) c == null) {
				break; //finalizado!
			}
			
			if(this.charDiscover.isLetra(c)) {
				//chama funcao de automato identificador...
				this.buffer.charSeguinte();
				continue;
			} else if(this.charDiscover.isDigito(c) ) {
				//chama funcao de automato numero...
				this.buffer.charSeguinte();
				continue;
			} else if(c == '"') {
				//chama funcao de automato de cadeia de caracteres...
				this.buffer.charSeguinte();
				token = this.automatos.automatoCadeiaCaractere();
				if(!token.equals("TOKEN INDEFINIDO")) System.out.println(token);
			} else {
				//chama as outras funcoes de automatos...
				
				//testando...
				token = this.automatos.automatoOperadorAritmetico();
				if(!token.equals("TOKEN INDEFINIDO")) System.out.println(token);
				
				token = this.automatos.automatoOperadorLogico();
				if(!token.equals("TOKEN INDEFINIDO")) System.out.println(token);
				
				token = this.automatos.automatoOperadorRelacional();
				if(!token.equals("TOKEN INDEFINIDO")) System.out.println(token);
				
				token = this.automatos.automatoDelimitador();
				if(!token.equals("TOKEN INDEFINIDO")) System.out.println(token);
				
				if(!this.buffer.isFimScript()) {
					//this.buffer.charSeguinte();
				}
			} 
		}
	}
}
