package automatos;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class analisadorLexico {

	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub

		String line = "0";
		int i = 0;
		Automatos aut = new Automatos();
		
		Scanner texto = new Scanner(new FileReader("teste.entrada"));
		while(texto.hasNextLine()) {
			 line = texto.nextLine();
		}
		
		aut.setCounter(i);
		System.out.println(aut.automatoOperadorRelacional(line));
		texto.close();
		
	}

}
