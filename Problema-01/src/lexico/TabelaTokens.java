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
	private LinkedList<Tokens> conjunto_token;
	
	
	public TabelaTokens(String nome_arquivo) {
		
		file = new File(nome_arquivo);
		conjunto_token = new LinkedList<Tokens>();
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
		int tam_conjuntoToken = tamanhoTabela();
		FileWriter writer = new FileWriter(file);
		writer.write("Linha	|	Lexama	|		Classificacao\n");
		for (int i = 0; i < tam_conjuntoToken; i++) {
		writer.write(conjunto_token.get(i).getLinha() + 
				"		" +conjunto_token.get(i).getLexama()+
				"				"+conjunto_token.get(i).getClassificacao() + "\n");
		writer.flush();
		}
		writer.write("\n\n\n\nColocar aqui nessa parte os erros!");
		writer.flush();
		
		writer.close();
	}
	
	public int tamanhoTabela() {
		return conjunto_token.size();
	}
	
	
}
