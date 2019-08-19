package lexico;


import java.io.IOException;
import java.util.LinkedList;

import AnalisarSintatico.AnalisadorSintatico;
import AnalisarSintatico.Erro_Sintatico;
import AnalisarSintatico.ErrorFileSaver;
import AnalisarSintatico.TokenReader;

public class MainTeste {
	public static void main(String[] args) {
		
		String caminhoEntrada = "teste/entrada/";
		String caminhoSaida = "teste/saida/";
		
		//Essas duas linha abaixo acessa a pasta teste e retorna os nomes do arquivos com a extensao .entrada
		LinkedList<String> files = new FilesListed(caminhoEntrada).arquivosEntrada();
		int tam = files.size();
		
		for (int i = 0; i < tam; i++) {
			try {
				System.out.println("> Carregando o arquivo ["+files.get(i)+"] em: /"+caminhoEntrada);
				testarArquivo(files.get(i), caminhoEntrada, caminhoSaida);
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }
		}

        System.out.println("\n> Todos os arquivos de saida foram salvos em: "+caminhoSaida);
	}
	
	private static void testarArquivo(String arquivo, String caminhoEntrada, String caminhoSaida) throws IOException{
		
		Buffer buffer = new Buffer(caminhoEntrada + arquivo);
		Lexico lexico = new Lexico(buffer, caminhoSaida + arquivo.replace("entrada", "saida"));
        
        lexico.rodarAutomatos();
        lexico.saveTokensInFile();
        
        buffer.closeFile();
        
        if (lexico.hasLeximasErros()) {
        	TokenReader tr = new TokenReader(lexico.getListTokens());
            
            AnalisadorSintatico as = new AnalisadorSintatico(tr);
            
            as.programa(); 
            
            new ErrorFileSaver(arquivo, as.getErro_Sintatico()).saveErrorInFile();
        }
        else {
        	System.out.println("> O arquivo [ " + arquivo + " ] possui erros lexicos. Impossivel prosseguir");
        }
        
                
        
	}
}
