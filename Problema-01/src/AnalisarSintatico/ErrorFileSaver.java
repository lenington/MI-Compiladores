package AnalisarSintatico;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

public class ErrorFileSaver {
	
	private String file_name, caminho_saida;
	private LinkedList<String> erros;
	
	public ErrorFileSaver(String file_name, LinkedList<String> erros) {
		this.file_name = file_name;
		this.erros = erros;
	}
	
	public void saveErrorInFile() throws IOException {
		
		System.out.println("> Salvando erros no arquivo: " + file_name);
		int tam_erros = erros.size();
		
		FileWriter writer = new FileWriter(new File (file_name));
		
		for (int i = 0; i < tam_erros; i++) {
			writer.write(erros.get(i) + "\n");
			writer.flush();
		}
		
		writer.close();
	}
	
	

}
