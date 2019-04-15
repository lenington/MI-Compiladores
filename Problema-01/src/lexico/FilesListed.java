package lexico;

import java.io.File;
import java.util.LinkedList;

public class FilesListed {
	
	private String directory_name;
	
	public FilesListed(String directory_name) {
		this.directory_name = directory_name;
	}
	
	public LinkedList<String> arquivosEntrada() {
		String[] filesNames;
		LinkedList<String> entry_files = new LinkedList<String>();
		int tam;

		File f = new File(directory_name);
		if (f.isDirectory() == false)
			return null;
		
		filesNames = f.list();
		tam = filesNames.length;
		for (int i = 0; i < tam; i++)
			if (filesNames[i].contains(".entrada"))
				entry_files.add("teste/"+filesNames[i]);

		return entry_files;
	}

	
}
