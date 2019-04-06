package lexico;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

/*
 * Classe responsavel por estruturar as listas  de tokens
 * e salvar no arquivo
 * */
public class TabelaTokens {
	
	private File file;
	private String saida;
	private LinkedList<Tokens> conjunto_token;
	
	
	public TabelaTokens(String nome_arquivo) {
		
		file = new File(nome_arquivo);
		saida = "LINHA		LEXAMA		DESCRICAO";
		conjunto_token = new LinkedList<Tokens>();
		
		/*
		 * Inicializar o arquivo de saida dos tokens
		 * Inicializar estrutura de dados que guarda os tokens
		 * */
	}
	
	/*
	 * Esse metodo vai receber os dados para serem salvos 
	 * no arquivo de token
	 * */
	public void guardarTokens(int linha, String lexama, String classificacao) {
		conjunto_token.add(new Tokens(linha, lexama, classificacao));
		
	}
	
	/*
	 * Metodo responsavel por salvar no arquivo os tokens
	 * */
	public void salvarTokens() throws IOException {
		FileWriter writer = new FileWriter(file);
		writer.write(saida);
		writer.flush();
		writer.close();
		
	}
	
	/*
	 * Chamar esse metodo para salvar os presentes no programa
	 * */
	public void errorTokens() {
		
	}
}
