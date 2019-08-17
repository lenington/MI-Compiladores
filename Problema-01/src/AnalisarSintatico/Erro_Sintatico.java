package AnalisarSintatico;

import java.util.LinkedList;

public class Erro_Sintatico {

	private LinkedList<String> erros;
		public Erro_Sintatico() {  
			erros = new LinkedList<String>();
		}  
		
		public void guardarErros(int linha, String esperado) {
			erros.add("Error! Linha: " + linha + " Esperava: " + esperado);
			displayError(linha, esperado);
		}
		
		private void displayError(int linha, String esperado) {
			System.out.println("Error! Linha: " + linha + " Esperava: " + esperado);
		}
}
