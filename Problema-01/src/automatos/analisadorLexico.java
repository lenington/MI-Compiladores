package automatos;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class analisadorLexico {
	
	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		String line = "";
		int i = 0, state = 0, count = -1;
		Automatos aut = new Automatos();
		
		Scanner texto = new Scanner(new FileReader("teste.entrada"));
		aut.setCounter(i);
		while (texto.hasNextLine()) {
			line = texto.nextLine();
			switch (state) {
			case 0:
				count++;
				if (line.charAt(count) == ' ')
					state = 0; 
				else if (line.charAt(count) == '/') {
					count++;
					if (line.charAt(count) == '/') {
						aut.setCounter(count);
						System.out.println(aut.automatoComentarios(line, texto, 1));
						count = 0; //teoricamente ele recebe 0 por que comentario de linha ocupa toda a linha
					}
					else if (line.charAt(count) == '*') {
						aut.setCounter(count);
						System.out.println(aut.automatoComentarios(line, texto, 2));
						count = aut.getCounter(); // uma possibilidade do usuario fazer um comentario de bloco que ocupe multiplas linhas, mas nem sempre o comentario de bloco vai ocupar toda a linha;
					}
					else {
						System.out.println("/ eh um operador arimetico");
					}
						
				}
				break;
			}
		}
		
		System.out.println(aut.palavrasReservadas("string-teste"));
		texto.close();
		
	}

}
